# fiberSimulation

This project is a set of tools for the purpose of doing numerical simulation and computational analysis of doped optical fibers.

The project has been developed as a web server using Java/Spring, which can be accessed through a REST API or a web-based graphical interface.

## Roadmap

These are the tasks I plan on working on in the future to improve this project:

- Adding unit tests with JUnit, at least for the core model components that do the heavy lifting in terms of computation. Some parts of the code are difficult to unit test properly due to the use of random number generation.
- Adding support for input parameters based on interpolated experimental data read from CSV files.
- Adding comments and documentation to make the algorithms used easier to understand.
- Splitting the architecture into microservices rather than a monolithic project.
- Improving the REST API interface to allow a greater degree of customization of the configuration of optical elements to simulate.
- Adding a frontend web to consume the API and provide a better UX.