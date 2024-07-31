package bg.softuni.farmers_market.offers.repository;

import bg.softuni.farmers_market.offers.model.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {
}
