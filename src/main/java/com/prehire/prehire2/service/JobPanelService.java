package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobPanel;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobPanelRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobPanelService {

    private final JobPanelRepository panelRepository;

    public JobPanel getPanelById(Long id) {
        return panelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panel entry not found: " + id));
    }

    public List<JobPanel> getPanelByJob(Long jobId) {
        return panelRepository.findByJob_Id(jobId);
    }

    public JobPanel getPanelByJobAndUser(Long jobId, Long userId) {
        return panelRepository.findByJob_IdAndPanelUser_Id(jobId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Panel assignment not found for Job " + jobId + " and User " + userId));
    }

    public boolean isUserAssignedToJob(Long jobId, Long userId) {
        return panelRepository.existsByJob_IdAndPanelUser_Id(jobId, userId);
    }

    @Transactional
    public JobPanel savePanel(JobPanel panel) {
        return panelRepository.save(panel);
    }

    @Transactional
    public void deletePanel(Long id) {
        panelRepository.deleteById(id);
    }
}
