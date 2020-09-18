package org.softuni.productshop.integegration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.entities.Order;
import org.softuni.productshop.domain.entities.Product;
import org.softuni.productshop.domain.entities.User;
import org.softuni.productshop.repository.OrderRepository;
import org.softuni.productshop.repository.ProductRepository;
import org.softuni.productshop.service.OrderService;
import org.softuni.productshop.service.OrderServiceModel;
import org.softuni.productshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTests {
    @Autowired
    OrderService service;

    @MockBean
     OrderRepository orderRepository;
    @MockBean
     UserService userService;

    private List<Order>orders;

    @Before
    public void setupTest(){
        orders = new ArrayList<>();
        when(orderRepository.findAll())
                .thenReturn(orders);

    }


    @Test
    public void findAllOrders_whenOrders_retrunOrders(){
        String customer = "Test customer";
        String url = "http://image.url";
        String name = "product 1";
        BigDecimal price = BigDecimal.valueOf(1.34);
        Order order = new Order();
        order.setUser(new User(){{setUsername(customer);}});
        order.setProduct(new Product(){{setImageUrl(url);
        setName(name);
        setPrice(price);
        }});

        orders.add(order);

        var result = service.findAllProducts();
        //assertEquals(1, result.size());
        OrderServiceModel orderRes = result.get(0);

    //    assertEquals(1, result.size());
        assertEquals(customer, orderRes.getCustomer());



    }

}
