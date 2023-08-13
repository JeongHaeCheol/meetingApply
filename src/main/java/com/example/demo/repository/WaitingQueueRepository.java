package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enum_model.SwimClass;
import com.example.demo.model.MeetingApplication;
import com.example.demo.model.WaitingQueue;

@Repository
public interface WaitingQueueRepository extends JpaRepository<WaitingQueue, Integer> {


    public Long countAllBy();
    public List<WaitingQueue> findAll();

    public WaitingQueue findBySwimClass(SwimClass swimClass);

    public WaitingQueue save(WaitingQueue entity);

    public void delete(WaitingQueue entity);

}
