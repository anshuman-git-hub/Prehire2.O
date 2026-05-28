package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.PrehireRole;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.PrehireRoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrehireRoleService {

    private final PrehireRoleRepository prehireRoleRepository;

    public List<PrehireRole> getAllRoles() {
        return prehireRoleRepository.findAll();
    }

    public PrehireRole getRoleByName(String name) {
        return prehireRoleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + name));
    }

    public boolean roleExists(String name) {
        return prehireRoleRepository.existsByName(name);
    }
}
