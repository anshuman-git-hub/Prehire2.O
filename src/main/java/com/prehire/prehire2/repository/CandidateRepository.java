package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.Candidate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    Optional<Candidate> findByUser_Id(UUID userId);

    List<Candidate> findByTenant_Id(UUID tenantId);
}
