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
@Table(name = "waiting_queue")
@EntityListeners(AuditingEntityListener.class)
public class WaitingQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    private SwimClass swimClass;

    private Integer capacity;

    private Integer extraCapacity;

    private Integer applicationCount;

    private Integer availableLastNumber;

    @PostConstruct
    public void init() {
        {
            applicationCount = 0;
            availableLastNumber = 1;
        }
    }

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
