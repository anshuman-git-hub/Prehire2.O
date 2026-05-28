package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.QuestionBank;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBankRepository extends JpaRepository<QuestionBank, UUID> {

    List<QuestionBank> findByTestSkillMapping_Id(UUID testSkillMappingId);

    List<QuestionBank> findBySkillNameIgnoreCase(String skillName);

    List<QuestionBank> findByQuestionType(String questionType);

    List<QuestionBank> findByDifficultyLevel(String difficultyLevel);
}
