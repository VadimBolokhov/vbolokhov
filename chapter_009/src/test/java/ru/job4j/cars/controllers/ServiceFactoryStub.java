package ru.job4j.cars.controllers;

import ru.job4j.cars.service.CarsService;
import ru.job4j.cars.service.ServiceFactory;
import ru.job4j.cars.service.Validate;

/**
 * Stub service factory for testing.
 * @author Vadim Bolokhov
 */
public class ServiceFactoryStub implements ServiceFactory {

    private CarsService carsService;

    private Validate validateService;

    @Override
    public CarsService getCarsService() {
        return this.carsService;
    }

    @Override
    public Validate getValidateService() {
        return this.validateService;
    }

    public ServiceFactoryStub setCarsService(CarsService carsService) {
        this.carsService = carsService;
        return this;
    }

    public ServiceFactoryStub setValidateService(Validate validateService) {
        this.validateService = validateService;
        return this;
    }
}
