package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobPanel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPanelRepository extends JpaRepository<JobPanel, Long> {
    List<JobPanel> findByJob_Id(Long jobId);
    Optional<JobPanel> findByJob_IdAndPanelUser_Id(Long jobId, Long panelUserId);
    boolean existsByJob_IdAndPanelUser_Id(Long jobId, Long panelUserId);
}
