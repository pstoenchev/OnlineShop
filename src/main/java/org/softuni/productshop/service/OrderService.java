package org.softuni.productshop.service;

import java.util.List;

public interface OrderService {

    void CreateOrder(String productId, String name);

    List<OrderServiceModel> findAllProducts();




}