package fxDeals.repository;

import fxDeals.entity.Deals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DealRepository extends JpaRepository<Deals, Long> {
    Optional<Deals> findByDealUniqueId(String dealUniqueId);


}