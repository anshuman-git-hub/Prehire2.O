package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.TestMaster;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestMasterRepository extends JpaRepository<TestMaster, UUID> {

    List<TestMaster> findByTenant_Id(UUID tenantId);

    List<TestMaster> findByJob_Id(UUID jobId);

    Optional<TestMaster> findByJob_IdAndTestName(UUID jobId, String testName);

    List<TestMaster> findByCreatedBy_Id(UUID createdById);
}
