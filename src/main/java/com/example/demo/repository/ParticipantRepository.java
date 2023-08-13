package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Participant;
import com.example.demo.model.WaitingQueue;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    public List<Participant> findAll();

    public Participant save(Participant entity);

    public void delete(Participant entity);

    public Participant findByNickName(String nickName);

}
