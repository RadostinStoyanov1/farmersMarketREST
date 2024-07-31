package bg.softuni.farmers_market.offers.service;

import bg.softuni.farmers_market.offers.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.offers.model.dto.OfferDTO;

import java.util.List;

public interface OfferService {
    public OfferDTO createOffer(AddOfferDTO addOfferDTO);
    public OfferDTO getOfferById(Long id);
    public void deleteOffer(Long offerId);
    public List<OfferDTO> getAllOffers();
}
