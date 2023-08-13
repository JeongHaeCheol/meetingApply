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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.enum_model.SwimClass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    // 대기번호
    private Integer waitingNumber;

    private SwimClass swimClass;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant mainParticipant;

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
}
