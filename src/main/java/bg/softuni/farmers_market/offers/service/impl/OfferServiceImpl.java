package bg.softuni.farmers_market.offers.service.impl;

import bg.softuni.farmers_market.offers.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.offers.model.dto.OfferDTO;
import bg.softuni.farmers_market.offers.model.entity.OfferEntity;
import bg.softuni.farmers_market.offers.repository.OfferRepository;
import bg.softuni.farmers_market.offers.service.OfferService;
import bg.softuni.farmers_market.offers.service.exception.ApiOfferNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Period;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final Period retentionPeriod;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, @Value("${offers.retention.period}") Period retentionPeriod) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.retentionPeriod = retentionPeriod;
    }

    @Override
    public OfferDTO createOffer(AddOfferDTO addOfferDTO) {
        OfferEntity offerEntity = offerRepository.save(modelMapper.map(addOfferDTO, OfferEntity.class));
        return modelMapper.map(offerEntity, OfferDTO.class);
    }

    @Override
    public OfferDTO getOfferById(Long id) {
        return offerRepository
                .findById(id)
                .map(o -> modelMapper.map(o, OfferDTO.class))
                .orElseThrow(() -> new ApiOfferNotFoundException("Offer with id: " + id + "was not found", id));
    }

    @Override
    public void deleteOffer(Long offerId) {
        offerRepository.deleteById(offerId);
    }

    @Override
    public List<OfferDTO> getAllOffers() {
        return offerRepository
                .findAll()
                .stream()
                .map(o -> modelMapper.map(o, OfferDTO.class))
                .toList();
    }

    @Override
    public void cleanupOldOffers() {
        Instant now = Instant.now();
        Instant deleteBefore = now.minus(retentionPeriod);
        LOGGER.info("Removing all offers older than " + deleteBefore);
        offerRepository.deleteOldOffers(deleteBefore);
        LOGGER.info("Old orders were removed");
    }


}
