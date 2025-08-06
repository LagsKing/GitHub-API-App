package com.example.github_api.dto;

public class BranchInfo {
    private String name;
    private String lastCommitSha;

    public BranchInfo(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }

    public String getName() {
        return name;
    }

    public String getLastCommitSha() {
        return lastCommitSha;
    }
}
