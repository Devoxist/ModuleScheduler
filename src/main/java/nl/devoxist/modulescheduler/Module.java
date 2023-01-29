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

package nl.devoxist.modulescheduler;

import nl.devoxist.modulescheduler.settings.ModuleSchedulerSettings;
import nl.devoxist.typeresolver.constructor.ConstructorResolver;
import nl.devoxist.typeresolver.register.Register;

/**
 * A module is an object that runs when called. This can be compared with a task. A module of the
 * {@link ModuleScheduler} will be sorted to run in the correct module order.
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Module {

    /**
     * The executor method of the module. This will be executing after the module is constructed by the
     * {@link ConstructorResolver#initClass(Class)}. This will be constructed with the specified registers in
     * {@link ModuleSchedulerSettings#addRegisters(Register...)}.
     *
     * @since 1.0.0
     */
    void onExecute();

}
