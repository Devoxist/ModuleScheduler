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

package resolvers;

import modules.ModuleA;
import modules.ModuleB;
import modules.ModuleC;
import nl.devoxist.modulescheduler.Module;
import nl.devoxist.modulescheduler.resolvers.DependencyResolver;
import nl.devoxist.modulescheduler.settings.ModuleInformation;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@TestOnly
public class DependencyResolverTest {

    @Test
    public void resolveInformationTest() {
        Set<Class<? extends Module>> modulesClassesSet = new HashSet<>();
        modulesClassesSet.add(ModuleA.class);

        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                DependencyResolver.resolveDependencies(modulesClassesSet);

        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleA.class));
        Assertions.assertFalse(moduleInformationMap.containsKey(ModuleC.class));
        ModuleInformation<?> moduleInformationA = moduleInformationMap.get(ModuleA.class);
        Assertions.assertEquals(ModuleA.class, moduleInformationA.getModule());
        Assertions.assertNull(moduleInformationMap.get(ModuleC.class));
    }

    @Test
    public void resolveInformationTest2() {
        Set<Class<? extends Module>> modulesClassesSet = new HashSet<>();
        modulesClassesSet.add(ModuleA.class);
        modulesClassesSet.add(ModuleB.class);

        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                DependencyResolver.resolveDependencies(modulesClassesSet);

        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleA.class));
        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleB.class));
        Assertions.assertFalse(moduleInformationMap.containsKey(ModuleC.class));
        ModuleInformation<?> moduleInformationA = moduleInformationMap.get(ModuleA.class);
        Assertions.assertEquals(ModuleA.class, moduleInformationA.getModule());
        ModuleInformation<?> moduleInformationB = moduleInformationMap.get(ModuleB.class);
        Assertions.assertEquals(ModuleB.class, moduleInformationB.getModule());
        Assertions.assertTrue(moduleInformationA.getDependsOn().contains(moduleInformationB));
        Assertions.assertNull(moduleInformationMap.get(ModuleC.class));
    }

    @Test
    public void resolveInformationTest3() {
        Set<Class<? extends Module>> modulesClassesSet = new HashSet<>();
        modulesClassesSet.add(ModuleA.class);
        modulesClassesSet.add(ModuleB.class);
        modulesClassesSet.add(ModuleC.class);

        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                DependencyResolver.resolveDependencies(modulesClassesSet);

        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleA.class));
        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleB.class));
        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleC.class));
        ModuleInformation<?> moduleInformationA = moduleInformationMap.get(ModuleA.class);
        Assertions.assertEquals(ModuleA.class, moduleInformationA.getModule());
        ModuleInformation<?> moduleInformationB = moduleInformationMap.get(ModuleB.class);
        Assertions.assertEquals(ModuleB.class, moduleInformationB.getModule());
        ModuleInformation<?> moduleInformationC = moduleInformationMap.get(ModuleC.class);
        Assertions.assertEquals(ModuleC.class, moduleInformationC.getModule());
        Assertions.assertTrue(moduleInformationB.getDependencies().contains(ModuleA.class));
        Assertions.assertTrue(moduleInformationA.getDependsOn().contains(moduleInformationB));
        Assertions.assertTrue(moduleInformationC.getDependencies().contains(ModuleB.class));
        Assertions.assertTrue(moduleInformationB.getDependsOn().contains(moduleInformationC));
    }


}
