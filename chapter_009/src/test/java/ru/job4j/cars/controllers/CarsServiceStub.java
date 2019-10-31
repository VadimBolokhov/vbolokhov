package ru.job4j.cars.controllers;

import ru.job4j.cars.models.*;
import ru.job4j.cars.service.CarsService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A stub implementation of CarsService interface for testing.
 * @author Vadim Bolokhov
 */
public enum CarsServiceStub implements CarsService {

    INSTANCE;

    private List<Car> cars = new LinkedList<>();

    private Set<Make> makes = new HashSet<>();

    private Set<Model> models = new HashSet<>();

    private Set<Gearbox> gearboxes = new HashSet<>();

    private Map<String, Integer> nextId = new HashMap<>();

    CarsServiceStub() {
        Stream.of("car", "model", "make", "gearbox")
                .forEach(entity -> this.nextId.put(entity, 0));
    }

    @Override
    public int saveCar(Car car) {
        Model model = car.getModel();
        if (this.models.add(model)) {
            model.setId(this.getId("model"));
        }
        Make make = model.getMake();
        if (this.makes.add(make)) {
            make.setId(this.getId("make"));
        }
        this.gearboxes.add(car.getGearbox());
        int carId = this.getId("car");
        car.setId(carId);
        this.cars.add(car);
        return carId;
    }

    private int getId(String entity) {
        return this.nextId.merge(entity, 1, Integer::sum);
    }

    @Override
    public void update(Car car) {
        for (int i = 0; i < this.cars.size(); i++) {
            if (car.getId() == this.cars.get(i).getId()) {
                this.cars.set(i, car);
                break;
            }
        }
    }

    @Override
    public List<Car> getAllCars() {
        return this.cars;
    }

    @Override
    public List<Car> getUnsoldCars() {
        return this.cars.stream()
                .filter(car -> !car.isSold())
                .collect(Collectors.toList());
    }

    @Override
    public List<Color> getColors() {
        return Arrays.asList(Color.values());
    }

    @Override
    public Optional<Car> getCarById(int id) {
        return this.cars.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
    }

    @Override
    public List<Make> getMakes() {
        return new ArrayList<>(this.makes);
    }

    @Override
    public List<Model> getModelsByMake(int id) {
        return this.models.stream()
                .filter(model -> model.getMake().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public List<Gearbox> getGearboxes() {
        return new ArrayList<>(this.gearboxes);
    }
}
