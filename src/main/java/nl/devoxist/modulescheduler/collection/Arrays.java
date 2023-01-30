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

package nl.devoxist.modulescheduler.collection;

import nl.devoxist.modulescheduler.resolvers.DependencyRetriever;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

/**
 * {@link Arrays} is an object that has functions that manipulate an {@link Array}.
 *
 * @author Dev-Bjorn
 * @version 1.2.0
 * @since 1.2.0
 */
public class Arrays {

    /**
     * Construct a new {@link DependencyRetriever} object. This always fails, because the class is a static class.
     * So it only contains static objects. Thus, it throws an {@link IllegalAccessException}.
     *
     * @throws IllegalAccessException If the {@link DependencyRetriever} was try to construct the class. The
     *                                construction of this class is not possible, because this is a static class.
     * @since 1.2.0
     */
    private Arrays() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static class, so the construction is not accessible.");
    }

    /**
     * Merge two arrays into one array.
     *
     * @param firstArray  The first array that going to be merged with the second array.
     * @param secondArray The second array that will be merged into the first array.
     * @param <T>         The type of the arrays.
     *
     * @return The merged array.
     *
     * @since 1.2.0
     */
    public static <T> T @NotNull [] concatArrays(T[] firstArray, T @NotNull [] secondArray) {
        T[] result = java.util.Arrays.copyOf(firstArray, firstArray.length + secondArray.length);
        System.arraycopy(secondArray, 0, result, firstArray.length, secondArray.length);
        return result;
    }
}
