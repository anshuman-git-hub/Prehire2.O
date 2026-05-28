package com.prehire.prehire2.service;

import com.prehire.prehire2.dto.ScreeningSubmissionRequest;
import com.prehire.prehire2.dto.ScreeningSubmissionResponse;
import com.prehire.prehire2.entity.Candidate;
import com.prehire.prehire2.entity.Job;
import com.prehire.prehire2.entity.JobCandidateScreeningSubmission;
import com.prehire.prehire2.entity.JobScreeningQuestion;
import com.prehire.prehire2.entity.Tenant;
import com.prehire.prehire2.enums.QuestionType;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.CandidateRepository;
import com.prehire.prehire2.repository.JobCandidateScreeningSubmissionRepository;
import com.prehire.prehire2.repository.JobRepository;
import com.prehire.prehire2.repository.JobScreeningQuestionRepository;
import com.prehire.prehire2.repository.TenantRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCandidateScreeningSubmissionService {

    private final JobCandidateScreeningSubmissionRepository jobCandidateScreeningSubmissionRepository;
    private final JobScreeningQuestionRepository jobScreeningQuestionRepository;
    private final TenantRepository tenantRepository;
    private final JobRepository jobRepository;
    private final CandidateRepository candidateRepository;

    // ── existing read methods ──────────────────────────────────────────────

    public JobCandidateScreeningSubmission getSubmissionById(UUID id) {
        return jobCandidateScreeningSubmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screening submission not found: " + id));
    }

    public List<JobCandidateScreeningSubmission> getSubmissionsByJob(UUID jobId) {
        return jobCandidateScreeningSubmissionRepository.findByJob_Id(jobId);
    }

    public List<JobCandidateScreeningSubmission> getSubmissionsByCandidate(UUID candidateId) {
        return jobCandidateScreeningSubmissionRepository.findByCandidate_Id(candidateId);
    }

    public List<JobCandidateScreeningSubmission> getSubmissionsByJobAndCandidate(UUID jobId, UUID candidateId) {
        return jobCandidateScreeningSubmissionRepository.findByJob_IdAndCandidate_Id(jobId, candidateId);
    }

    public List<JobCandidateScreeningSubmission> getSubmissionsByQuestion(UUID questionId) {
        return jobCandidateScreeningSubmissionRepository.findByQuestion_Id(questionId);
    }

    // ── submit ────────────────────────────────────────────────────────────

    @Transactional
    public ScreeningSubmissionResponse submitScreening(ScreeningSubmissionRequest request) {

        Tenant tenant = tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + request.getTenantId()));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found: " + request.getJobId()));

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found: " + request.getCandidateId()));

        // Check duplicate submission
        List<JobCandidateScreeningSubmission> existing =
                jobCandidateScreeningSubmissionRepository.findByJob_IdAndCandidate_Id(
                        job.getId(), candidate.getId());
        if (!existing.isEmpty()) {
            throw new IllegalStateException("Screening already submitted for this job and candidate");
        }

        List<JobCandidateScreeningSubmission> submissions = request.getAnswers().stream()
                .map(answer -> buildSubmission(tenant, job, candidate, answer))
                .toList();

        jobCandidateScreeningSubmissionRepository.saveAll(submissions);

        return new ScreeningSubmissionResponse("Screening submitted successfully", submissions.size());
    }

    private JobCandidateScreeningSubmission buildSubmission(
            Tenant tenant,
            Job job,
            Candidate candidate,
            ScreeningSubmissionRequest.AnswerDTO answer) {

        JobScreeningQuestion question = jobScreeningQuestionRepository.findById(answer.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found: " + answer.getQuestionId()));

        validateAnswer(question, answer);

        JobCandidateScreeningSubmission submission = new JobCandidateScreeningSubmission();
        submission.setTenant(tenant);
        submission.setJob(job);
        submission.setCandidate(candidate);
        submission.setQuestion(question);
        submission.setAnswerText(answer.getAnswerText());
        submission.setSelectedOptions(
                answer.getSelectedOptions() != null
                        ? answer.getSelectedOptions().toArray(new String[0])
                        : null
        );

        return submission;
    }

    private void validateAnswer(JobScreeningQuestion question,
                                ScreeningSubmissionRequest.AnswerDTO answer) {
        if (question.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
            if (answer.getSelectedOptions() == null || answer.getSelectedOptions().isEmpty()) {
                throw new IllegalArgumentException(
                        "selected_options required for multiple_choice question: " + question.getId());
            }
        } else {
            if (answer.getAnswerText() == null || answer.getAnswerText().isBlank()) {
                throw new IllegalArgumentException(
                        "answer_text required for question type " + question.getQuestionType()
                                + " — question: " + question.getId());
            }
        }
    }
}
