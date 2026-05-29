package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndTenant_Id(String email, Long tenantId);

    List<User> findByTenant_Id(Long tenantId);
}

