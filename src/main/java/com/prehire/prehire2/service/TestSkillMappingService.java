package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.TestSkillMapping;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.TestSkillMappingRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestSkillMappingService {

    private final TestSkillMappingRepository testSkillMappingRepository;

    public TestSkillMapping getMappingById(UUID id) {
        return testSkillMappingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test skill mapping not found: " + id));
    }

    public List<TestSkillMapping> getMappingsByTest(UUID testId) {
        return testSkillMappingRepository.findByTest_Id(testId);
    }

    public List<TestSkillMapping> getMappingsBySkillName(String skillName) {
        return testSkillMappingRepository.findBySkillNameIgnoreCase(skillName);
    }
}
