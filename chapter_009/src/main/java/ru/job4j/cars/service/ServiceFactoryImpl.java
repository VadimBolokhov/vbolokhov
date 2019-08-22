package ru.job4j.cars.service;

/**
 * An implementation of ServiceFactory interface.
 * @author Vadim Bolokhov
 */
public class ServiceFactoryImpl implements ServiceFactory {

    @Override
    public CarsService getCarsService() {
        return CarStoreService.INSTANCE;
    }

    @Override
    public Validate getValidateService() {
        return ValidateService.INSTANCE;
    }
}
