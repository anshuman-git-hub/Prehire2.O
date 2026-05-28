package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.Tenant;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.TenantRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TenantService {

    private final TenantRepository tenantRepository;

    public Tenant getTenantById(UUID id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + id));
    }

    public Tenant getTenantBySlug(String slug) {
        return tenantRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + slug));
    }

    public Tenant getTenantByAdminEmail(String adminEmail) {
        return tenantRepository.findByAdminEmail(adminEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for admin email: " + adminEmail));
    }

    public boolean slugExists(String slug) {
        return tenantRepository.existsBySlug(slug);
    }
}
