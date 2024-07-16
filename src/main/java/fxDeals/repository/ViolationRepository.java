package fxDeals.repository;


import fxDeals.entity.Violation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViolationRepository extends JpaRepository<Violation, Long> {
    Optional<Violation> findByFileName(String fileName);



}
