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

/**
 * {@link ModuleScheduler} is an object that is scheduling and managing the modules that are given in
 * {@link #updateSettings(ModuleSchedulerSettings)}.
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ModuleScheduler {

    /**
     * Update the settings of this scheduler.
     *
     * @param settings The settings of the scheduler.
     *
     * @since 1.0.0
     */
    void updateSettings(ModuleSchedulerSettings settings);

    /**
     * Called before the {@link Module} or task is executed.
     *
     * @param module The {@link Module} that is going to be executed.
     *
     * @since 1.0.0
     */
    void beforeModuleExecute(Module module);


    /**
     * Called after the {@link Module} or task is executed.
     *
     * @param module The {@link Module} that has been executed.
     *
     * @since 1.0.0
     */
    void afterModuleExecute(Module module);
}
