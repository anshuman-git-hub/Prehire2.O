package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.Tenant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {

    Optional<Tenant> findBySlug(String slug);

    Optional<Tenant> findByAdminEmail(String adminEmail);

    boolean existsBySlug(String slug);
}
