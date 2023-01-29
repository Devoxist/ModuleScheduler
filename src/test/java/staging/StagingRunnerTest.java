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

package staging;

import nl.devoxist.modulescheduler.Module;
import nl.devoxist.modulescheduler.ModuleScheduler;
import nl.devoxist.modulescheduler.annotation.Dependency;
import nl.devoxist.modulescheduler.resolvers.ModuleInformationResolver;
import nl.devoxist.modulescheduler.runner.StageRunner;
import nl.devoxist.modulescheduler.settings.ModuleInformation;
import nl.devoxist.modulescheduler.settings.ModuleSchedulerSettings;
import nl.devoxist.modulescheduler.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class StagingRunnerTest {

    @Test
    public void stageRunnerTest1() {
        ModuleSchedulerSettings schedulerSettings = new ModuleSchedulerSettings();

        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                new TreeMap<>(Comparator.comparing(Class::getName));

        ModuleInformation<?> moduleInformationA = ModuleInformationResolver.resolveInformation(
                ModuleA.class,
                moduleInformationMap
        );

        TreeSet<Stage> stages = new TreeSet<>();

        Stage stageA = new Stage(0, moduleInformationA);
        stages.add(stageA);

        StageRunner.runStages(schedulerSettings, new ModuleScheduler() {

            int stage = 1;
            boolean before = false;

            @Override
            public void updateSettings(ModuleSchedulerSettings settings) {

            }

            @Override
            public void beforeModuleExecute(Module module) {
                if (stage == 1) {
                    Assertions.assertEquals(ModuleA.class, module.getClass());
                }
                before = true;
                ++stage;
            }

            @Override
            public void afterModuleExecute(Module module) {
                Assertions.assertTrue(before);
            }
        }, stages);
    }

    @Test
    public void stageRunnerTest2() {
        ModuleSchedulerSettings schedulerSettings = new ModuleSchedulerSettings();

        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                new TreeMap<>(Comparator.comparing(Class::getName));

        ModuleInformation<?> moduleInformationA = ModuleInformationResolver.resolveInformation(
                ModuleA.class,
                moduleInformationMap
        );

        ModuleInformation<?> moduleInformationB = ModuleInformationResolver.resolveInformation(
                ModuleB.class,
                moduleInformationMap
        );

        TreeSet<Stage> stages = new TreeSet<>();

        Stage stageA = new Stage(1, moduleInformationA);
        Stage stageB = new Stage(2, moduleInformationB);
        stages.add(stageA);
        stages.add(stageB);

        StageRunner.runStages(schedulerSettings, new ModuleScheduler() {

            int stage = 1;
            boolean before = false;

            @Override
            public void updateSettings(ModuleSchedulerSettings settings) {

            }

            @Override
            public void beforeModuleExecute(Module module) {
                if (stage == 1) {
                    Assertions.assertEquals(ModuleA.class, module.getClass());
                } else if (stage == 2) {
                    Assertions.assertEquals(ModuleB.class, module.getClass());
                }

                before = true;
                ++stage;
            }

            @Override
            public void afterModuleExecute(Module module) {
                Assertions.assertTrue(before);
            }
        }, stages);
    }

    @Test
    public void stageRunnerTest3() {
        ModuleSchedulerSettings schedulerSettings = new ModuleSchedulerSettings();

        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                new TreeMap<>(Comparator.comparing(Class::getName));

        ModuleInformation<?> moduleInformationA = ModuleInformationResolver.resolveInformation(
                ModuleA.class,
                moduleInformationMap
        );

        ModuleInformation<?> moduleInformationC = ModuleInformationResolver.resolveInformation(
                ModuleC.class,
                moduleInformationMap
        );

        ModuleInformation<?> moduleInformationB = ModuleInformationResolver.resolveInformation(
                ModuleB.class,
                moduleInformationMap
        );

        TreeSet<Stage> stages = new TreeSet<>();

        Stage stageA = new Stage(1, moduleInformationA);
        Stage stageB = new Stage(2, moduleInformationB);
        Stage stageC = new Stage(3, moduleInformationC);
        stages.add(stageA);
        stages.add(stageB);
        stages.add(stageC);

        StageRunner.runStages(schedulerSettings, new ModuleScheduler() {

            int stage = 1;
            boolean before = false;

            @Override
            public void updateSettings(ModuleSchedulerSettings settings) {

            }

            @Override
            public void beforeModuleExecute(Module module) {
                if (stage == 1) {
                    Assertions.assertEquals(ModuleA.class, module.getClass());
                } else if (stage == 2) {
                    Assertions.assertEquals(ModuleB.class, module.getClass());
                } else if (stage == 3) {
                    Assertions.assertEquals(ModuleC.class, module.getClass());
                }
                before = true;
                ++stage;
            }

            @Override
            public void afterModuleExecute(Module module) {
                Assertions.assertTrue(before);
            }
        }, stages);
    }

    public static class ModuleA implements Module {


        @Override
        public void onExecute() {

        }
    }

    @Dependency({ModuleA.class})
    public static class ModuleB implements Module {

        @Contract(pure = true)
        public ModuleB(ModuleA moduleA) {
        }


        @Override
        public void onExecute() {

        }
    }

    @Dependency({ModuleB.class, ModuleA.class})
    public static class ModuleC implements Module {

        @Contract(pure = true)
        public ModuleC(ModuleA moduleA, ModuleB moduleB) {

        }

        @Override
        public void onExecute() {

        }
    }
}
