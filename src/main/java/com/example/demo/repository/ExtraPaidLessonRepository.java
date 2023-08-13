package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ExtraPaidLesson;
import com.example.demo.model.Participant;

@Repository
public interface ExtraPaidLessonRepository extends JpaRepository<ExtraPaidLesson, Integer> {

    public List<ExtraPaidLesson> findAll();

    public ExtraPaidLesson save(ExtraPaidLesson entity);

    public void delete(ExtraPaidLesson entity);
}
