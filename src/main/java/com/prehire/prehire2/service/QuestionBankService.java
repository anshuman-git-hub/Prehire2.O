package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.QuestionBank;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.QuestionBankRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionBankService {

    private final QuestionBankRepository questionBankRepository;

    public QuestionBank getQuestionById(Long id) {
        return questionBankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found: " + id));
    }

    public List<QuestionBank> getQuestionsByTestSkillMapping(Long testSkillMappingId) {
        return questionBankRepository.findByTestSkillMapping_Id(testSkillMappingId);
    }

    public List<QuestionBank> getQuestionsBySkillName(String skillName) {
        return questionBankRepository.findBySkillNameIgnoreCase(skillName);
    }

    public List<QuestionBank> getQuestionsByType(String questionType) {
        return questionBankRepository.findByQuestionType(questionType);
    }

    public List<QuestionBank> getQuestionsByDifficulty(String difficultyLevel) {
        return questionBankRepository.findByDifficultyLevel(difficultyLevel);
    }
}

