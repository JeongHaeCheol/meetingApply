package com.example.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import com.example.demo.controller.dto.ApplyDto;
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

    private List<MeetingApplication> classA;
    private List<MeetingApplication> classB;
    private List<MeetingApplication> classC;
    private List<MeetingApplication> classD;


    private final MeetingApplicationRepository meetingApplicationRepository;

    private final WaitingQueueRepository waitingQueueRepository;

    private final ParticipantRepository participantRepository;


    @PostConstruct
    public void init() {
        classA = meetingApplicationRepository.findBySwimClassOrderByIdAsc(SwimClass.HAIL);
        classB = meetingApplicationRepository.findBySwimClassOrderByIdAsc(SwimClass.PADO);
        classC = meetingApplicationRepository.findBySwimClassOrderByIdAsc(SwimClass.JANJAN);
        classD = meetingApplicationRepository.findBySwimClassOrderByIdAsc(SwimClass.NULNUL);
    }


    public List<MeetingApplication> getMeetingApplicationList(SwimClass swimClass) {

            List<MeetingApplication> result = new ArrayList<>();

            switch (swimClass) {
                case HAIL:
                    result = classA;
                    break;
                case PADO:
                    result = classB;
                    break;
                case JANJAN:
                    result = classC;
                    break;
                case NULNUL:
                    result = classD;
                    break;
                default:
                    break;
            }

            return result;
    }




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

    public List<MeetingApplication> addWaiting(ApplyDto applyDto) {

        Participant participant = participantRepository.findByNickName(applyDto.getParticipantNickName());
        if(participant == null) {
            return null;
        }

//        List<MeetingApplication> isExist = meetingApplicationRepository.findByNickname(applyDto.getParticipantNickName());
//        if(!isExist.isEmpty()) {
//        log.info("isExist : {}", meetingApplicationRepository.findByNickname(applyDto.getParticipantNickName()));
//            return null;
//        }

        MeetingApplication meetingApplication = MeetingApplication.builder()
                .approval(false)
                .mainParticipant(participant)
                .nickname(participant.getNickName())
                .swimClass(applyDto.getSwimClass())
                .isAfterMeeting(applyDto.getIsAfterMeeting())
                .inquiry(applyDto.getInquiry())
                .isAttendance(applyDto.getIsAttendance())
                .isPayment(applyDto.getIsPayment())
                .build();

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

        List<MeetingApplication> classX = applicationClass == SwimClass.HAIL ? classA : applicationClass == SwimClass.PADO ? classB : applicationClass == SwimClass.JANJAN ? classC : classD;
        WaitingQueue waitingQueue = allWaitingQueues.stream().filter(wq -> wq.getSwimClass().equals(applicationClass)).findFirst().orElseThrow();

        if (Role.USER.equals(participant.getRole()) && meetingApplication.getMainParticipant().getId() != id) {
            return null;
        }


        //classApp을 id 오름차순으로 정렬
        classX.sort(Comparator.comparing(MeetingApplication::getId, Comparator.nullsLast(Integer::compareTo)));
        //삭제할 meetingApplication의 다음 meetingApplication의 approval을 true로 바꾸고 싶습니다.

        for(int i = 1; i <= classX.size(); i++) {
               if(classX.get(i).getApproval() == false && waitingQueue.getCapacity() >= i) {
                   classX.get(i).setApproval(true);
                     meetingApplicationRepository.save(classX.get(i));
                   break;
               }
        }

        classX.remove(meetingApplication);
        meetingApplicationRepository.delete(meetingApplication);

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

                switch (applicationClass) {
                    case HAIL:
                        classA.add(meetingApplication);
                        if (waitingQueue.getCapacity() >= classA.size()) {
                            meetingApplication.setApproval(true);
                        }
                        break;
                    case PADO:
                        classB.add(meetingApplication);
                        if (waitingQueue.getCapacity() >= classB.size()) {
                            meetingApplication.setApproval(true);
                        }
                        break;
                    case JANJAN:
                        classC.add(meetingApplication);
                        if (waitingQueue.getCapacity() >= classC.size()) {
                            meetingApplication.setApproval(true);
                        }
                        break;
                    case NULNUL:
                        classD.add(meetingApplication);
                        if (waitingQueue.getCapacity() >= classD.size()) {
                            meetingApplication.setApproval(true);
                        }
                        break;
                    default:
                        break;
                }

                meetingApplicationRepository.save(meetingApplication);
                // 해당 반의 신청인원 증가
                waitingQueue.setApplicationCount(waitingQueue.getApplicationCount() + 1);
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
