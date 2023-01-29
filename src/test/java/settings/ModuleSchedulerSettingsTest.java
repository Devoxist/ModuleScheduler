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

package settings;

import modules.ModuleA;
import nl.devoxist.modulescheduler.exception.ModuleException;
import nl.devoxist.modulescheduler.settings.ModuleSchedulerSettings;
import nl.devoxist.typeresolver.register.Register;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@TestOnly
public class ModuleSchedulerSettingsTest {

    @Test
    public void addModuleTest() {
        ModuleSchedulerSettings moduleSchedulerSettings = new ModuleSchedulerSettings();

        Assertions.assertTrue(moduleSchedulerSettings.getModules().isEmpty());

        moduleSchedulerSettings.addModule(ModuleA.class);

        Assertions.assertTrue(moduleSchedulerSettings.getModules().contains(ModuleA.class));
    }

    @Test
    public void addModuleTest2() throws ClassNotFoundException {
        ModuleSchedulerSettings moduleSchedulerSettings = new ModuleSchedulerSettings();

        Assertions.assertTrue(moduleSchedulerSettings.getModules().isEmpty());

        moduleSchedulerSettings.addModule("modules.ModuleA");

        Assertions.assertTrue(moduleSchedulerSettings.getModules().contains(ModuleA.class));
    }

    @Test
    public void addModuleTest3() {
        ModuleSchedulerSettings moduleSchedulerSettings = new ModuleSchedulerSettings();

        Assertions.assertTrue(moduleSchedulerSettings.getModules().isEmpty());

        moduleSchedulerSettings.addModule(ModuleA.class);
        moduleSchedulerSettings.addModule(ModuleA.class);

        Assertions.assertEquals(1, moduleSchedulerSettings.getModules().size());
        Assertions.assertTrue(moduleSchedulerSettings.getModules().contains(ModuleA.class));
    }

    @Test
    public void addModuleFailed() {
        ModuleSchedulerSettings moduleSchedulerSettings = new ModuleSchedulerSettings();

        Assertions.assertThrows(
                ClassNotFoundException.class,
                () -> moduleSchedulerSettings.addModule("modules.Module"),
                "modules.Module"
        );
    }

    @Test
    public void addModuleFailed2() {
        ModuleSchedulerSettings moduleSchedulerSettings = new ModuleSchedulerSettings();

        Assertions.assertThrows(
                ModuleException.class,
                () -> moduleSchedulerSettings.addModule("modules.ModuleSchedulerProcess"),
                "The 'ModuleSchedulerProcess' is has not implemented Module.class."
        );
    }

    @Test
    public void addRegistersTest() {
        ModuleSchedulerSettings moduleSchedulerSettings = new ModuleSchedulerSettings();
        Register register = new Register();

        Assertions.assertTrue(moduleSchedulerSettings.getRegistries().isEmpty());

        moduleSchedulerSettings.addRegisters(register);

        Assertions.assertEquals(1, moduleSchedulerSettings.getRegistries().size());
        Assertions.assertTrue(moduleSchedulerSettings.getRegistries().contains(register));
    }

    @Test
    public void addRegistersTest2() {
        ModuleSchedulerSettings moduleSchedulerSettings = new ModuleSchedulerSettings();
        Register register = new Register();
        Register register2 = new Register();

        Assertions.assertEquals(register, register2);

        Assertions.assertTrue(moduleSchedulerSettings.getRegistries().isEmpty());

        moduleSchedulerSettings.addRegisters(register, register);
        moduleSchedulerSettings.addRegisters(register2);
        Assertions.assertTrue(moduleSchedulerSettings.getRegistries().contains(register));
        Assertions.assertTrue(moduleSchedulerSettings.getRegistries().contains(register2));
        Assertions.assertEquals(2, moduleSchedulerSettings.getRegistries().size());
    }

    @Test
    public void setOutputRegisterTest() {
        ModuleSchedulerSettings moduleSchedulerSettings = new ModuleSchedulerSettings();
        Register register = new Register();
        moduleSchedulerSettings.setOutputRegister(register);

        Assertions.assertEquals(register, moduleSchedulerSettings.getOutputRegister());
    }

    @Test
    public void constructOutputRegisterTest() {
        ModuleSchedulerSettings moduleSchedulerSettings = new ModuleSchedulerSettings();

        Assertions.assertNotNull(moduleSchedulerSettings.getOutputRegister());
    }


}
