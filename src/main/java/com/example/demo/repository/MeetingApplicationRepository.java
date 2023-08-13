package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enum_model.SwimClass;
import com.example.demo.model.MeetingApplication;

@Repository
public interface MeetingApplicationRepository extends JpaRepository<MeetingApplication, Integer> {

    public List<MeetingApplication> findAll();

    public MeetingApplication save(MeetingApplication entity);

    public void delete(MeetingApplication entity);

    public List<MeetingApplication> findBySwimClassOrderByIdAsc(SwimClass swimClass);

}
