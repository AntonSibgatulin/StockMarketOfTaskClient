package ru.antonsibgatulin.stockmarketoftaskclient.include.dilog;

import ru.antonsibgatulin.stockmarketoftaskclient.include.user.User;

public class Dilog {

    private Long id;
    private User user_1;
    private User user_2;
    private String name;

    public Dilog(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser_1() {
        return user_1;
    }

    public void setUser_1(User user_1) {
        this.user_1 = user_1;
    }

    public User getUser_2() {
        return user_2;
    }

    public void setUser_2(User user_2) {
        this.user_2 = user_2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
