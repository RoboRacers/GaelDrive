# GaelDrive: Monte Carlo Localization for FTC

[![Gradle Build and Test](https://github.com/RoboRacers/GaelDrive/actions/workflows/gradle.yml/badge.svg)](https://github.com/RoboRacers/GaelDrive/actions/workflows/gradle.yml)

GaelDrive is a autonomous localization library for FTC that uses Monte Carlo Localization (MCL) to achieve the best possible autonomous cycles possible. 

Works with [Roadrunner](https://github.com/acmerobotics/road-runner), a popular FTC autonomous navigation library, out of the box, but can work with any other autonomous library. 

Full installation releasing September 9th.

## Building and testing

GaelDrive is a standard Gradle multi-project build targeting Java 11. From the repo root:

```sh
./gradlew build   # compiles the library and runs the test suite
./gradlew test    # runs just the test suite
```

Tests live under `lib/src/test/java` and use JUnit 5. CI runs the same `./gradlew build` on every push and pull request against `main` and `dev`.

Created by Tarun Rajesh and Vikram Kommera from FTC Team 16481 RoboRacers, in collaboration with [GaelSuite](https://github.com/GaelSuite).
