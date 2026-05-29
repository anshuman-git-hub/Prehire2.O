package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.PsyTestMaster;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.PsyTestMasterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PsyTestMasterService {

    private final PsyTestMasterRepository psyTestMasterRepository;

    public PsyTestMaster getPsyTestById(Long id) {
        return psyTestMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Psychometric test config not found: " + id));
    }

    public List<PsyTestMaster> getPsyTestsByTenant(Long tenantId) {
        return psyTestMasterRepository.findByTenant_Id(tenantId);
    }

    public PsyTestMaster getPsyTestByJob(Long jobId) {
        return psyTestMasterRepository.findByJob_Id(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Psychometric test config not found for job: " + jobId));
    }

    public List<PsyTestMaster> getPsyTestsCreatedBy(Long createdById) {
        return psyTestMasterRepository.findByCreatedBy_Id(createdById);
    }
}
