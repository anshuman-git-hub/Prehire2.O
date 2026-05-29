package com.prehire.prehire2.controller;

import com.prehire.prehire2.dto.ScreeningSubmissionDetailResponse;
import com.prehire.prehire2.dto.ScreeningSubmissionRequest;
import com.prehire.prehire2.dto.ScreeningSubmissionResponse;
import com.prehire.prehire2.service.JobCandidateScreeningSubmissionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/screening")
@RequiredArgsConstructor
public class ScreeningController {

    private final JobCandidateScreeningSubmissionService submissionService;

    /**
     * POST /api/screening/submit
     * Submit screening answers for a candidate against a job.
     */
    @PostMapping("/submit")
    public ResponseEntity<ScreeningSubmissionResponse> submitScreening(
            @RequestBody @Valid ScreeningSubmissionRequest request) {

        ScreeningSubmissionResponse response = submissionService.submitScreening(request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/screening/job/{jobId}
     * Get all submissions for a job.
     */
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ScreeningSubmissionDetailResponse>> getByJob(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(submissionService.getSubmissionsByJob(jobId));
    }

    /**
     * GET /api/screening/candidate/{candidateId}
     * Get all submissions by a candidate.
     */
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<ScreeningSubmissionDetailResponse>> getByCandidate(
            @PathVariable Long candidateId) {

        return ResponseEntity.ok(submissionService.getSubmissionsByCandidate(candidateId));
    }

    /**
     * GET /api/screening/job/{jobId}/candidate/{candidateId}
     * Get all submissions for a specific candidate on a specific job.
     */
    @GetMapping("/job/{jobId}/candidate/{candidateId}")
    public ResponseEntity<List<ScreeningSubmissionDetailResponse>> getByJobAndCandidate(
            @PathVariable Long jobId,
            @PathVariable Long candidateId) {

        return ResponseEntity.ok(submissionService.getSubmissionsByJobAndCandidate(jobId, candidateId));
    }
}

