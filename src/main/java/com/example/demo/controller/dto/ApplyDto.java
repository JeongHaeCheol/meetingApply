package com.example.demo.controller.dto;

import com.example.demo.enum_model.SwimClass;
import com.example.demo.model.MeetingApplication;
import com.example.demo.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
public class ApplyDto {

    private SwimClass swimClass;

    private String participantNickName;

    private Boolean isAfterMeeting;

    // 톡방 입장여부
    private Boolean isAttendance;

    // 입금 여부
    private Boolean isPayment;

    // 문의사항
    private String inquiry;

}
