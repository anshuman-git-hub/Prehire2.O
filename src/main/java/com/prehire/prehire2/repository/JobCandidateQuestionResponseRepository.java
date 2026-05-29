package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateQuestionResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidateQuestionResponseRepository
        extends JpaRepository<JobCandidateQuestionResponse, Long> {

    List<JobCandidateQuestionResponse> findByCandidateTest_Id(Long candidateTestId);

    List<JobCandidateQuestionResponse> findByCandidate_Id(Long candidateId);

    List<JobCandidateQuestionResponse> findByJob_Id(Long jobId);

    List<JobCandidateQuestionResponse> findByQuestion_Id(Long questionId);
}

