package bg.softuni.farmers_market.offers.service.impl;

import bg.softuni.farmers_market.offers.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.offers.model.dto.OfferDTO;
import bg.softuni.farmers_market.offers.model.entity.OfferEntity;
import bg.softuni.farmers_market.offers.repository.OfferRepository;
import bg.softuni.farmers_market.offers.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
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
                .orElseThrow(() -> new IllegalArgumentException("Not found!"));
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
}
