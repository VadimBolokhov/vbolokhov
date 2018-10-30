package ru.job4j.servlets;

/**
 * Person class for JSON task.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Person {
    private String firstname;
    private String lastname;
    private String sex;
    private String desc;

    public Person() { }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
