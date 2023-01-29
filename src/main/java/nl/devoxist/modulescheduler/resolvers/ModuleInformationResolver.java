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

import java.util.Map;

/**
 * The {@link ModuleInformationResolver} will be used to resolve the information of the {@link Module}s
 * ({@link ModuleInformation}).
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ModuleInformationResolver {

    /**
     * Construct a new {@link ModuleInformationResolver} object. This always fails, because the class is a static class.
     * So it only contains static objects. Thus, it throws an {@link IllegalAccessException}.
     *
     * @throws IllegalAccessException If the {@link ModuleInformationResolver} was try to construct the class. The
     *                                construction of this class is not possible, because this is a static class.
     * @since 1.0.0
     */
    @Contract(value = " -> fail",
              pure = true)
    private ModuleInformationResolver() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static class, so the construction is not accessible.");
    }

    /**
     * Resolve the information of the given {@link Module}. The map is needed for the resolving of the dependent on
     * information. It may be so that the module is already in the map, then the information will be updated.
     *
     * @param moduleCls            The {@link Module} {@link Class} of which the information needs to be resolved.
     * @param moduleInformationMap The {@link Map} of all the already loaded information of the {@link Module}s.
     *
     * @return The information of the given module ({@link ModuleInformation}).
     *
     * @since 1.0.0
     */
    public static @NotNull ModuleInformation<?> resolveInformation(
            Class<? extends Module> moduleCls,
            Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap
    ) {
        ModuleInformation<?> moduleInformation = getModuleInformation(moduleCls, moduleInformationMap);

        Class<? extends Module>[] dependencies = getDependencies(moduleCls);

        for (Class<? extends Module> dependency : dependencies) {
            addDependsOnInformation(moduleInformation, dependency, moduleInformationMap);
            moduleInformation.addDependency(dependency);
        }

        return moduleInformation;
    }

    /**
     * Get the information of a {@link Module}. If the {@link Class} of the {@link Module} is present in the
     * {@link Map}, then it will return the object that is present in the {@link Map}. When the object is not present,
     * the function constructs a new {@link ModuleInformation}, this constructed object will be returned. NOTE: the
     * constructed object will automatically being added to the {@link Map}.
     *
     * @param moduleCls            The {@link Class} of which the information will be retrieved or made.
     * @param moduleInformationMap The {@link Map} of all the already loaded information of the {@link Module}s.
     *
     * @return If the {@link Class} of the {@link Module} is present in the {@link Map}, . When the object is not
     * present, it returns the newly cont a new {@link ModuleInformation}.
     *
     * @since 1.0.0
     */
    @NotNull
    private static ModuleInformation<?> getModuleInformation(
            Class<? extends Module> moduleCls,
            @NotNull Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap
    ) {
        ModuleInformation<?> moduleInformation = moduleInformationMap.get(moduleCls);

        if (moduleInformation == null) {
            moduleInformation = new ModuleInformation<>(moduleCls);
            moduleInformationMap.put(moduleCls, moduleInformation);
        }

        return moduleInformation;
    }

    /**
     * Update the dependent on information of a dependency of the {@link Module}.
     *
     * @param moduleInformation    The {@link ModuleInformation} where the dependent {@link Module} is needed.
     * @param dependentOfModule    The {@link Class} of the dependent {@link ModuleInformation}.
     * @param moduleInformationMap The map of all the already loaded information of the {@link Module}s.
     *
     * @since 1.0.0
     */
    private static void addDependsOnInformation(
            ModuleInformation<?> moduleInformation,
            Class<? extends Module> dependentOfModule,
            @NotNull Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap
    ) {
        ModuleInformation<?> dependentModuleInformation = getModuleInformation(dependentOfModule, moduleInformationMap);
        dependentModuleInformation.addDependsOn(moduleInformation);
    }

    /**
     * Get the dependencies of a {@link Module} class. If the {@link Dependency} is not present on the class it will
     * return an empty array of {@link Module} classes.
     *
     * @param moduleCls The class of which the dependencies are retrieved from.
     *
     * @return The dependencies of the given {@link Module} class. If the {@link Dependency} is not present on the class it will
     * return an empty array of {@link Module} classes.
     *
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Module>[] getDependencies(@NotNull Class<? extends Module> moduleCls) {
        if (!moduleCls.isAnnotationPresent(Dependency.class)) {
            return (Class<Module>[]) new Class<?>[0];
        }
        Dependency dependency = moduleCls.getAnnotation(Dependency.class);
        return dependency.value();
    }
}
