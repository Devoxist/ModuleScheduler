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
import nl.devoxist.modulescheduler.Module;
import nl.devoxist.modulescheduler.resolvers.ModuleInformationResolver;
import nl.devoxist.modulescheduler.settings.ModuleInformation;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

@TestOnly
public class ModuleInformationResolverTest {

    @Test
    public void resolveInformationTest() {
        HashMap<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap = new HashMap<>();
        ModuleInformation<?> moduleInformation =
                ModuleInformationResolver.resolveInformation(ModuleA.class, moduleInformationMap);

        Assertions.assertEquals(ModuleA.class, moduleInformation.getModule());
        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleA.class));
        Assertions.assertEquals(moduleInformation, moduleInformationMap.get(ModuleA.class));
    }

    @Test
    public void resolveInformationTest2() {
        HashMap<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap = new HashMap<>();
        ModuleInformation<?> moduleInformationA =
                ModuleInformationResolver.resolveInformation(ModuleA.class, moduleInformationMap);
        ModuleInformation<?> moduleInformationB =
                ModuleInformationResolver.resolveInformation(ModuleB.class, moduleInformationMap);

        Assertions.assertEquals(ModuleA.class, moduleInformationA.getModule());
        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleA.class));
        Assertions.assertEquals(moduleInformationA, moduleInformationMap.get(ModuleA.class));

        Assertions.assertFalse(moduleInformationA.getDependsOn().isEmpty());
        Assertions.assertTrue(moduleInformationB.getDependsOn().isEmpty());
        Assertions.assertTrue(moduleInformationA.getDependencies().isEmpty());
        Assertions.assertFalse(moduleInformationB.getDependencies().isEmpty());
        Assertions.assertTrue(moduleInformationA.getDependsOn().contains(moduleInformationB));
        Assertions.assertTrue(moduleInformationB.getDependencies().contains(ModuleA.class));

        Assertions.assertEquals(ModuleB.class, moduleInformationB.getModule());
        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleB.class));
        Assertions.assertEquals(moduleInformationB, moduleInformationMap.get(ModuleB.class));

    }

    @Test
    public void resolveInformationTest3() {
        HashMap<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap = new HashMap<>();
        ModuleInformation<?> moduleInformationA =
                ModuleInformationResolver.resolveInformation(ModuleA.class, moduleInformationMap);

        Assertions.assertEquals(ModuleA.class, moduleInformationA.getModule());
        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleA.class));
        Assertions.assertEquals(moduleInformationA, moduleInformationMap.get(ModuleA.class));

        Assertions.assertTrue(moduleInformationA.getDependsOn().isEmpty());
        Assertions.assertTrue(moduleInformationA.getDependencies().isEmpty());

        ModuleInformation<?> moduleInformationB =
                ModuleInformationResolver.resolveInformation(ModuleB.class, moduleInformationMap);

        Assertions.assertFalse(moduleInformationA.getDependsOn().isEmpty());
        Assertions.assertTrue(moduleInformationB.getDependsOn().isEmpty());
        Assertions.assertTrue(moduleInformationA.getDependencies().isEmpty());
        Assertions.assertFalse(moduleInformationB.getDependencies().isEmpty());
        Assertions.assertTrue(moduleInformationA.getDependsOn().contains(moduleInformationB));
        Assertions.assertTrue(moduleInformationB.getDependencies().contains(ModuleA.class));

        Assertions.assertEquals(ModuleB.class, moduleInformationB.getModule());
        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleB.class));
        Assertions.assertEquals(moduleInformationB, moduleInformationMap.get(ModuleB.class));

        Assertions.assertEquals(ModuleA.class, moduleInformationA.getModule());
        Assertions.assertTrue(moduleInformationMap.containsKey(ModuleA.class));
        Assertions.assertEquals(moduleInformationA, moduleInformationMap.get(ModuleA.class));
    }
}
