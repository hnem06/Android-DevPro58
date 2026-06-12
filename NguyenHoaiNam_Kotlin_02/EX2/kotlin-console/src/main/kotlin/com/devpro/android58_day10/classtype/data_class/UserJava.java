package com.devpro.android58_day10.classtype.data_class;

public class UserJava {
    private String name;
    private int age;

    public UserJava(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserJava{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
