package org.softuni.productshop.config;

import org.apache.catalina.mapper.Mapper;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.entities.Order;
import org.softuni.productshop.mappings.MappingsInitializer;
import org.softuni.productshop.service.OrderServiceModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
public class ApplicationBeanConfiguration {

    static ModelMapper mapper;
    static {
        mapper = new ModelMapper();
        MappingsInitializer.initMappings(mapper);

    }


    @Bean
    public ModelMapper modelMapper(){
        return mapper;

    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();

    }

}
