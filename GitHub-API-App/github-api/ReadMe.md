# GitHub API Integration

## Description

Spring Boot application that retrieves all **non-forked** repositories of a GitHub user, including branch names and latest commit SHA.

### Endpoint

`GET /api/github/{username}/repos`

**Success:**  
Returns a list of repositories with branches and commit SHAs.

**Error (user not found):**
```json
{
  "status": 404,
  "message": "User not found"
}
