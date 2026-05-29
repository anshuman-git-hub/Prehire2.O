package com.prehire.prehire2.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class ScreeningSubmissionRequest {

    @NotNull(message = "tenantId is required")
    private Long tenantId;

    @NotNull(message = "jobId is required")
    private Long jobId;

    @NotNull(message = "candidateId is required")
    private Long candidateId;

    @NotEmpty(message = "answers cannot be empty")
    @Valid
    private List<AnswerDTO> answers;

    @Data
    public static class AnswerDTO {

        @NotNull(message = "questionId is required")
        private Long questionId;

        // For yes_no / short_text / numeric
        private String answerText;

        // For multiple_choice
        private List<String> selectedOptions;
    }
}

