package ru.job4j.crud.models;

import java.time.LocalDate;
import java.util.Objects;

/**
 * User data model.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class User {
    /** User id */
    private final String id;
    /** User name */
    private final String name;
    /** User login */
    private final String login;
    /** User's password */
    private final String password;
    /** User's e-mail */
    private final String email;
    /** Creation date*/
    private final LocalDate createDate;
    /** User's country */
    private final String country;
    /** User's city */
    private final String city;

    private Role role;

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.login = builder.login;
        this.password = builder.password;
        this.email = builder.email;
        this.createDate = builder.createDate;
        this.role = builder.role;
        this.country = builder.country;
        this.city = builder.city;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Role getRole() {
        return role;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", login='" + login + '\''
                + ", email='" + email + '\''
                + ", createDate=" + createDate
                + '}';
    }

    public static class Builder {
        private String login;
        private String password;
        private String id;
        private String name;
        private String email;
        private LocalDate createDate;
        private Role role;
        private String country;
        private String city;

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder createDate(LocalDate createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
