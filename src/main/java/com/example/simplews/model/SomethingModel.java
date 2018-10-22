package com.example.simplews.model;

import javax.validation.constraints.NotNull;

public class SomethingModel {
    @NotNull
    private String model;
    private String id;

    public SomethingModel(String model, String id) {
        this.model = model;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
