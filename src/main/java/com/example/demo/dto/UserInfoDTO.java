package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonFilter("outputFilter")
public class UserInfoDTO {

    private Long id;

    private String login;

    private String name;

    private String type;

    private String avatarUrl;

    private String createdAt;

    private Long followers;

    private Long publicRepos;

    private Double calculations;
}
