package org.softuni.productshop.web.controllers;

import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.entities.Product;
import org.softuni.productshop.domain.models.binding.ProductAddBindingModel;
import org.softuni.productshop.domain.models.view.ProductDetailsViewModel;
import org.softuni.productshop.domain.models.view.ProductViewModel;
import org.softuni.productshop.service.CategoryService;
import org.softuni.productshop.service.CloudinaryService;
import org.softuni.productshop.service.ProductService;
import org.softuni.productshop.service.ProductServiceModel;
import org.softuni.productshop.web.exception.ProductNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.ldap.PagedResultsControl;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;


    public ProductController(ProductService productService, CloudinaryService cloudinaryService, ModelMapper modelMapper, CategoryService categoryService) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allProducts(Principal principal, ModelAndView modelAndView){

        modelAndView.addObject("products", this.productService.findAllProducts()
                .stream().map(c-> this.modelMapper.map(c, ProductViewModel.class))
                .collect(Collectors.toList()));
        return super.view("all-products", modelAndView);
    }
    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProducts(ModelAndView modelAndView){

        return super.view("add-products", modelAndView);
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProductsConfirm(@ModelAttribute ProductAddBindingModel model) throws IOException {

        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategories(
          this.categoryService.findAllCategories()
                .stream()
                .filter(c ->model.getCategories().contains(c.getId()))
                  .collect(Collectors.toList())
        );
        productServiceModel.setImageUrl(this.cloudinaryService.uploadImage(model.getImage()));
        this.productService.addProduct(productServiceModel);
        return super.redirect("/all-products");

    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsProducts(@PathVariable String id, ModelAndView modelAndView) throws IOException {

        modelAndView.addObject("product", this.modelMapper.map(this.productService.findProductById(id), ProductDetailsViewModel.class));


        return super.view("/details", modelAndView);



    }

   @GetMapping("/edit/{id}")
   @PreAuthorize("hasRole('ROLE_MODERATOR')")
   public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) {
       ProductServiceModel productServiceModel = this.productService.findProductById(id);
       ProductAddBindingModel model = this.modelMapper.map(productServiceModel,ProductAddBindingModel.class);
       model.setCategories(productServiceModel.getCategories().stream().map(c-> c.getName()).collect(Collectors.toList()));

       modelAndView.addObject("product", model);
       modelAndView.addObject("productId", id);
       return super.view("/edit-product", modelAndView);
   }

   @PostMapping("/edit/{id}")
   @PreAuthorize("hasRole('ROLE_MODERATOR')")
   public ModelAndView editProductConfirm(@PathVariable String id, @ModelAttribute ProductAddBindingModel model){
    this.productService.editProduct(id, this.modelMapper.map(model, ProductServiceModel.class));

       return super.redirect("/all-products");


   }

   @GetMapping("/delete/{id}")
   @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView){
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductAddBindingModel model = this.modelMapper.map(productServiceModel, ProductAddBindingModel.class);
        model.setCategories(productServiceModel.getCategories().stream().map(c-> c.getName()).collect(Collectors.toList()));

        modelAndView.addObject("product", model);
        modelAndView.addObject("productId", id);

        return super.view("/delete-product", modelAndView);


   }
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteConfirmProduct(@PathVariable String id){
        this.productService.deleteProduct(id);

        return super.redirect("/all-products");


    }

}
