package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.TestSkillMapping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSkillMappingRepository extends JpaRepository<TestSkillMapping, Long> {

    List<TestSkillMapping> findByTest_Id(Long testId);

    List<TestSkillMapping> findBySkillNameIgnoreCase(String skillName);
}

