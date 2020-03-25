package org.softuni.productshop.service;

import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.entities.Order;
import org.softuni.productshop.mappings.IHaveCustomMappings;

import java.math.BigDecimal;

public class OrderServiceModel implements IHaveCustomMappings {
    private String url;
    private String name;
    private BigDecimal price;
    private String customer;

    public OrderServiceModel() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public void configureMappings(ModelMapper mapper) {
        mapper.createTypeMap(Order.class, OrderServiceModel.class)
                .addMapping(
                        entity -> entity.getProduct().getName(),
                        (dto, value) -> dto.setName((String)value)
                )
                .addMapping(
                        entity -> entity.getProduct().getPrice(),
                        (dto, value) -> dto.setPrice((BigDecimal)value)
                ).addMapping(
                entity -> entity.getProduct().getImageUrl(),
                (dto, value) -> dto.setUrl((String)value)
        ).addMapping(
                entity -> entity.getUser().getUsername(),
                (dto, value) -> dto.setCustomer((String)value)
        );
    }
}
