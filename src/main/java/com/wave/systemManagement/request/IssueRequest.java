package com.wave.systemManagement.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IssueRequest {
    private String title;
    private String description;
    private String Status;
    private Long projectId;
    private String priority;
    private LocalDate dueDate;
}
