package com.example.simplews.dao;

import com.example.simplews.model.Some;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeRepository extends JpaRepository<Some, String> {
}
