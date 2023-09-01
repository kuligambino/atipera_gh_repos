package com.atipera.kulig.task.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(using = RepositoryDtoDeserializer.class)
public class RepositoryDto {
    private String name;
    private String ownerLogin;
    private boolean isNotFork;
}
