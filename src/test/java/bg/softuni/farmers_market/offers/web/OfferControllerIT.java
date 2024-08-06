package bg.softuni.farmers_market.offers.web;

import bg.softuni.farmers_market.offers.model.entity.OfferEntity;
import bg.softuni.farmers_market.offers.model.entity.ProductTypeEnum;
import bg.softuni.farmers_market.offers.repository.OfferRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerIT {
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        offerRepository.deleteAll();
    }

    @Test
    public void testGetById() throws Exception {

        OfferEntity actualOffer = createTestOffer();

        ResultActions result = mockMvc.perform(get("/offers/{id}", actualOffer.getId())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actualOffer.getId().intValue())))
                .andExpect(jsonPath("$.description", is(actualOffer.getDescription())))
                .andExpect(jsonPath("$.productType", is(actualOffer.getProductType().toString())))
                .andExpect(jsonPath("$.name", is(actualOffer.getName())))
                .andExpect(jsonPath("$.author", is(actualOffer.getAuthor().intValue())));

    }

    @Test
    public void testGetById_NotFound() throws Exception {
        mockMvc
                .perform(get("/offers/{id}", 100000))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOffer() throws Exception {
        MvcResult result = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "description": "Test description",
                    "name": "Red Tomatoes",
                    "author": 1,
                    "productType": "TOMATO"
                 }
                """)
                ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        String body = result.getResponse().getContentAsString();

        int id = JsonPath.read(body, "$.id");

        Optional<OfferEntity> createdOfferOpt = offerRepository.findById((long) id);

        Assertions.assertTrue(createdOfferOpt.isPresent());

        OfferEntity createdOffer = createdOfferOpt.get();

        Assertions.assertEquals("Test description", createdOffer.getDescription());
        Assertions.assertEquals("Red Tomatoes", createdOffer.getName());
        Assertions.assertEquals(1, createdOffer.getAuthor().intValue());
        Assertions.assertEquals("TOMATO", createdOffer.getProductType().toString());
    }

    @Test
    void deleteOffer() throws Exception {
        OfferEntity actualEntity = createTestOffer();

        mockMvc.perform(delete("/offers/{id}", actualEntity.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Assertions.assertTrue(offerRepository.findById(actualEntity.getId()).isEmpty());
    }

    private OfferEntity createTestOffer() {
        return offerRepository.save(
                new OfferEntity()
                        .setProductType(ProductTypeEnum.TOMATO)
                        .setDescription("test offer")
                        .setName("Red Tomatoes")
                        .setAuthor(1l)
                        .setCreated(Instant.now())
        );
    }


}
