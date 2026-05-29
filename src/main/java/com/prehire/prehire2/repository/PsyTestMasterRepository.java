package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.PsyTestMaster;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsyTestMasterRepository extends JpaRepository<PsyTestMaster, Long> {

    List<PsyTestMaster> findByTenant_Id(Long tenantId);

    Optional<PsyTestMaster> findByJob_Id(Long jobId);

    List<PsyTestMaster> findByCreatedBy_Id(Long createdById);
}
