package ru.job4j.cars.models;

import javax.persistence.*;

/**
 * User roles.
 * @author Vadim Bolokhov
 */
@Entity
@Table(name = "roles")
public enum Role {
    AMDIN, USER
}
