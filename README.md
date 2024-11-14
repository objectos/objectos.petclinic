# Objectos PetClinic

A sample [Objectos Way](https://github.com/objectos/objectos.way) web application
inspired by the [Spring PetClinic Application](https://github.com/spring-projects/spring-petclinic).

## How to run (dev mode)

To build and run the Objectos PetClinic application you need:

- JDK 21 (or later)
- GNU Make 4.4.1 (or later)
- GNU Wget 1.24.5 (or later)

Clone, build and run:

```shell
git clone git@github.com:objectos/objectos.petclinic.git
cd objectos.petclinic
make dev
```

On the console, wait for the following messages:

```
INFO [main] objectos.petclinic.StartDev : Total time [ms] 564
INFO [HTTP] objectos.way.Http.Server    : Started ServerSocket[addr=localhost/127.0.0.1,localport=8004]
```

Then visit [http://localhost:8004](http://localhost:8004).

## Importing the project in your IDE

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
