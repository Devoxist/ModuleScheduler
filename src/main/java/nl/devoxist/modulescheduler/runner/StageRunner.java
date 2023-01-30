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

package nl.devoxist.modulescheduler.runner;

import nl.devoxist.modulescheduler.Module;
import nl.devoxist.modulescheduler.ModuleScheduler;
import nl.devoxist.modulescheduler.settings.ModuleSchedulerSettings;
import nl.devoxist.modulescheduler.stage.Stage;
import nl.devoxist.typeresolver.constructor.ConstructorResolver;
import nl.devoxist.typeresolver.register.Register;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * The process of loading the {@link Module}s.
 *
 * @author Dev-Bjorn
 * @version 1.2.0
 * @since 1.0.0
 */
public final class StageRunner extends Thread {
    /**
     * The settings of the current running {@link ModuleScheduler}.
     *
     * @since 1.0.0
     */
    private final ModuleSchedulerSettings moduleSchedulerSettings;
    /**
     * The current running {@link ModuleScheduler}.
     *
     * @see ModuleScheduler
     * @since 1.0.0
     */
    private final ModuleScheduler moduleScheduler;
    /**
     * The registers that the auto construction can use.
     *
     * @since 1.0.0
     */
    private final Register inputRegistries;
    /**
     * The temporary that the auto construction can use.
     *
     * @since 1.0.0
     */
    private final Register temporaryRegister = new Register();
    /**
     * The stages that need to be constructed, loaded and runned.
     *
     * @since 1.0.0
     */
    private final Set<Stage> stages;


    /**
     * Construct a process class that is responsible for the constructing, loading and running of the {@link Module}s.
     * This will not be runned on the main thread.
     *
     * @param stages                  The stages that needs to be loaded. This need to be in the correct order.
     * @param moduleScheduler         The scheduler of the process.
     * @param moduleSchedulerSettings The settings of the current running scheduler.
     *
     * @since 1.0.0
     */
    private StageRunner(
            ModuleSchedulerSettings moduleSchedulerSettings,
            ModuleScheduler moduleScheduler,
            Set<Stage> stages
    ) {
        this.moduleSchedulerSettings = moduleSchedulerSettings;
        this.inputRegistries = new Register(moduleSchedulerSettings.getRegistries());
        this.moduleScheduler = moduleScheduler;
        this.stages = stages;
    }


    /**
     * Run the process of constructing, loading and running the {@link Module}s. This will not be runned on the main
     * thread.
     *
     * @param stages                  The stages that needs to be loaded. This need to be in the correct order.
     * @param moduleScheduler         The scheduler of the process.
     * @param moduleSchedulerSettings The settings of the current running scheduler.
     *
     * @since 1.0.0
     */
    public static void runStages(
            ModuleSchedulerSettings moduleSchedulerSettings,
            ModuleScheduler moduleScheduler,
            Set<Stage> stages
    ) {
        StageRunner stageRunner = new StageRunner(moduleSchedulerSettings, moduleScheduler, stages);
        stageRunner.start();
    }

    /**
     * Run the process of constructing, loading and running the {@link Module}s. This will not be runned on the main
     * thread.
     *
     * @since 1.0.0
     */
    @Override
    public void run() {
        for (Stage stage : stages) {
            Class<? extends Module> moduleCls = stage.moduleInformation().getModule();

            Module module = getModule(moduleCls);

            if (module == null) {
                continue;
            }

            temporaryRegister.register(moduleCls, module);
            moduleSchedulerSettings.getOutputRegister().register(moduleCls, module);

            moduleScheduler.beforeModuleExecute(module);
            module.onExecute();
            moduleScheduler.afterModuleExecute(module);
        }
    }

    /**
     * Get the constructed given {@link Module} by its {@link Class}.
     *
     * @param moduleCls The {@link Module} class that needs to be constructed.
     *
     * @return The constructed {@link Module}.
     *
     * @since 1.0.0
     */
    @Nullable
    private Module getModule(Class<? extends Module> moduleCls) {
        try {
            return ConstructorResolver.initClass(
                    moduleCls,
                    false,
                    inputRegistries,
                    temporaryRegister
            );
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
