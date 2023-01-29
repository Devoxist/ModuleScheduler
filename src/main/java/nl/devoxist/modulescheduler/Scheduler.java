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

import nl.devoxist.modulescheduler.resolvers.DependencyResolver;
import nl.devoxist.modulescheduler.runner.StageRunner;
import nl.devoxist.modulescheduler.settings.ModuleInformation;
import nl.devoxist.modulescheduler.settings.ModuleSchedulerInformation;
import nl.devoxist.modulescheduler.settings.ModuleSchedulerSettings;
import nl.devoxist.modulescheduler.stage.Stage;
import nl.devoxist.modulescheduler.stage.Staging;
import org.jetbrains.annotations.Contract;

import java.util.Map;
import java.util.Set;

/**
 * {@link Scheduler} is an object that is scheduling and executing the modules that are given in the settings of
 * {@link ModuleScheduler}.
 *
 * @author Dev-Bjorn
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Scheduler {

    /**
     * The scheduler and managing object of the loading process. This contains all the settings and some other methods.
     *
     * @since 1.0.0
     */
    private final ModuleScheduler moduleScheduler;
    /**
     * The settings of the current running scheduler.
     *
     * @since 1.0.0
     */
    private final ModuleSchedulerSettings moduleSchedulerSettings;
    /**
     * The information of the current running scheduler.
     *
     * @since 1.0.0
     */
    private final ModuleSchedulerInformation moduleSchedulerInformation;

    /**
     * Construct and run the loading algorithm of the given {@link ModuleScheduler}. It will automatically resolve the
     * dependencies, order the modules in the correct load order and run the modules in the correct order.
     *
     * @param moduleScheduler The scheduler of which the modules are retrieved.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    public Scheduler(ModuleScheduler moduleScheduler) {
        this.moduleScheduler = moduleScheduler;
        this.moduleSchedulerSettings = new ModuleSchedulerSettings();
        this.moduleSchedulerInformation = new ModuleSchedulerInformation(moduleSchedulerSettings);

        Thread thread = new Thread(this::run);
        thread.start();
    }

    /**
     * Run the full process of resolving, staging, loading, constructing and running of the {@link Module}s. This will
     * do all the processing for the {@link Module}s. This will automatically runned when constructing this object in
     * another {@link Thread} than the main thread.
     *
     * @since 1.0.0
     */
    private void run() {
        this.moduleScheduler.updateSettings(this.moduleSchedulerSettings);

        Set<Class<? extends Module>> modules = this.moduleSchedulerSettings.getModules();
        Map<Class<? extends Module>, ModuleInformation<?>>
                moduleInformationSet = DependencyResolver.resolveDependencies(modules);

        this.moduleSchedulerInformation.setModuleInformationMap(moduleInformationSet);
        Set<Stage> stages = Staging.stageModules(this.moduleSchedulerInformation);

        StageRunner.runStages(this.moduleSchedulerSettings, this.moduleScheduler, stages);
    }

}
