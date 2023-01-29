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
import nl.devoxist.modulescheduler.annotation.Dependency;
import nl.devoxist.modulescheduler.exception.ModuleException;
import nl.devoxist.modulescheduler.resolvers.ModuleInformationResolver;
import nl.devoxist.modulescheduler.settings.ModuleInformation;
import nl.devoxist.modulescheduler.settings.ModuleSchedulerInformation;
import nl.devoxist.modulescheduler.settings.ModuleSchedulerSettings;
import nl.devoxist.modulescheduler.stage.Stage;
import nl.devoxist.modulescheduler.stage.Staging;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StagingTest {

    @Test
    public void stageSortTest() {
        ModuleSchedulerSettings schedulerSettings = new ModuleSchedulerSettings();
        ModuleSchedulerInformation schedulerInformation = new ModuleSchedulerInformation(schedulerSettings);
        Assertions.assertThrows(
                ModuleException.class,
                () -> Staging.stageModules(schedulerInformation),
                "There is no module to be ordered."
        );
    }

    @Test
    public void stageSortTest2() {
        ModuleSchedulerSettings schedulerSettings = new ModuleSchedulerSettings();
        ModuleSchedulerInformation schedulerInformation = new ModuleSchedulerInformation(schedulerSettings);

        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                new TreeMap<>(Comparator.comparing(Class::getName));

        schedulerInformation.setModuleInformationMap(moduleInformationMap);

        ModuleInformation<?> moduleInformation = ModuleInformationResolver.resolveInformation(
                ModuleA.class,
                moduleInformationMap
        );

        Set<Stage> stageSet = Staging.stageModules(schedulerInformation);

        Assertions.assertEquals(1, stageSet.size());
        Assertions.assertEquals(0, stageSet.toArray(Stage[]::new)[0].stage());
        Assertions.assertEquals(moduleInformation, stageSet.toArray(Stage[]::new)[0].moduleInformation());
    }

    @Test
    public void stageSortTest3() {
        ModuleSchedulerSettings schedulerSettings = new ModuleSchedulerSettings();
        ModuleSchedulerInformation schedulerInformation = new ModuleSchedulerInformation(schedulerSettings);

        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                new TreeMap<>(Comparator.comparing(Class::getName));

        schedulerInformation.setModuleInformationMap(moduleInformationMap);

        ModuleInformation<?> moduleInformationA = ModuleInformationResolver.resolveInformation(
                ModuleA.class,
                moduleInformationMap
        );

        ModuleInformation<?> moduleInformationB = ModuleInformationResolver.resolveInformation(
                ModuleB.class,
                moduleInformationMap
        );

        Set<Stage> stageSet = Staging.stageModules(schedulerInformation);

        Assertions.assertEquals(2, stageSet.size());

        Stage stageA = stageSet.toArray(Stage[]::new)[0];
        Assertions.assertEquals(1, stageA.stage());
        Assertions.assertEquals(moduleInformationA, stageA.moduleInformation());

        Stage stageB = stageSet.toArray(Stage[]::new)[1];
        Assertions.assertEquals(2, stageB.stage());
        Assertions.assertEquals(moduleInformationB, stageB.moduleInformation());
    }

    @Test
    public void stageSortTest4() {
        ModuleSchedulerSettings schedulerSettings = new ModuleSchedulerSettings();
        ModuleSchedulerInformation schedulerInformation = new ModuleSchedulerInformation(schedulerSettings);

        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                new TreeMap<>(Comparator.comparing(Class::getName));

        schedulerInformation.setModuleInformationMap(moduleInformationMap);

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

        Set<Stage> stageSet = Staging.stageModules(schedulerInformation);

        Assertions.assertEquals(3, stageSet.size());

        Stage stageA = stageSet.toArray(Stage[]::new)[0];
        Assertions.assertEquals(1, stageA.stage());
        Assertions.assertEquals(moduleInformationA, stageA.moduleInformation());

        Stage stageB = stageSet.toArray(Stage[]::new)[1];
        Assertions.assertEquals(2, stageB.stage());
        Assertions.assertEquals(moduleInformationB, stageB.moduleInformation());

        Stage stageC = stageSet.toArray(Stage[]::new)[2];
        Assertions.assertEquals(3, stageC.stage());
        Assertions.assertEquals(moduleInformationC, stageC.moduleInformation());
    }

    public static class ModuleA implements Module {

        @Override
        public void onExecute() {

        }
    }

    @Dependency({ModuleA.class})
    public static class ModuleB implements Module {

        public ModuleB(ModuleA moduleA) {
        }


        @Override
        public void onExecute() {

        }
    }

    @Dependency({ModuleB.class, ModuleA.class})
    public static class ModuleC implements Module {

        public ModuleC(ModuleA moduleA, ModuleB moduleB) {

        }

        @Override
        public void onExecute() {

        }
    }
}
