package bg.softuni.farmers_market.offers.web;

import bg.softuni.farmers_market.offers.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.offers.model.dto.OfferDTO;
import bg.softuni.farmers_market.offers.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offers")
public class OfferController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferController.class);
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(
            @RequestBody AddOfferDTO addOfferDTO
    ) {
        LOGGER.info("Going to create an offer {}", addOfferDTO);

        offerService.createOffer(addOfferDTO);
        return ResponseEntity.ok().build();
    }

}
