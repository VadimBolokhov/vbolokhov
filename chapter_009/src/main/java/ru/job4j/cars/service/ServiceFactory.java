package ru.job4j.cars.service;

/**
 * Store service factory.
 * @author Vadim Bolokhov
 */
public interface ServiceFactory {
    CarsService getCarsService();
    Validate getValidateService();
}
