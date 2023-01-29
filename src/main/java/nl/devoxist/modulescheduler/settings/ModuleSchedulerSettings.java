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
import nl.devoxist.modulescheduler.exception.ModuleException;
import nl.devoxist.typeresolver.register.Register;

import java.util.*;

/**
 * This object gives the options to manipulate the process.
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public class ModuleSchedulerSettings {
    /**
     * {@link Set} of modules that need to be loaded.
     *
     * @since 1.0.0
     */
    private final Set<Class<? extends Module>> modules = new HashSet<>();
    /**
     * {@link Set} of {@link Register}s used in the construction of the {@link Module}s
     *
     * @since 1.0.0
     */
    private final Set<Register> registers = new TreeSet<>();
    /**
     * The output {@link Register} of the {@link Module}. All the loaded modules will be registered in this
     * {@link Register}.
     *
     * @since 1.0.0
     */
    private Register outputRegister = new Register();

    /**
     * Add a {@link Module} to the load process. If the module is added to the process it will be used to order the
     * modules in the correct load order.
     *
     * @param moduleCls The module that is being added to the load process.
     *
     * @since 1.0.0
     */
    public void addModule(Class<? extends Module> moduleCls) {
        this.modules.add(moduleCls);
    }

    /**
     * Add a {@link Module} by its path to the load process. If the module is added to the process it will be used to
     * order the modules in the correct load order.
     *
     * @param modulePath The class path of the module, which need to be added to the load process. The path must lead to
     *                   class and where that class has been implemented with {@link Module}.
     *
     * @throws ClassNotFoundException If the class was not found
     * @throws ModuleException        If the class, that is retrieved of the path, is not extended with {@link Module}.
     * @since 1.0.0
     */
    public void addModule(String modulePath) throws ClassNotFoundException {
        Class<?> cls = this.getClass().getClassLoader().loadClass(modulePath);

        if (!Module.class.isAssignableFrom(cls)) {
            throw new ModuleException("The '%s' is not extended by Module.class.".formatted(cls.getName()));
        }

        Class<? extends Module> moduleCls = cls.asSubclass(Module.class);

        this.modules.add(moduleCls);
    }

    /**
     * Get the {@link Module}s that needs to in the load process.
     *
     * @return The {@link Module}s that needs to be in the load process.
     *
     * @since 1.0.0
     */
    public Set<Class<? extends Module>> getModules() {
        return Collections.unmodifiableSet(modules);
    }

    /**
     * Add registers to the {@link Set} of {@link Register}s that are used in the construction of the {@link Module}s.
     *
     * @param registers The registers that will be added to the {@link Set} of {@link Register}s.
     *
     * @since 1.0.0
     */
    public void addRegisters(Register... registers) {
        this.registers.addAll(Arrays.asList(registers));
    }

    /**
     * Get the {@link Set} of registries that are used to construct the {@link Module}.
     *
     * @return The {@link Set} of registries that are used to construct the {@link Module}.
     *
     * @since 1.0.0
     */
    public Set<Register> getRegistries() {
        return Collections.unmodifiableSet(registers);
    }

    /**
     * Set the output registry of the module dependency sorter. This gives all the loaded modules in a register. If not
     * given there is a new register created while processing the build process of the modules.
     *
     * @param outputRegister The output registry of the modules that are getting loaded.
     *
     * @since 1.0.0
     */
    public void setOutputRegister(Register outputRegister) {
        this.outputRegister = outputRegister;
    }

    /**
     * Get the output registery of the module scheduler. This will contain all the loaded modules after the
     * modules are loaded.
     *
     * @return The output registery of the module scheduler.
     *
     * @since 1.0.0
     */
    public Register getOutputRegister() {
        return this.outputRegister.clone();
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     * <p>
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     * <p>
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     *
     * @return a clone of this instance.
     *
     * @implSpec The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     * <p>
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     * @see Cloneable
     * @since 1.0.0
     */
    public ModuleSchedulerSettings clone() {
        try {
            return (ModuleSchedulerSettings) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
