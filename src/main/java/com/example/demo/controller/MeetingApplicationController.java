package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import com.example.demo.controller.dto.ApplyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam Integer id, @RequestParam Integer participantId) {

        List<MeetingApplication> result = meetingApplicationService.deleteWaiting(id, participantId);

        return ResponseEntity.ok(result);
    }

    // 정모신청
    @PostMapping("/apply")
    public ResponseEntity apply(@ModelAttribute ApplyDto payload) {

        List<MeetingApplication> result = meetingApplicationService.addWaiting(payload);

        return ResponseEntity.ok(result);
    }

    // 반별 신청 현황리스트
    @GetMapping("/list")
    public ResponseEntity list(@RequestParam SwimClass swimClass) {

        List<MeetingApplication> result = meetingApplicationService.getMeetingApplicationList(swimClass);

        return ResponseEntity.ok(result);

    }

    // 대기정보 확인
    @GetMapping("/info")
    public ResponseEntity info(@RequestParam SwimClass swimClass) {

        WaitingInfoDto result = waitingQueueService.getWaitingInfoDto(swimClass);

        return ResponseEntity.ok(result);

    }

}
