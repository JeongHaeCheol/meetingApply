package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.enum_model.Role;
import com.example.demo.enum_model.SwimClass;
import com.example.demo.model.MeetingApplication;
import com.example.demo.model.Participant;
import com.example.demo.model.WaitingQueue;
import com.example.demo.repository.MeetingApplicationRepository;
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
public class MeetingApplicationService {

    // 메모리말고 DB에서 처리하자
    // private List<MeetingApplication> waitingQueue = new ArrayList<>();

    private final MeetingApplicationRepository meetingApplicationRepository;

    private final WaitingQueueRepository waitingQueueRepository;

    private final ParticipantRepository participantRepository;

    public MeetingApplication checkMyMeetingApplication(Integer participantId) {

        Participant participant = participantRepository.findById(participantId).orElseThrow();

        List<MeetingApplication> meetingApplications = meetingApplicationRepository.findAll();

        for (MeetingApplication meetingApplication : meetingApplications) {

            if (meetingApplication.getMainParticipant() == null) {
                continue;
            }

            if (meetingApplication.getMainParticipant().getId() == participantId) {
                return meetingApplication;
            }
        }

        return null;
    }

    public List<MeetingApplication> addWaiting(MeetingApplication meetingApplication) {

        setWaitingNumber(meetingApplication);

        List<MeetingApplication> result = meetingApplicationRepository.findAll();

        return result;
    }

    @Transactional
    public List<MeetingApplication> deleteWaiting(Integer id, Integer participantId) {

        Participant participant = participantRepository.findById(participantId).orElseThrow();

        MeetingApplication meetingApplication = meetingApplicationRepository.findById(id).orElseThrow();

        List<WaitingQueue> allWaitingQueues = waitingQueueRepository.findAll();

        SwimClass applicationClass = meetingApplication.getSwimClass();

        if (Role.USER.equals(participant.getRole()) && meetingApplication.getMainParticipant().getId() != id) {
            return null;
        }

        for (WaitingQueue waitingQueue : allWaitingQueues) {

            if (waitingQueue.getSwimClass().equals(applicationClass)) {
                Integer waitingNumber = waitingQueue.getAvailableLastNumber();

                waitingQueue.setApplicationCount(waitingNumber - 1);
                waitingQueue.setAvailableLastNumber(waitingNumber + 1);
                meetingApplicationRepository.delete(meetingApplication);
            }
        }
        List<MeetingApplication> result = meetingApplicationRepository.findAll();

        return result;
    }

    @Transactional
    public void setWaitingNumber(MeetingApplication meetingApplication) {

        // list로 구현하는게 비효율적이지만 일단은 구현
        // 나중에 고친다면 Map으로
        List<WaitingQueue> allWaitingQueues = waitingQueueRepository.findAll();

        SwimClass applicationClass = meetingApplication.getSwimClass();

        for (WaitingQueue waitingQueue : allWaitingQueues) {

            // main 참가자 먼저
            if (waitingQueue.getSwimClass().equals(applicationClass)) {

                Integer waitingNumber = waitingQueue.getAvailableLastNumber() + 1;
                // 신청서에 대기번호 초기화
                meetingApplication.setWaitingNumber(waitingNumber);
                meetingApplicationRepository.save(meetingApplication);

                // 해당 반의 신청인원 증가
                waitingQueue.setApplicationCount(waitingQueue.getApplicationCount() + 1);
                waitingQueue.setAvailableLastNumber(waitingNumber);
                waitingQueueRepository.save(waitingQueue);

            }

            // // 동반 참가자
            // if (waitingQueue.getSwimClass().equals(accompanyingApplicationClass)) {

            // // 신청서에 대기번호 초기화
            // meetingApplication.setAccompanyingWaitingNumber(waitingQueue.getApplicationCount()
            // + 1);
            // meetingApplicationRepository.save(meetingApplication);

            // // 해당 반의 신청인원 증가
            // waitingQueue.setApplicationCount(waitingQueue.getApplicationCount() + 1);
            // waitingQueueRepository.save(waitingQueue);
            // }

        }
    }

}
