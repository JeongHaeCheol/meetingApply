package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.WaitingInfoDto;
import com.example.demo.enum_model.SwimClass;
import com.example.demo.model.MeetingApplication;
import com.example.demo.service.MeetingApplicationService;
import com.example.demo.service.WaitingQueueService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MeetingApplicationController {

    private final MeetingApplicationService meetingApplicationService;
    private final WaitingQueueService waitingQueueService;

    @GetMapping("/")
    public ResponseEntity home() {
        return ResponseEntity.ok("Home");
    }

    // 정모신청
    @PostMapping("/apply")
    public ResponseEntity apply(@ModelAttribute MeetingApplication payload) {

        List<MeetingApplication> result = meetingApplicationService.addWaiting(payload);

        return ResponseEntity.ok(result);
    }

    // 대기정보 확인
    @GetMapping("/info")
    public ResponseEntity info(@RequestParam SwimClass swimClass) {

        WaitingInfoDto result = waitingQueueService.getWaitingInfoDto(swimClass);

        return ResponseEntity.ok(result);

    }

}
