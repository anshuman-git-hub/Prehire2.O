package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.TestMaster;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.TestMasterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestMasterService {

    private final TestMasterRepository testMasterRepository;

    public TestMaster getTestById(Long id) {
        return testMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found: " + id));
    }

    public List<TestMaster> getTestsByTenant(Long tenantId) {
        return testMasterRepository.findByTenant_Id(tenantId);
    }

    public List<TestMaster> getTestsByJob(Long jobId) {
        return testMasterRepository.findByJob_Id(jobId);
    }

    public TestMaster getTestByJobAndName(Long jobId, String testName) {
        return testMasterRepository.findByJob_IdAndTestName(jobId, testName)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found: " + testName));
    }

    public List<TestMaster> getTestsCreatedBy(Long createdById) {
        return testMasterRepository.findByCreatedBy_Id(createdById);
    }
}

