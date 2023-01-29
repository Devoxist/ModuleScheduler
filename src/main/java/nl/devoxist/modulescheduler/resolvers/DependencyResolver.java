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

package nl.devoxist.modulescheduler.resolvers;

import nl.devoxist.modulescheduler.Module;
import nl.devoxist.modulescheduler.annotation.Dependency;
import nl.devoxist.modulescheduler.settings.ModuleInformation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The {@link DependencyResolver} will be used to resolve the dependencies of the {@link Module}s.
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DependencyResolver {

    /**
     * Construct a new {@link DependencyResolver} object. This always fails, because the class is a static class. So it
     * only contains static objects. Thus, it throws an {@link IllegalAccessException}.
     *
     * @throws IllegalAccessException If the {@link DependencyResolver} was try to construct the class. The
     *                                construction of this class is not possible, because this is a static class.
     * @since 1.0.0
     */
    @Contract(value = " -> fail",
              pure = true)
    private DependencyResolver() throws IllegalAccessException {
        throw new IllegalAccessException("This class is an static class, so this class cannot be initialized.");
    }

    /**
     * Resolve the dependencies of the given {@link Module}s and return the information of these modules. These
     * dependencies will be resolved from the information of the {@link Dependency} annotation.
     *
     * @param modules The collection of modules of which the dependencies need to be resolved.
     *
     * @return The map with the class of the {@link Module} and information of that particular {@link Module}.
     *
     * @since 1.0.0
     */
    public static @NotNull Map<Class<? extends Module>, ModuleInformation<?>> resolveDependencies(@NotNull Set<Class<? extends Module>> modules) {
        Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
                new TreeMap<>(Comparator.comparing(Class::getName));

        for (Class<? extends Module> module : modules) {
            ModuleInformation<?> information =
                    ModuleInformationResolver.resolveInformation(module, moduleInformationMap);
            moduleInformationMap.put(module, information);
        }

        return moduleInformationMap;
    }

}
