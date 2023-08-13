package com.example.demo.enum_model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SwimClass {

    HAIL(0, "해일반"),
    PADO(1, "파도반"),
    JANJAN(2, "잔잔반"),
    NULNUL(3, "너울반");

    private Integer id;

    private String className;

}
