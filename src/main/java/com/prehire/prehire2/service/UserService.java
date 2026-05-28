package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.User;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public User getUserByEmailAndTenant(String email, UUID tenantId) {
        return userRepository.findByEmailAndTenant_Id(email, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
    }

    public List<User> getUsersByTenant(UUID tenantId) {
        return userRepository.findByTenant_Id(tenantId);
    }
}
