package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.Job;
import com.prehire.prehire2.enums.JobStatus;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobService {

    private final JobRepository jobRepository;

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found: " + id));
    }

    public List<Job> getJobsByTenant(Long tenantId) {
        return jobRepository.findByTenant_Id(tenantId);
    }

    public List<Job> getJobsByTenantAndStatus(Long tenantId, JobStatus status) {
        return jobRepository.findByTenant_IdAndStatus(tenantId, status);
    }

    public List<Job> getJobsByCreator(Long createdById) {
        return jobRepository.findByCreatedBy_Id(createdById);
    }

    @Transactional
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    @Transactional
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
