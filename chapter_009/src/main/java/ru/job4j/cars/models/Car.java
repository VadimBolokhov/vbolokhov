package ru.job4j.cars.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.util.Objects;

/**
 * Car entity.
 * @author Vadim Bolokhov
 */
@Entity
@Table(name = "car")
public class Car {
    /** Car id */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    /** Car make */
    private String make;
    /** Car body type */
    @ManyToOne
    @JoinColumn(name = "body_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Body body;
    /** Car engine type */
    @ManyToOne
    @JoinColumn(name = "eng_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Engine engine;
    /** Car transmission type */
    @ManyToOne
    @JoinColumn(name = "trans_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Transmission transmission;

    public Car() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Body getBody() {
        return this.body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Engine getEngine() {
        return this.engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return this.transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id
                && Objects.equals(make, car.make)
                && Objects.equals(body, car.body)
                && Objects.equals(engine, car.engine)
                && Objects.equals(transmission, car.transmission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, body, engine, transmission);
    }

    @Override
    public String toString() {
        return "Car{"
                + "id=" + this.id
                + ", make='" + this.make + '\''
                + ", body=" + this.body.getStyle()
                + ", engine=" + this.engine.getType()
                + ", transmission=" + this.transmission.getType() + '}';
    }
}
