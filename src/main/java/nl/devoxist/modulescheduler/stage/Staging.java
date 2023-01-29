/*
 * Copyright (c) 2023 Devoxist, Dev-Bjorn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package nl.devoxist.modulescheduler.stage;

import nl.devoxist.modulescheduler.Module;
import nl.devoxist.modulescheduler.ModuleScheduler;
import nl.devoxist.modulescheduler.exception.ModuleException;
import nl.devoxist.modulescheduler.path.Path;
import nl.devoxist.modulescheduler.path.PathCyclePrinter;
import nl.devoxist.modulescheduler.settings.ModuleInformation;
import nl.devoxist.modulescheduler.settings.ModuleSchedulerInformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

/**
 * The process of placing the {@link Module}s in the correct load order.
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Staging {
    /**
     * The ordered {@link Map} with {@link Module}s linked with the highest stage number.
     *
     * @since 1.0.0
     */
    private final TreeMap<ModuleInformation<?>, Integer> highestModuleStage = new TreeMap<>();
    /**
     * The ordered {@link Set} of {@link Module}s, that are in the correct load order.
     *
     * @since 1.0.0
     */
    private final TreeSet<Stage> stages = new TreeSet<>();
    /**
     * The boolean to check if the zeroth or first stage is occupied.
     *
     * @since 1.0.0
     */
    private boolean zeroOrFirstStageOccupied = false;

    /**
     * Update the {@link #stages} to create the load order of the {@link Module}s.
     *
     * @param moduleSchedulerInformation The information of the current {@link ModuleScheduler}.
     *
     * @return The unmodifiable {@link Set} of {@link Module}s that is in the correct load order.
     *
     * @throws ModuleException If the zeroth or first stage is not occupied.
     * @see ModuleInformation
     * @since 1.0.0
     */
    public static @NotNull @UnmodifiableView Set<Stage> stageModules(ModuleSchedulerInformation moduleSchedulerInformation) {
        Set<Stage> stageSet = new Staging().stageModule(moduleSchedulerInformation);
        return Collections.unmodifiableSet(stageSet);
    }


    /**
     * Update the {@link #stages} to create the load order of the {@link Module}s.
     *
     * @param moduleSchedulerInformation The information of the current {@link ModuleScheduler}.
     *
     * @return The {@link Set} of {@link Module}s that is in the correct load order.
     *
     * @throws ModuleException If the zeroth or first stage is not occupied.
     * @see ModuleInformation
     * @since 1.0.0
     */
    private Set<Stage> stageModule(@NotNull ModuleSchedulerInformation moduleSchedulerInformation) {
        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                moduleSchedulerInformation.getModuleInformationMap();

        if (moduleInformationMap.isEmpty()) {
            throw new ModuleException("There is no module to be ordered.");
        }

        createStages(moduleInformationMap);

        if (!zeroOrFirstStageOccupied) {
            throw new ModuleException("There need to be a zeroth or first stage to start the loading process.");
        }

        return stages;
    }

    /**
     * Update the {@link #stages} to create the load order of the {@link Module}s.
     *
     * @param moduleInformationMap The map of {@link Module}s with their information.
     *
     * @see ModuleInformation
     * @since 1.0.0
     */
    private void createStages(@NotNull Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap) {
        for (ModuleInformation<?> moduleInformation : moduleInformationMap.values()) {
            if (isZeroStage(moduleInformation) && addStage(0, moduleInformation)) {
                zeroOrFirstStageOccupied = true;
                continue;
            }
            if (!isFirstStage(moduleInformation)) {
                continue;
            }
            zeroOrFirstStageOccupied = true;
            addStage(1, moduleInformation);

            new HigherStageUpdater(moduleInformation);
        }
    }


    /**
     * Add a stage to the load process. If the {@link Module} is already in the load process it will be updated, if the
     * given stage is higher than the current stage. If it is not in the load process it will be added and constructed.
     *
     * @param stageNumber       The number of the stage.
     * @param moduleInformation The information of the {@link Module}.
     *
     * @since 1.0.0
     */
    private boolean addStage(int stageNumber, ModuleInformation<?> moduleInformation) {
        Integer highestStage = highestModuleStage.get(moduleInformation);

        if (highestStage == null) {
            createNewStage(stageNumber, moduleInformation);
            return true;
        }

        if (highestStage < stageNumber) {
            updateStage(highestStage, stageNumber, moduleInformation);
            return true;
        }

        return true;
    }

    /**
     * Update the stage to the new highest stage and update the load process.
     *
     * @param highestStage      The highest stage.
     * @param stageNumber       The current number of the stage.
     * @param moduleInformation The information of the {@link Module}.
     *
     * @since 1.0.0
     */
    private void updateStage(int highestStage, int stageNumber, ModuleInformation<?> moduleInformation) {
        stages.remove(new Stage(highestStage, moduleInformation));
        stages.add(new Stage(stageNumber, moduleInformation));
        highestModuleStage.put(moduleInformation, stageNumber);
    }

    /**
     * Create a stage and add it to the load process.
     *
     * @param stageNumber       The number of the stage.
     * @param moduleInformation The information of the {@link Module}.
     *
     * @since 1.0.0
     */
    private void createNewStage(int stageNumber, ModuleInformation<?> moduleInformation) {
        Stage newStage = new Stage(stageNumber, moduleInformation);
        stages.add(newStage);
        highestModuleStage.put(moduleInformation, stageNumber);
    }

    /**
     * Check if a module is in the first stage. This is a stage for the {@link Module} that does not have dependencies,
     * but only depends on other {@link Module}s.
     *
     * @param moduleInformation The information of the {@link Module}, which will be checked if it is in the first
     *                          stage.
     *
     * @return If {@code true} the {@link Module} needs to be in the first stage.
     *
     * @since 1.0.0
     */
    private boolean isFirstStage(@NotNull ModuleInformation<?> moduleInformation) {
        return moduleInformation.getDependencies().isEmpty();
    }

    /**
     * Check if a module is in the zeroth stage. This is a stage for the {@link Module} that does not have a link with
     * any {@link Module}.
     *
     * @param moduleInformation The information of the {@link Module}, which will be checked if it is in the zeroth
     *                          stage.
     *
     * @return If {@code true} the {@link Module} needs to be in the zeroth stage.
     *
     * @since 1.0.0
     */
    private boolean isZeroStage(@NotNull ModuleInformation<?> moduleInformation) {
        return moduleInformation.getDependencies().isEmpty() && moduleInformation.getDependsOn().isEmpty();
    }

    /**
     * The process of placing the higher staged {@link Module}s in the correct load order.
     *
     * @author Dev-Bjorn
     * @version 1.0.0
     * @since 1.0.0
     */
    private class HigherStageUpdater {
        /**
         * The current non-ordered {@link Set} which contains the {@link Module}s that are in the current path.
         *
         * @since 1.0.0
         */
        private final Set<ModuleInformation<?>> pathing = new TreeSet<>();
        /**
         * The number of the current stage.
         *
         * @since 1.0.0
         */
        private int stageNumber = 1;
        /**
         * The current path of the current {@link Module}.
         *
         * @since 1.0.0
         */
        private Path path = new Path();
        /**
         * The path of the previous {@link Module}.
         *
         * @since 1.0.0
         */
        private Path previousPath = new Path();

        /**
         * Start the process of resolving the higher staged {@link Module}s.
         *
         * @param firstModule The {@link Module} that is in the first stage and will be resolved.
         *
         * @since 1.0.0
         */
        HigherStageUpdater(@NotNull ModuleInformation<?> firstModule) {
            previousPath.setCls(firstModule.getModule());
            previousPath.setNextPath(path);
            pathing.add(firstModule);

            resolve(firstModule);
        }

        /**
         * The process of resolving the higher staged {@link Module}s. The modules that are resolved will be added to
         * {@link #stages}.
         *
         * @param startingModule The module where the process starts or the loading module that needs to be resolved.
         *
         * @since 1.0.0
         */
        private void resolve(@NotNull ModuleInformation<?> startingModule) {
            ++stageNumber;
            updatePath(startingModule);

            for (ModuleInformation<?> loadingModule : startingModule.getDependsOn()) {
                if (isCycle(loadingModule)) {
                    break;
                }

                Staging.this.addStage(stageNumber, loadingModule);
                resolve(loadingModule);
            }

            pathing.remove(startingModule);
            path = path.getPreviousPath();
            --stageNumber;

        }

        /**
         * Check if the {@link Module} is a cycle.
         *
         * @param loadingModule The loading {@link Module} that needs to be checked.
         *
         * @return If {@code true} the loading {@link Module} is in a cycle.
         *
         * @since 1.0.0
         */
        private boolean isCycle(ModuleInformation<?> loadingModule) {
            if (!pathing.add(loadingModule)) {
                path.setCls(loadingModule.getModule());
                PathCyclePrinter.printPath(path);
                return true;
            }

            return false;
        }

        /**
         * Update the path to go to the next {@link Path}.
         *
         * @param pathModule The current loading {@link Module}.
         *
         * @since 1.0.0
         */
        private void updatePath(ModuleInformation<?> pathModule) {
            path.setCls(pathModule.getModule());

            previousPath = path;
            path = new Path();

            previousPath.setNextPath(path);
        }
    }


}
