# Module Scheduler

[![Maven Central](https://img.shields.io/maven-central/v/nl.devoxist/module-scheduler.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22nl.devoxist%22%20AND%20a:%22module-scheduler%22)
[![GitHub license](https://img.shields.io/github/license/Devoxist/ModuleScheduler)](https://github.com/Devoxist/ModuleScheduler/blob/master/LICENSE)

### Getting Started

How to install this API? For all possible options to install this API [maven
central](https://search.maven.org/artifact/nl.devoxist/module-scheduler) and select the version.

#### Maven

Add this to your `pom.xml`.

```xml

<dependency>
    <groupId>nl.devoxist</groupId>
    <artifactId>module-scheduler</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Usage

This API can be used to automatically order tasks/modules in the correct order.

### How to use this API?

Firstly create as class that implements `ModuleScheduler`. This is the controller of all the modules.

```java
public class ModuleSchedulerProcess implements ModuleScheduler {

    @Override
    public void updateSettings(@NotNull ModuleSchedulerSettings settings) {
    }


    @Override
    public void beforeModuleExecute(Module module) {
        // Put here your stuff
    }

    @Override
    public void afterModuleExecute(Module module) {
        // Put here your stuff
    }
}
```

Then you want to add some modules to the controllers. The modules can be created by implementing `Module`.

```java
public class ModuleA implements Module {

    @Override
    public void onExecute() {
        // Put here your stuff
    }
}
```

To mark a dependency link to another module use the `@Dependency` annotation above the class of the `Module`. This
annotation is needed to order the Modules.

```java

@Dependency(ModuleA.class)
public class ModuleB implements Module {

    public ModuleB(ModuleA moduleA) {
        // Put here your stuff
    }


    @Override
    public void onExecute() {
        // Put here your stuff
    }
}
```

To mark a multiple dependency links to another module use the `@Dependency` annotation above the class of the `Module`.
This annotation is needed to order the Modules.

```java

@Dependency({ModuleB.class, ModuleA.class})
public class ModuleC implements Module {

    public ModuleC(ModuleA moduleA, ModuleB moduleB) {
        // Put here your stuff
    }

    @Override
    public void onExecute() {
        // Put here your stuff
    }
}
```

How to add modules to the controller, the scheduler? This can be done to change the content of the
method, `ModuleScheduler#updateSettings`. Add a line with `ModuleSchedulerSettings#addModule`. It is important to add
all the tasks/modules that need to be loaded. Only the modules that are added can be loaded.

```java
    @Override
    public void updateSettings(@NotNull ModuleSchedulerSettings settings){
        settings.addModule(ModuleA.class);
        settings.addModule(ModuleB.class);
        settings.addModule(ModuleC.class);
    }
```

How to start the process? Construct the `Scheduler` class with the inherited `ModuleScheduler` that you want to load.
After calling this construction the modules will automatically be runned in correct order. These methods will run, when
a module is being executed, in the following
order `ModuleScheduler#beforeModuleExecute` -> `Module#onExecute` -> `ModuleScheduler#afterModuleExecute`.

```java
new Scheduler(new ModuleSchedulerProcess());
```

#### Module Scheduler Settings

#### Modules

The modules which are going to be loaded can be added through the `ModuleSchedulerSettings#addModule` method. The
modules can be added through their class path or their `Class`. 

#### Input Registers

The registers that are used in to construct the modules. These modules need to have a constructor with the parameters
types registered in the input registries and/or can be the modules that are loaded. These registries can be registered
through the `ModuleSchedulerSettings#addRegisters` method. Note: The modules that are getting loaded do not have to be
registered in the input registers.

#### Output Register

This is a register that contains the constructed modules that are loaded inside the loading process.

#### Logger

This is the logger of the process that is going to be used to output the messages of process.

### Contributors

+ Dev-Bjorn
