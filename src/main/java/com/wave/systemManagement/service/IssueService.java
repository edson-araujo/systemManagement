package com.wave.systemManagement.service;

import com.wave.systemManagement.model.Issue;
import com.wave.systemManagement.model.User;
import com.wave.systemManagement.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssuesById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issue, User user) throws Exception;

    void deleteIssue(Long issueId, long userId) throws Exception;

    Issue addUserToIssue(Long issueid, Long userId) throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;


}
