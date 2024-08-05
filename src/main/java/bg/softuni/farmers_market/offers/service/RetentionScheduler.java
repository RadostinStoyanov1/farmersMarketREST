package bg.softuni.farmers_market.offers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RetentionScheduler {
    private final Logger LOGGER = LoggerFactory.getLogger(RetentionScheduler.class);
    private final OfferService offerService;

    public RetentionScheduler(OfferService offerService) {
        this.offerService = offerService;
    }

    @Scheduled(cron = "0 0 3 * * SAT")
    public void deleteOldRecords() {
        LOGGER.info("Start deletion of old objects.");
        offerService.cleanupOldOffers();
        LOGGER.info("Start deletion finished.");
    }

}
