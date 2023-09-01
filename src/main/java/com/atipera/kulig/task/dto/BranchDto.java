package com.atipera.kulig.task.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(using = BranchDtoDeserializer.class)
public class BranchDto {
    private String name;
    private String lastCommitSha;
}

