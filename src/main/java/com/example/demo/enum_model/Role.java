package com.example.demo.enum_model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN(0, "ADMIN"),
    USER(1, "USER");

    private Integer id;

    private String roleName;

}
