package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.PsyTraitMaster;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.PsyTraitMasterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PsyTraitMasterService {

    private final PsyTraitMasterRepository psyTraitMasterRepository;

    public List<PsyTraitMaster> getAllTraits() {
        return psyTraitMasterRepository.findAll();
    }

    public PsyTraitMaster getTraitById(Long id) {
        return psyTraitMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Psychometric trait not found: " + id));
    }

    public PsyTraitMaster getTraitByCode(String code) {
        return psyTraitMasterRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Psychometric trait not found: " + code));
    }
}
