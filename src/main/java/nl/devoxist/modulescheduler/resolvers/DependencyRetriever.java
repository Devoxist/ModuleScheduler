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
import nl.devoxist.modulescheduler.collection.Arrays;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * {@link DependencyRetriever} is an object that is responsible for retrieving the dependencies of the {@link Module}s
 *
 * @author Dev-Bjorn
 * @version 1.2.0
 * @since 1.2.0
 */
public final class DependencyRetriever {

    /**
     * Construct a new {@link DependencyRetriever} object. This always fails, because the class is a static class.
     * So it only contains static objects. Thus, it throws an {@link IllegalAccessException}.
     *
     * @throws IllegalAccessException If the {@link DependencyRetriever} was try to construct the class. The
     *                                construction of this class is not possible, because this is a static class.
     * @since 1.2.0
     */
    @Contract(pure = true)
    private DependencyRetriever() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static class, so the construction is not accessible.");
    }

    /**
     * Get the dependencies of a {@link Module} class. These dependencies can be retrieved by the {@link Dependency}
     * annotation or the constructors of the given class.
     *
     * @param moduleCls The class of which the dependencies are retrieved from.
     *
     * @return The dependencies of the given {@link Module} class. If there is no {@link Dependency} annotation or
     * constructor present in the given class it will return an empty array of {@link Module} classes.
     *
     * @since 1.2.0
     */
    public static Class<? extends Module>[] getDependencies(
            @NotNull Class<? extends Module> moduleCls
    ) {
        return Arrays.concatArrays(
                getDependenciesByAnnotation(moduleCls),
                getDependenciesByConstructor(moduleCls)
        );
    }


    /**
     * Get the dependencies of a {@link Module} class by the parameters of the constructors of the given class.
     *
     * @param moduleCls The class of which the dependencies are retrieved from.
     *
     * @return The dependencies of the given {@link Module} class. If there are no constructors present in the given
     * class, the return will be an empty array.
     *
     * @since 1.2.0
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Module> @NotNull [] getDependenciesByConstructor(@NotNull Class<? extends Module> moduleCls) {
        return (Class<? extends Module>[]) Stream.of(moduleCls.getDeclaredConstructors())
                .flatMap((declaredConstructor) -> Stream.of(declaredConstructor.getParameterTypes()))
                .filter(Module.class::isAssignableFrom)
                .distinct()
                .toArray(Class<?>[]::new);
    }

    /**
     * Get the dependencies of a {@link Module} class by the annotation {@link Dependency}. If the {@link Dependency} is
     * not present on the class it will return an empty array of {@link Module} classes.
     *
     * @param moduleCls The class of which the dependencies are retrieved from.
     *
     * @return The dependencies of the given {@link Module} class. If the {@link Dependency} is not present on the class it will
     * return an empty array of {@link Module} classes.
     *
     * @since 1.2.0
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Module>[] getDependenciesByAnnotation(@NotNull Class<? extends Module> moduleCls) {
        if (!moduleCls.isAnnotationPresent(Dependency.class)) {
            return (Class<? extends Module>[]) new Class<?>[0];
        }
        Dependency dependency = moduleCls.getAnnotation(Dependency.class);
        return dependency.value();
    }
}
