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

package settings;

import modules.ModuleA;
import modules.ModuleB;
import modules.ModuleC;
import nl.devoxist.modulescheduler.settings.ModuleInformation;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@TestOnly
public class ModuleInformationTest {

    @Test
    public void constructModuleInformationTest() {
        ModuleInformation<ModuleA> moduleInformationA = new ModuleInformation<>(ModuleA.class);

        Assertions.assertEquals(ModuleA.class, moduleInformationA.getModule());
    }

    @Test
    public void addDependencyTest() {
        ModuleInformation<ModuleA> moduleInformationA = new ModuleInformation<>(ModuleA.class);

        Assertions.assertTrue(moduleInformationA.getDependencies().isEmpty());

        boolean added = moduleInformationA.addDependency(ModuleB.class);

        Assertions.assertTrue(added);
        Assertions.assertTrue(moduleInformationA.getDependencies().contains(ModuleB.class));
        Assertions.assertFalse(moduleInformationA.getDependencies().contains(ModuleC.class));
    }

    @Test
    public void addDependencyTest2() {

        ModuleInformation<ModuleA> moduleInformationA = new ModuleInformation<>(ModuleA.class);

        Assertions.assertTrue(moduleInformationA.getDependencies().isEmpty());

        boolean added = moduleInformationA.addDependency(ModuleB.class);

        Assertions.assertTrue(added);

        added = moduleInformationA.addDependency(ModuleC.class);

        Assertions.assertTrue(added);
        Assertions.assertTrue(moduleInformationA.getDependencies().contains(ModuleB.class));
        Assertions.assertTrue(moduleInformationA.getDependencies().contains(ModuleC.class));
    }

    @Test
    public void addDependencyFailedTest() {
        ModuleInformation<ModuleA> moduleInformationA = new ModuleInformation<>(ModuleA.class);

        Assertions.assertTrue(moduleInformationA.getDependencies().isEmpty());

        boolean added = moduleInformationA.addDependency(ModuleA.class);

        Assertions.assertFalse(added);
        Assertions.assertTrue(moduleInformationA.getDependencies().isEmpty());
    }

    @Test
    public void addDependsOnTest() {
        ModuleInformation<ModuleA> moduleInformationA = new ModuleInformation<>(ModuleA.class);
        ModuleInformation<ModuleB> moduleInformationB = new ModuleInformation<>(ModuleB.class);

        Assertions.assertTrue(moduleInformationA.getDependsOn().isEmpty());

        boolean added = moduleInformationA.addDependsOn(moduleInformationB);

        Assertions.assertTrue(added);
        Assertions.assertTrue(moduleInformationA.getDependsOn().contains(moduleInformationB));
    }

    @Test
    public void addDependsOnTest2() {
        ModuleInformation<ModuleA> moduleInformationA = new ModuleInformation<>(ModuleA.class);
        ModuleInformation<ModuleB> moduleInformationB = new ModuleInformation<>(ModuleB.class);
        ModuleInformation<ModuleC> moduleInformationC = new ModuleInformation<>(ModuleC.class);

        Assertions.assertTrue(moduleInformationA.getDependsOn().isEmpty());
        Assertions.assertTrue(moduleInformationC.getDependsOn().isEmpty());

        boolean added = moduleInformationA.addDependsOn(moduleInformationB);

        Assertions.assertTrue(added);
        Assertions.assertTrue(moduleInformationC.getDependsOn().isEmpty());

        added = moduleInformationC.addDependsOn(moduleInformationB);

        Assertions.assertTrue(added);
        Assertions.assertTrue(moduleInformationC.getDependsOn().contains(moduleInformationB));
        Assertions.assertTrue(moduleInformationA.getDependsOn().contains(moduleInformationB));
    }

    @Test
    public void addDependsOnFailedTest() {
        ModuleInformation<ModuleA> moduleInformationA = new ModuleInformation<>(ModuleA.class);

        Assertions.assertTrue(moduleInformationA.getDependsOn().isEmpty());

        boolean added = moduleInformationA.addDependsOn(moduleInformationA);

        Assertions.assertFalse(added);
        Assertions.assertFalse(moduleInformationA.getDependsOn().contains(moduleInformationA));
    }

    @Test
    public void addDependsOnFailedTest1() {
        ModuleInformation<ModuleA> moduleInformationA = new ModuleInformation<>(ModuleA.class);
        ModuleInformation<ModuleB> moduleInformationB = new ModuleInformation<>(ModuleB.class);
        ModuleInformation<ModuleC> moduleInformationC = new ModuleInformation<>(ModuleC.class);

        Assertions.assertTrue(moduleInformationA.getDependsOn().isEmpty());
        Assertions.assertTrue(moduleInformationC.getDependsOn().isEmpty());

        boolean added = moduleInformationA.addDependsOn(moduleInformationB);

        Assertions.assertTrue(added);
        Assertions.assertTrue(moduleInformationA.getDependsOn().contains(moduleInformationB));
        Assertions.assertTrue(moduleInformationC.getDependsOn().isEmpty());

        added = moduleInformationC.addDependsOn(moduleInformationC);

        Assertions.assertFalse(added);
        Assertions.assertTrue(moduleInformationC.getDependsOn().isEmpty());
    }


}
