package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.PsyTestSubmission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsyTestSubmissionRepository extends JpaRepository<PsyTestSubmission, Long> {

    List<PsyTestSubmission> findByPsyTest_Id(Long psyTestId);

    List<PsyTestSubmission> findByCandidate_Id(Long candidateId);

    List<PsyTestSubmission> findByJob_Id(Long jobId);

    List<PsyTestSubmission> findByQuestionId(Long questionId);
}
