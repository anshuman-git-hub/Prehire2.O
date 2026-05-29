package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.PsyTestTraitConfig;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsyTestTraitConfigRepository extends JpaRepository<PsyTestTraitConfig, Long> {

    List<PsyTestTraitConfig> findByPsyTest_Id(Long psyTestId);

    Optional<PsyTestTraitConfig> findByPsyTest_IdAndTrait_Id(Long psyTestId, Long traitId);

    List<PsyTestTraitConfig> findByTrait_Id(Long traitId);
}
