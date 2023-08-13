package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.WaitingInfoDto;
import com.example.demo.model.Participant;
import com.example.demo.repository.ParticipantRepository;
import com.example.demo.repository.WaitingQueueRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public String addParticipant(Participant participant) {

        String resultMessage = "Add Particiant : failed";
        String nickName = participant.getNickName();

        List<Participant> list = participantRepository.findAll();

        boolean nickNameExists = list.stream().anyMatch(part -> part.getNickName().equals(nickName));

        if (nickNameExists) {
            resultMessage = "This nickName is already exist";
        }

        participantRepository.save(participant);

        resultMessage = "Add Particiant : success";

        return resultMessage;

    }

}
