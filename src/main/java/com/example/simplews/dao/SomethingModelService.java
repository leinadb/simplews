package com.example.simplews.dao;

import com.example.simplews.model.SomethingModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class SomethingModelService {

    private static List<SomethingModel> list = new ArrayList<>();

    static {
        list.add(new SomethingModel("sth1", "1"));
        list.add(new SomethingModel("sth2", "2"));
        list.add(new SomethingModel("sth3", "3"));
    }

    public SomethingModel save(SomethingModel model) {
        list.add(model);
        return model;
    }

    public List<SomethingModel> findAll() {
        return this.list;
    }

    public Optional<SomethingModel> findOne(String id) {
        return list.stream().filter($ -> id.equals($.getId())).findFirst();
    }

    public SomethingModel delete(String id) {
        SomethingModel removed = null;
        for (int i = 0; i < list.size(); i++) {
            if (id.equals(list.get(i).getId())) {
                removed = list.remove(i);
            }
        }
        return removed;
    }
}
