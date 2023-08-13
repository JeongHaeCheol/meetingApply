package com.example.demo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH:mm:ss", timezone = "Asia/Seoul")
    private Date versionTime;

    public ResponseDto(Date versionTime) {
        this.versionTime = versionTime;
    }

}
