package com.example.github_api.services;

import com.example.github_api.dto.BranchInfo;
import com.example.github_api.dto.RepositoryInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RepositoryInfo> getNonForkedRepositories(String username) {
        String url = "https://api.github.com/users/" + username + "/repos?per_page=100";
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            List<Map<String, Object>> repos = response.getBody();
            if (repos == null) return Collections.emptyList();

            List<RepositoryInfo> result = new ArrayList<>();

            for (Map<String, Object> repo : repos) {
                Boolean fork = (Boolean) repo.get("fork");
                if (fork != null && !fork) {
                    String repoName = (String) repo.get("name");
                    Map<String, Object> owner = (Map<String, Object>) repo.get("owner");
                    String ownerLogin = (String) owner.get("login");

                    List<BranchInfo> branches = fetchBranches(ownerLogin, repoName);

                    result.add(new RepositoryInfo(repoName, ownerLogin, branches));
                }
            }
            return result;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("User not found");
        }
    }

    private List<BranchInfo> fetchBranches(String owner, String repo) {
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/branches";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        List<Map<String, Object>> branches = response.getBody();
        if (branches == null) return Collections.emptyList();

        List<BranchInfo> result = new ArrayList<>();
        for (Map<String, Object> branch : branches) {
            String name = (String) branch.get("name");
            Map<String, Object> commit = (Map<String, Object>) branch.get("commit");
            String sha = (String) commit.get("sha");
            result.add(new BranchInfo(name, sha));
        }
        return result;
    }
}

