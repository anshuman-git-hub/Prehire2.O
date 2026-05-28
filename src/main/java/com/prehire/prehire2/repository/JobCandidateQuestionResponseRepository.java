package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateQuestionResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidateQuestionResponseRepository
        extends JpaRepository<JobCandidateQuestionResponse, UUID> {

    List<JobCandidateQuestionResponse> findByCandidateTest_Id(UUID candidateTestId);

    List<JobCandidateQuestionResponse> findByCandidate_Id(UUID candidateId);

    List<JobCandidateQuestionResponse> findByJob_Id(UUID jobId);

    List<JobCandidateQuestionResponse> findByQuestion_Id(UUID questionId);
}
