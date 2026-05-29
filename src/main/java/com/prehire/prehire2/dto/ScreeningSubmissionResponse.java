package com.prehire.prehire2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScreeningSubmissionResponse {
    private String message;
    private int totalAnswersSubmitted;
}

