package org.softuni.productshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.entities.Order;
import org.softuni.productshop.domain.models.rest.ProductOrderRequestModel;
import org.softuni.productshop.domain.models.view.ProductDetailsViewModel;
import org.softuni.productshop.service.OrderService;
import org.softuni.productshop.service.OrderServiceModel;
import org.softuni.productshop.service.ProductService;
import org.softuni.productshop.service.ProductServiceModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrdersController  extends BaseController{
    private final ModelMapper modelMapper;
    private final OrderService orderService;
    private final ProductService productService;
    public OrdersController(ModelMapper modelMapper1, OrderService orderService, ProductService productService1){
        this.modelMapper = modelMapper1;
        this.orderService = orderService;
        this.productService = productService1;


    }

    @GetMapping("/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderProduct(@PathVariable String id, ModelAndView modelAndView){
        ProductServiceModel productServiceModel = productService.findProductById(id);
        ProductDetailsViewModel viewModel = modelMapper.map(productServiceModel, ProductDetailsViewModel.class);
        modelAndView.addObject("product", viewModel);
        return super.view("order/product", modelAndView);

    }
    @PostMapping("/submit")
    public void submitOrder(@ModelAttribute ProductOrderRequestModel model, Principal principal){
        String name = principal.getName();
        orderService.CreateOrder(model.getId(), name);



    }
    @GetMapping("/all")
    public ModelAndView getAllOrders(ModelAndView modelAndView){
        List<OrderServiceModel> viewModels = orderService.findAllProducts()
                .stream()
                .map(o -> modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("orders" , viewModels);



        return view("order/all-orders", modelAndView);

    }

}
