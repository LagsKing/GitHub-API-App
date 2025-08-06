package com.example.github_api.controllers;

import com.example.github_api.dto.RepositoryInfo;
import com.example.github_api.dto.ErrorResponse;
import com.example.github_api.services.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/{username}/repos")
    public ResponseEntity<?> getNonForkedRepos(@PathVariable String username) {
        try {
            List<RepositoryInfo> repos = gitHubService.getNonForkedRepositories(username);
            return ResponseEntity.ok(repos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, e.getMessage()));
        }
    }
}
