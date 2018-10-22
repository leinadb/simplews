package com.example.simplews.dao;

import com.example.simplews.model.SomethingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomethingModelRepository extends JpaRepository<SomethingModel, String> {

}
