package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.Stage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {

    Optional<Stage> findByName(String name);

    List<Stage> findAllByOrderBySequenceOrderAsc();
}
