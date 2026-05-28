package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailAndTenant_Id(String email, UUID tenantId);

    List<User> findByTenant_Id(UUID tenantId);
}
