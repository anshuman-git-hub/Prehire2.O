package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.PrehireRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrehireRoleRepository extends JpaRepository<PrehireRole, Long> {

    Optional<PrehireRole> findByName(String name);

    boolean existsByName(String name);
}

