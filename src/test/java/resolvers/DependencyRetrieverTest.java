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

import nl.devoxist.modulescheduler.Module;
import nl.devoxist.modulescheduler.annotation.Dependency;
import nl.devoxist.modulescheduler.resolvers.DependencyRetriever;
import org.jetbrains.annotations.Contract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DependencyRetrieverTest {

    @Test
    public void retrieveDependenciesTest() {
        Class<? extends Module>[] modulesClasses = DependencyRetriever.getDependencies(ModuleA.class);

        List<Class<? extends Module>> list = Arrays.asList(modulesClasses);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void retrieveDependenciesTest2() {
        Class<? extends Module>[] modulesClasses = DependencyRetriever.getDependencies(ModuleB.class);

        List<Class<? extends Module>> list = Arrays.asList(modulesClasses);

        Assertions.assertEquals(1, list.size());
        Assertions.assertTrue(list.contains(ModuleA.class));
    }

    @Test
    public void retrieveDependenciesTest3() {
        Class<? extends Module>[] modulesClasses = DependencyRetriever.getDependencies(ModuleD.class);

        List<Class<? extends Module>> list = Arrays.asList(modulesClasses);

        Assertions.assertEquals(2, list.size());
        Assertions.assertTrue(list.contains(ModuleA.class));
        Assertions.assertTrue(list.contains(ModuleC.class));
    }

    @Test
    public void retrieveDependenciesTest4() {
        Class<? extends Module>[] modulesClasses = DependencyRetriever.getDependencies(ModuleC.class);

        List<Class<? extends Module>> list = Arrays.asList(modulesClasses);

        Assertions.assertEquals(2, list.size());
        Assertions.assertTrue(list.contains(ModuleA.class));
        Assertions.assertTrue(list.contains(ModuleB.class));
    }


    public static class ModuleA implements Module {


        @Override
        public void onExecute() {

        }
    }

    public static class ModuleB implements Module {

        @Contract(pure = true)
        public ModuleB(ModuleA moduleA) {
        }


        @Override
        public void onExecute() {

        }
    }

    @Dependency(ModuleA.class)
    public static class ModuleC implements Module {

        @Contract(pure = true)
        public ModuleC(ModuleB moduleB) {

        }

        @Override
        public void onExecute() {

        }
    }

    @Dependency({ModuleA.class, ModuleC.class})
    public static class ModuleD implements Module {

        @Contract(pure = true)
        public ModuleD() {

        }

        @Override
        public void onExecute() {

        }
    }
}
