package bg.softuni.farmers_market.offers.model.dto;

import bg.softuni.farmers_market.offers.model.entity.ProductTypeEnum;

public class AddOfferDTO {
    private ProductTypeEnum productType;
    private String description;
    private String name;
    private Long author;

    public ProductTypeEnum getProductType() {
        return productType;
    }

    public AddOfferDTO setProductType(ProductTypeEnum productType) {
        this.productType = productType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddOfferDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public AddOfferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Long getAuthor() {
        return author;
    }

    public AddOfferDTO setAuthor(Long author) {
        this.author = author;
        return this;
    }
}
