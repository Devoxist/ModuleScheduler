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

package nl.devoxist.modulescheduler.settings;

import nl.devoxist.modulescheduler.Module;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@link ModuleInformation} is a {@link Class} that contains the information about a {@link Module}.
 *
 * @param <T> The type of the {@link Module}.
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ModuleInformation<T extends Module> implements Comparable<ModuleInformation<?>> {
    /**
     * The {@link Module}, where this information is about.
     *
     * @since 1.0.0
     */
    private final Class<T> module;
    /**
     * The dependencies of {@link #module}.
     *
     * @since 1.0.0
     */
    private final Set<Class<? extends Module>> dependencies = new HashSet<>();
    /**
     * A set of dependent {@link Module}s on {@link #module}.
     *
     * @since 1.0.0
     */
    private final Set<ModuleInformation<?>> dependsOn = new HashSet<>();

    /**
     * Constructs a new {@link ModuleInformation} object, which has a {@link Module} class linked to the information.
     *
     * @param moduleCls The {@link Class} of the {@link Module} where the information is about.
     *
     * @since 1.0.0
     */
    public ModuleInformation(Class<T> moduleCls) {
        this.module = moduleCls;
    }

    /**
     * Add a dependency by the class. A dependency is a module, where the current module depends on.
     *
     * @param moduleCls The class path of the module. Which need to be a dependency of the current module.
     *
     * @return If {@code false} the dependency cannot be added. The dependency cannot be added if the current module
     * already dependency on the given {@link Module}. If the dependency is on the same {@link Module} as this
     * {@link Module}.
     *
     * @since 1.0.0
     */
    public boolean addDependency(Class<? extends Module> moduleCls) {
        if (moduleCls == this.module) {
            return false;
        }
        return dependencies.add(moduleCls);
    }

    /**
     * Add a dependent {@link Module} of this {@link Module} with its {@link ModuleInformation}. A dependent module is a
     * dependency on this {@link Module}.
     *
     * @param moduleInformation The information of the {@link Module} that has a dependency on this {@link Module}.
     *
     * @return If {@code false} the dependency cannot be added. The dependency cannot be added if the current module
     * already depends on the given dependency. If the dependence is on the same {@link Module} as this {@link Module}.
     *
     * @since 1.0.0
     */
    public boolean addDependsOn(@NotNull ModuleInformation<?> moduleInformation) {
        if (moduleInformation.getModule() == this.module) {
            return false;
        }
        return dependsOn.add(moduleInformation);
    }

    /**
     * Get the dependencies of {@link #module}.
     *
     * @return A set of classes that have the dependencies of {@link #module}.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    public @NotNull @UnmodifiableView Set<Class<? extends Module>> getDependencies() {
        return Collections.unmodifiableSet(dependencies);
    }

    /**
     * Get the dependent {@link Module}s of this {@link #module}.
     *
     * @return A set of {@link ModuleInformation}s that are dependent on {@link #module}.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    public @NotNull @UnmodifiableView Set<ModuleInformation<?>> getDependsOn() {
        return Collections.unmodifiableSet(dependsOn);
    }

    /**
     * Get the {@link Class} of the {@link Module}, where this information is about.
     *
     * @return The {@link Class} of the {@link Module}, where this information is about.
     *
     * @since 1.0.0
     */
    public Class<T> getModule() {
        return module;
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
        ModuleInformation<?> that = (ModuleInformation<?>) obj;
        return Objects.equals(module, that.module) || hashCode() == that.hashCode();
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
        return Objects.hash(module);
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
    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "ModuleInformation{" +
               "module=" + module +
               '}';
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     *
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(@NotNull ModuleInformation<?> o) {
        return getModule().getName().compareTo(o.getModule().getName());
    }
}
