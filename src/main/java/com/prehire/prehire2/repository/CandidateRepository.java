package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.Candidate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByUser_Id(Long userId);

    List<Candidate> findByTenant_Id(Long tenantId);
}

