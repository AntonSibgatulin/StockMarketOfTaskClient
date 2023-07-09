package ru.antonsibgatulin.stockmarketoftaskclient.include.user;

import java.util.List;

import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.ActionTask;

public class User {


    private Long id;
    private String email;
    private boolean ban;
    private boolean delete;
    private Role role;
    private Profile profile;

    private List<ActionTask> actionTasks;
    public User(Long id, String email, boolean ban, boolean delete, Role role, Profile profile) {
        this.id = id;
        this.email = email;
        this.ban = ban;
        this.delete = delete;
        this.role = role;
        this.profile = profile;
    }
    public User(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<ActionTask> getActionTasks() {
        return actionTasks;
    }

    public void setActionTasks(List<ActionTask> actionTasks) {
        this.actionTasks = actionTasks;
    }
}

