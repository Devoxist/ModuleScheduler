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
import nl.devoxist.modulescheduler.ModuleScheduler;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * {@link ModuleSchedulerInformation} is a {@link Class} that contains information over the current running
 * {@link ModuleScheduler}.
 *
 * @author Dev-Bjorn
 * @version 1.2.0
 * @since 1.0.0
 */
public final class ModuleSchedulerInformation {
    /**
     * The settings of the current running {@link ModuleScheduler}.
     *
     * @since 1.0.0
     */
    private final ModuleSchedulerSettings moduleSchedulerSettings;
    /**
     * The {@link Map} of the modules with there linked {@link ModuleInformation}.
     *
     * @since 1.0.0
     */
    private Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap =
            new TreeMap<>(Comparator.comparing(Class::getName));

    /**
     * Constructs a new {@link ModuleInformation} object, with the {@link ModuleSchedulerSettings} of the current
     * running {@link ModuleScheduler}.
     *
     * @param moduleSchedulerSettings The {@link ModuleSchedulerSettings} of the current running
     *                                {@link ModuleScheduler}.
     *
     * @since 1.0.0
     */
    public ModuleSchedulerInformation(ModuleSchedulerSettings moduleSchedulerSettings) {
        this.moduleSchedulerSettings = moduleSchedulerSettings;
    }

    /**
     * Get the {@link ModuleSchedulerSettings} of the current running {@link ModuleScheduler}.
     *
     * @return The {@link ModuleSchedulerSettings} of the current running {@link ModuleScheduler}.
     *
     * @since 1.0.0
     */
    public ModuleSchedulerSettings getModuleSchedulerSettings() {
        return moduleSchedulerSettings;
    }

    /**
     * Set the {@link Map} of the modules with there linked {@link ModuleInformation}.
     *
     * @param moduleInformationMap The {@link Map} of the modules with there linked {@link ModuleInformation}.
     *
     * @since 1.0.0
     */
    public void setModuleInformationMap(Map<Class<? extends Module>, ModuleInformation<?>> moduleInformationMap) {
        this.moduleInformationMap = moduleInformationMap;
    }

    /**
     * Get the {@link Map} of the modules with there linked {@link ModuleInformation}.
     *
     * @return The {@link Map} of the modules with there linked {@link ModuleInformation}.
     *
     * @since 1.0.0
     */
    public Map<Class<? extends Module>, ModuleInformation<?>> getModuleInformationMap() {
        return Collections.unmodifiableMap(moduleInformationMap);
    }

}
