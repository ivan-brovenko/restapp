package com.softdev.testtask.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
