package bg.softuni.farmers_market.offers.web;

import bg.softuni.farmers_market.offers.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.offers.model.dto.OfferDTO;
import bg.softuni.farmers_market.offers.service.OfferService;
import bg.softuni.farmers_market.offers.service.exception.ApiOfferNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferController.class);
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(offerService.getOfferById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OfferDTO> deleteById(@PathVariable("id") Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping
    public ResponseEntity<List<OfferDTO>> getAllOffers() {
        return ResponseEntity.ok(
                offerService.getAllOffers()
        );
    }

    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(@RequestBody AddOfferDTO addOfferDTO) {
        LOGGER.info("Going to create an offer {}", addOfferDTO);

        OfferDTO offerDTO = offerService.createOffer(addOfferDTO);
        return ResponseEntity.
                created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(offerDTO.getId())
                                .toUri()
                ).body(offerDTO);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ApiOfferNotFoundException.class)
    @ResponseBody
    public NotFoundErrorInfo handleApiOfferNotFoundException(ApiOfferNotFoundException apiOfferNotFoundException) {
        return new NotFoundErrorInfo("NOT FOUND", apiOfferNotFoundException.getId());
    }


    public record NotFoundErrorInfo(String code, Object id) {

    }

}
