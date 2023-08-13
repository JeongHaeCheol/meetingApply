package com.example.demo.model;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "waiting_queue")
@EntityListeners(AuditingEntityListener.class)
public class WaitingQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    private SwimClass swimClass;

    // 허용 가능 인원
    private Integer capacity;

    // 추가 지원자 수
    private Integer extraCapacity;

    //지원자 수
    private Integer applicationCount;


    @PostConstruct
    public void init() {
        {
            applicationCount = 0;
        }
    }

    @CreatedDate
    @Column(updatable = false, nullable = true)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public WaitingQueue(SwimClass swimClass, Integer capacity, Integer extraCapacity, Integer applicationCount) {
        this.swimClass = swimClass;
        this.capacity = capacity;
        this.extraCapacity = extraCapacity;
        this.applicationCount = applicationCount;
    }
}
