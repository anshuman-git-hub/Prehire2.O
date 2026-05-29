package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.Job;
import com.prehire.prehire2.enums.JobStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByTenant_Id(Long tenantId);

    List<Job> findByTenant_IdAndStatus(Long tenantId, JobStatus status);

    List<Job> findByCreatedBy_Id(Long createdById);
}

