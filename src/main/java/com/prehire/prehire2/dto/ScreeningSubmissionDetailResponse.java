package com.prehire.prehire2.dto;

import com.prehire.prehire2.entity.JobCandidateScreeningSubmission;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public record ScreeningSubmissionDetailResponse(
        Long id,
        Long jobId,
        Long candidateId,
        Long questionId,
        String questionText,
        String answerText,
        List<String> selectedOptions,
        LocalDateTime submittedAt
) {
    /**
     * Factory method — entity se DTO banata hai.
     * Lazy fields directly access nahi hote, sirf IDs use karo
     * to avoid LazyInitializationException.
     */
    public static ScreeningSubmissionDetailResponse from(JobCandidateScreeningSubmission s) {
        return new ScreeningSubmissionDetailResponse(
                s.getId(),
                s.getJob().getId(),
                s.getCandidate().getId(),
                s.getQuestion().getId(),
                s.getQuestion().getQuestionText(),
                s.getAnswerText(),
                s.getSelectedOptions() != null ? Arrays.asList(s.getSelectedOptions()) : null,
                s.getSubmittedAt()
        );
    }
}
