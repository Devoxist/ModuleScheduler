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

package nl.devoxist.modulescheduler.console;

import java.util.Enumeration;

/**
 * {@link Console} is used for the replacement values for the console. These values can include:
 * <ul>
 *     <li>Colors for the console.</li>
 *     <li>The reset for formatting of the console.</li>
 *     <li>The replacement of the new line in the console.</li>
 * </ul>
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public enum Console {
    /**
     * The green color replacement value of the console.
     *
     * @since 1.0.0
     */
    GREEN("\u001b[32m"),
    /**
     * The yellow color replacement value of the console.
     *
     * @since 1.0.0
     */
    YELLOW("\u001b[33m"),
    /**
     * The red color replacement value of the console.
     *
     * @since 1.0.0
     */
    RED("\u001b[31m"),
    /**
     * The replacement value of the reset of the formatting of the console.
     *
     * @since 1.0.0
     */
    RESET("\u001b[0m"),
    /**
     * The replacement value of the new line in the console.
     *
     * @since 1.0.0
     */
    NEWLINE(System.getProperty("line.separator"));

    /**
     * The replacement value in the console
     *
     * @since 1.0.0
     */
    private final String value;

    /**
     * Construct a new object of the replacement value of the console.
     *
     * @param value The replacement value of the console.
     *
     * @since 1.0.0
     */
    Console(String value) {
        this.value = value;
    }

    /**
     * Get the replacement value of the {@link Enumeration} type.
     *
     * @return The replacement value of the {@link Enumeration} type.
     *
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return value;
    }
}