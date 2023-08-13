package com.example.demo.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.WaitingInfoDto;
import com.example.demo.enum_model.SwimClass;
import com.example.demo.model.MeetingApplication;
import com.example.demo.model.WaitingQueue;
import com.example.demo.repository.MeetingApplicationRepository;
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
public class WaitingQueueService {

    private final WaitingQueueRepository waitingQueueRepository;

    private final MeetingApplicationRepository meetingApplicationRepository;

    // waitingQueue를 활용하지 않는 방식
    // 전체 리스트로 조회
    public WaitingInfoDto getWaitingInfoDto(SwimClass swimClass) {

        WaitingQueue waitingQueue = waitingQueueRepository.findBySwimClass(swimClass);
        List<MeetingApplication> list = meetingApplicationRepository.findBySwimClassOrderByIdAsc(swimClass);

        // list.sort(Comparator.comparing(MeetingApplication::getId,
        // Comparator.nullsLast(Integer::compareTo)));

        Integer capacity = waitingQueue.getCapacity();
        int order = 0;
        int lastNumber = 0;
        for (MeetingApplication target : list) {
            order++;
            if (order == capacity) {
                lastNumber = target.getId();
                break;
            }
        }

        WaitingInfoDto result = new WaitingInfoDto(lastNumber, list.size(), list);

        return result;
    }

}
