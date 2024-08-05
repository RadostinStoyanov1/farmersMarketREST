package bg.softuni.farmers_market.offers.repository;

import bg.softuni.farmers_market.offers.model.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM OfferEntity o WHERE o.created < :olderThan")
    void deleteOldOffers(Instant olderThan);

}
