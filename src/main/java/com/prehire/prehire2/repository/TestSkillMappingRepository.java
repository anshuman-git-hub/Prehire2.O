package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.TestSkillMapping;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSkillMappingRepository extends JpaRepository<TestSkillMapping, UUID> {

    List<TestSkillMapping> findByTest_Id(UUID testId);

    List<TestSkillMapping> findBySkillNameIgnoreCase(String skillName);
}
