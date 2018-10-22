package com.example.simplews.dao;

import com.example.simplews.model.SomethingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class SomethingModelDaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SomethingModelDaoService.class);

    @PersistenceContext
    private EntityManager manager;


    public SomethingModel save(SomethingModel model) {
        manager.persist(model);
        return model;
    }
}
