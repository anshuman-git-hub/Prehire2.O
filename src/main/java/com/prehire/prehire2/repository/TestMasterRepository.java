package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.TestMaster;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestMasterRepository extends JpaRepository<TestMaster, Long> {

    List<TestMaster> findByTenant_Id(Long tenantId);

    List<TestMaster> findByJob_Id(Long jobId);

    Optional<TestMaster> findByJob_IdAndTestName(Long jobId, String testName);

    List<TestMaster> findByCreatedBy_Id(Long createdById);
}

