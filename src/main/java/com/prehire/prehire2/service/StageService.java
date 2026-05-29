package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.Stage;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.StageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StageService {

    private final StageRepository stageRepository;

    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    public List<Stage> getPipelineStages() {
        return stageRepository.findAllByOrderBySequenceOrderAsc();
    }

    public Stage getStageByName(String name) {
        return stageRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found: " + name));
    }
}

