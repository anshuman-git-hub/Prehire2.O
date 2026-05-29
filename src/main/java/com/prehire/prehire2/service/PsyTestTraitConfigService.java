package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.PsyTestTraitConfig;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.PsyTestTraitConfigRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PsyTestTraitConfigService {

    private final PsyTestTraitConfigRepository psyTestTraitConfigRepository;

    public PsyTestTraitConfig getTraitConfigById(Long id) {
        return psyTestTraitConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Psychometric trait config not found: " + id));
    }

    public List<PsyTestTraitConfig> getTraitConfigsByPsyTest(Long psyTestId) {
        return psyTestTraitConfigRepository.findByPsyTest_Id(psyTestId);
    }

    public PsyTestTraitConfig getTraitConfigByPsyTestAndTrait(Long psyTestId, Long traitId) {
        return psyTestTraitConfigRepository.findByPsyTest_IdAndTrait_Id(psyTestId, traitId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Psychometric trait config not found for test " + psyTestId + " and trait " + traitId
                ));
    }

    public List<PsyTestTraitConfig> getTraitConfigsByTrait(Long traitId) {
        return psyTestTraitConfigRepository.findByTrait_Id(traitId);
    }
}
