package com.softdev.testtask.dto;

import com.softdev.testtask.entity.Actor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class UserActorsDto {

    @NotEmpty
    private String userEmail;

    @NotEmpty
    private List<Actor> actors;
}
