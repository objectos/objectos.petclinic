# Objectos PetClinic

A sample web application written in pure Java.
It showcases:

- [Objectos Way](https://github.com/objectos/objectos.way): a library for writing web applications in pure Java; and
- [Objectos MK](https://github.com/objectos/objectos.mk): build Java projects using Make and Makefiles.

Objectos PetClinic is inspired by the [Spring PetClinic Application](https://github.com/spring-projects/spring-petclinic).

# Quick start

To build and run Objectos PetClinic you will need:

- JDK 21 (or later)
- GNU Make 4.4.1 (or later)
- GNU Wget 1.24.5 (or later)

Clone the repository and point to the latest tag:

```shell
git clone --branch v001 git@github.com:objectos/objectos.petclinic.git
cd objectos.petclinic
```

Start the application in dev mode:

```shell
make dev
```

On the console, wait for the following messages:

```
INFO [main] objectos.petclinic.StartDev : Total time [ms] 564
INFO [HTTP] objectos.way.Http.Server    : Started ServerSocket[addr=localhost/127.0.0.1,localport=8004]
```

Visit [http://localhost:8004](http://localhost:8004) on your Web browser.

Press `Ctrl+C` on the console to stop the application.

# Slow start

Thank you for your interest in Objectos PetClinic!

The Objectos PetClinic is an ongoing project whose main purpose is two-fold:

- to serve as a showcase for both Objectos Way and Objectos MK; and
- to drive the development of both Objectos Way and Objectos MK.

The objective of this section is:

- show you how an Objectos Way application is organized;
- show you the features Objectos Way offers to the developer; and
- to serve as an example and inspiration for you to try Objectos Way on your own. 

## System requirements

In order to build, run and work with the Objectos PetClinic you will need:

- **Linux**. The build is currently only tested on Linux.
  If you have success building and running on a Mac or Windows machine please let us know;
- **JDK 21** (or later). This is strictly required as Objectos Way requires it;
- **GNU Make 4.4.1** (or later). You might be able to build the project with an earlier version of GNU Make.
  Version 4.4.1 was listed here as it is the one used during the development of this project; and
- **GNU Wget 1.24.5** (or later) You might be able to use an earlier version of GNU Wget.
  Version 1.24.5 was listed here as it is the one used during the development of this project.
  
## Import the project in your IDE

Please note that IDE support is a work in progress.
If you run into any problem, please [report an issue](https://github.com/objectos/objectos.petclinic/issues).

### Eclipse IDE

To import the project into Eclipse IDE:

```shell
git clone git@github.com:objectos/objectos.petclinic.git
cd objectos.petclinic
make eclipse
```

The last command will generate the Eclipse required files.

Open the project via `File -> Open Projects from File System...` and select the root of the cloned repository.

### IntelliJ IDEA

To import the project into IntelliJ:

```shell
git clone git@github.com:objectos/objectos.petclinic.git
cd objectos.petclinic
make idea
```

The last command will generate the IntelliJ required files.

Open the project by selecting the root of the cloned repository and then:

1. navigate to a Java file from the project, e.g. `main/module-info.java`
1. IntelliJ will ask you to setup a JDK for the project
1. select a JDK 21 or later.

### VS Code

To import the project into VS Code:

```shell
git clone git@github.com:objectos/objectos.petclinic.git
cd objectos.petclinic
make vscode
```

The last command will generate the VS code required files.

Open the project via `File -> Open Folder` and select the root of the cloned repository. 

## Features

The application is under development. It currently features:

- [last visits listing](https://github.com/objectos/objectos.petclinic/blob/main/main/objectos/petclinic/site/SiteWelcome.java); and
- [owners listing](https://github.com/objectos/objectos.petclinic/blob/main/main/objectos/petclinic/site/Owners.java).

## License

Copyright (C) 2023-2024 [Objectos Software LTDA](https://www.objectos.com.br)

Licensed under the Apache License, Version 2.0.
