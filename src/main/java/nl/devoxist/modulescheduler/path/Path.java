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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * {@link Path} is an object that constructs a {@link Path} which links an object to an upper level {@link Path} and a
 * lower level {@link Path}.
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Path {
    /**
     * The upper level {@link Path}.
     *
     * @since 1.0.0
     */
    private Path nextPath = null;
    /**
     * The lower level {@link Path}.
     *
     * @since 1.0.0
     */
    private Path previousPath = null;
    /**
     * The class of the {@link Path}.
     *
     * @since 1.0.0
     */
    private Class<?> cls;

    /**
     * Set the upper level {@link Path}. This also sets the lower level {@link Path} of the given upper level
     * {@link Path}.
     *
     * @param nextPath The upper level {@link Path}.
     *
     * @since 1.0.0
     */
    public void setNextPath(@NotNull Path nextPath) {
        this.nextPath = nextPath;
        nextPath.setPreviousPath(this);
    }

    /**
     * Get the upper level {@link Path}.
     *
     * @return The upper level {@link Path}.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    public Path getNextPath() {
        return nextPath;
    }

    /**
     * Checks if this {@link Path} has an upper level {@link Path}.
     *
     * @return If {@code true} this {@link Path} has an upper level {@link Path}.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    public boolean hasNextPath() {
        return this.nextPath != null;
    }

    /**
     * Set the lower level {@link Path}.
     *
     * @param previousPath The lower level {@link Path}.
     *
     * @since 1.0.0
     */
    private void setPreviousPath(Path previousPath) {
        this.previousPath = previousPath;
    }

    /**
     * Get the lower level {@link Path}.
     *
     * @return The lower level {@link Path}.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    public Path getPreviousPath() {
        return previousPath;
    }

    /**
     * Checks if this {@link Path} has a lower level {@link Path}.
     *
     * @return If {@code true} this {@link Path} has a lower level {@link Path}.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    public boolean hasPreviousPath() {
        return this.previousPath != null;
    }

    /**
     * Set the {@link Class} of the {@link Path}. This causes the link between the upper and lower level {@link Path}.
     *
     * @param cls The class of the link.
     *
     * @since 1.0.0
     */
    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    /**
     * Get the {@link Class} of the {@link Path}.
     *
     * @return The {@link Class} of the {@link Path}.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    public Class<?> getCls() {
        return cls;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     *
     * <p>
     * An equivalence relation partitions the elements it operates on
     * into <i>equivalence classes</i>; all the members of an
     * equivalence class are equal to each other. Members of an
     * equivalence class are substitutable for each other, at least
     * for some purposes.
     *
     * @param obj the reference object with which to compare.
     *
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     *
     * @implSpec The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * In other words, under the reference equality equivalence
     * relation, each equivalence class only has a single element.
     * @apiNote It is generally necessary to override the {@link #hashCode hashCode}
     * method whenever this method is overridden, to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     * @see #hashCode()
     * @see java.util.HashMap
     * @since 1.0.0
     */
    @Contract(value = "null -> false",
              pure = true)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Path path = (Path) obj;
        return Objects.equals(nextPath, path.nextPath) &&
               Objects.equals(cls, path.cls) &&
               Objects.equals(previousPath, path.previousPath);
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@link
     *     #equals(Object) equals} method, then calling the {@code
     *     hashCode} method on each of the two objects must produce the
     *     same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link #equals(Object) equals} method, then
     *     calling the {@code hashCode} method on each of the two objects
     *     must produce distinct integer results.  However, the programmer
     *     should be aware that producing distinct integer results for
     *     unequal objects may improve the performance of hash tables.
     * </ul>
     *
     * @return a hash code value for this object.
     *
     * @implSpec As far as is reasonably practical, the {@code hashCode} method defined
     * by class {@code Object} returns distinct integers for distinct objects.
     * @see java.lang.Object#equals(java.lang.Object)
     * @see java.lang.System#identityHashCode
     * @since 1.0.0
     */
    @Override
    public int hashCode() {
        return Objects.hash(cls);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     *
     * @apiNote In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * The string output is not necessarily stable over time or across
     * JVM invocations.
     * @implSpec The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return "Path{" +
               "cls=" + cls +
               '}';
    }
}
