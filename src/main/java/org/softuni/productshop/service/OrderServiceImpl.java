package org.softuni.productshop.service;

import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.entities.Order;
import org.softuni.productshop.domain.entities.Product;
import org.softuni.productshop.domain.entities.User;
import org.softuni.productshop.repository.OrderRepository;
import org.softuni.productshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;


    public OrderServiceImpl(OrderRepository oderRepository
            , UserService userService,
                            ProductRepository productRepository, ModelMapper mapper){
        this.orderRepository = oderRepository;
        this.productRepository = productRepository;
        this.userService = userService;

        this.mapper = mapper;
    }
    @Override
    public void CreateOrder(String productId, String name) {
        UserServiceModel userModel = userService.findUserName(name);
        Product product = productRepository.findById(productId).orElseThrow(()
                -> new IllegalArgumentException());

        User user = new User();
        user.setId(userModel.getId());
        Order order = new Order();
        order.setProduct(product);
        order.setUser(user);

        orderRepository.save(order);

    }

    @Override
    public List<OrderServiceModel> findAllProducts() {
        var models = orderRepository.findAll()
                .stream()
                .map(o -> mapper.map(o.getProduct(), OrderServiceModel.class))
                .collect(Collectors.toList());
        return models;

    }
}
