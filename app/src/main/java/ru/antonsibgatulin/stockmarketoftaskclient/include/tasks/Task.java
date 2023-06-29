package ru.antonsibgatulin.stockmarketoftaskclient.include.tasks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Task {

    private Long id;

    private String name;
    private String description;
    private Integer price;
    private Integer betterPrice;


    @JsonIgnore
    private String typeTasks;

    private Long timeCreate;
    private Long countLike;
    private Long countView;
    private Integer countRespond;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getBetterPrice() {
        return betterPrice;
    }

    public void setBetterPrice(Integer betterPrice) {
        this.betterPrice = betterPrice;
    }

    public String getTypeTasks() {
        return typeTasks;
    }

    public void setTypeTasks(String typeTasks) {
        this.typeTasks = typeTasks;
    }

    public Long getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Long timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Long getCountLike() {
        return countLike;
    }

    public void setCountLike(Long countLike) {
        this.countLike = countLike;
    }

    public Long getCountView() {
        return countView;
    }

    public void setCountView(Long countView) {
        this.countView = countView;
    }

    public Integer getCountRespond() {
        return countRespond;
    }

    public void setCountRespond(Integer countRespond) {
        this.countRespond = countRespond;
    }
}
