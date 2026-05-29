package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.Candidate;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.CandidateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found: " + id));
    }

    public Candidate getCandidateByUserId(Long userId) {
        return candidateRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found for User ID: " + userId));
    }

    public List<Candidate> getCandidatesByTenant(Long tenantId) {
        return candidateRepository.findByTenant_Id(tenantId);
    }

    @Transactional
    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Transactional
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
}
