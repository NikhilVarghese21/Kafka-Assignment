package com.knoldus;

public class User {
    private int userId;
    private String name;
    private int age;
    private String course;

    public User(int userId, String name, int age, String course) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public int getUserId()
    {
        return userId;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getCourse()
    {
        return course;
    }
}
