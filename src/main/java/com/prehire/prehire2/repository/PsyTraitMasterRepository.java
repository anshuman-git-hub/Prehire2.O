package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.PsyTraitMaster;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsyTraitMasterRepository extends JpaRepository<PsyTraitMaster, Long> {

    Optional<PsyTraitMaster> findByCode(String code);

    Optional<PsyTraitMaster> findByName(String name);
}
