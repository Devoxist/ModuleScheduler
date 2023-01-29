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

package nl.devoxist.modulescheduler.path;

import nl.devoxist.modulescheduler.console.Console;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * The process of path resolver and printing of the {@link Path}. This is meant to be for to showcase the cycle
 * dependencies.
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public class PathCyclePrinter extends Thread {
    /**
     * The start path of the resolver.
     *
     * @since 1.0.0
     */
    private final Path firstPath;
    /**
     * The current path of the resolver.
     *
     * @since 1.0.0
     */
    private Path path;
    /**
     * The amount of steps of the path.
     *
     * @since 1.0.0
     */
    private int steps = 0;
    /**
     * The future of the output of the cycle printer.
     *
     * @since 1.0.0
     */
    private final CompletableFuture<String> completableFuture;

    /**
     * Construct a {@link PathCyclePrinter} object that is responsible for the printing of a path cycle.
     *
     * @param path The path that needs to be printed.
     *
     * @since 1.0.0
     */
    PathCyclePrinter(Path path) {
        this.path = path;
        this.firstPath = path;

        completableFuture = new CompletableFuture<>();
    }

    /**
     * Construct and print the cycle of {@link Path}.
     *
     * @param path The path that needs to be printed.
     *
     * @since 1.0.0
     */
    public static void printPath(Path path) {
        PathCyclePrinter builder = new PathCyclePrinter(path);
        builder.start();
        builder.print();
    }

    /**
     * The process of path resolver and printing of the {@link Path}. This is meant to be for to showcase the cycle
     * dependencies.
     *
     * @since 1.0.0
     */
    @Override
    public void run() {
        StringBuilder builder = new StringBuilder();

        builder.append(Console.NEWLINE)
                .append("[WARN] Dependency cycle detected: ");

        while (path.hasPreviousPath()) {
            builder.append(Console.NEWLINE).append("[WARN]    -> %s".formatted(path.getCls().getName()));

            if (steps != 0 && firstPath.hashCode() == path.hashCode()) {
                builder.append(Console.NEWLINE).append("[WARN]    -> Cycle ends");
                break;
            }

            path = path.getPreviousPath();
            ++steps;
        }

        builder.append(Console.RESET);

        this.completableFuture.complete(builder.toString());
    }

    /**
     * Print the message to {@link System#out}, this can only be done after {@link #run()} is runned.
     *
     * @since 1.0.0
     */
    public void print() {
        String value;
        try {
            value = this.completableFuture.get();
        } catch (InterruptedException | ExecutionException ignored) {
            return;
        }
        this.completableFuture.thenRun(() -> System.out.println(value)).thenRun(() -> System.exit(0));
    }

}
