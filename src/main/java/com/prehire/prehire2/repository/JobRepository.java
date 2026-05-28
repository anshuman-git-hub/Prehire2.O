package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.Job;
import com.prehire.prehire2.enums.JobStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, UUID> {

    List<Job> findByTenant_Id(UUID tenantId);

    List<Job> findByTenant_IdAndStatus(UUID tenantId, JobStatus status);

    List<Job> findByCreatedBy_Id(UUID createdById);
}
