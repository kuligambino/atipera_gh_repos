package com.atipera.kulig.task.domain;

import com.atipera.kulig.task.dto.BranchDto;

import java.util.List;

public record Repository(String name, String ownerLogin, List<Branch> branches) {
}

record Branch(String name, String lastCommitSha) {

    static Branch of(BranchDto branch) {
        return new Branch(branch.getName(), branch.getLastCommitSha());
    }
}

