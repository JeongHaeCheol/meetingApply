package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.enum_model.SwimClass;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "meetinng_application")
@EntityListeners(AuditingEntityListener.class)
public class MeetingApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;


    private SwimClass swimClass;

    private Boolean approval;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant mainParticipant;

    private String nickname;

    // @OneToOne
    // @JoinColumn(name = "extra_paid_lesson_id")
    // private ExtraPaidLesson extraPaidLesson;

    // private Integer accompanyingWaitingNumber;

    // private SwimClass accompanyingSwimClass;

    // private ExtraPaidLesson accompanyingExtraPaidLesson;

    private Boolean isAfterMeeting;

    // 톡방 입장여부
    private Boolean isAttendance;

    // 입금 여부
    private Boolean isPayment;

    // 문의사항
    private String inquiry;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public MeetingApplication(SwimClass swimClass, Boolean approval, Participant mainParticipant, String nickname, Boolean isAfterMeeting, Boolean isAttendance, Boolean isPayment, String inquiry) {
        this.swimClass = swimClass;
        this.approval = approval;
        this.mainParticipant = mainParticipant;
        this.nickname = nickname;
        this.isAfterMeeting = isAfterMeeting;
        this.isAttendance = isAttendance;
        this.isPayment = isPayment;
        this.inquiry = inquiry;
    }
}
