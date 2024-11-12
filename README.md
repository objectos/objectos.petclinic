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
We will have Make targets to generate IDE configuration files.

### Eclipse IDE

TODO

### IntelliJ IDEA

TODO

### VS Code

TODO

## Features

The application is under development. It currently features:

- [last visits listing](https://github.com/objectos/objectos.petclinic/blob/main/main/objectos/petclinic/site/SiteWelcome.java); and
- [owners listing](https://github.com/objectos/objectos.petclinic/blob/main/main/objectos/petclinic/site/Owners.java).

## License

Copyright (C) 2023-2024 [Objectos Software LTDA](https://www.objectos.com.br)

Licensed under the Apache License, Version 2.0.
