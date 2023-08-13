package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.MeetingApplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WaitingInfoDto {

    private Integer availableLastWaitingNumber;

    private Integer currentHeadCount;

    private List<MeetingApplication> meetingApplicationList;

}
