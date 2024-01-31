package com.example.simple_marketplace.controllers;


import com.example.simple_marketplace.models.Images;
import com.example.simple_marketplace.models.Product;
import com.example.simple_marketplace.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ProductService productService;
    @Autowired
    HomeController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public String home(Model model){
        model.addAttribute("products", productService.allProducts());
        for(Product product : productService.allProducts()){
            model.addAttribute("images", product.getImagesList());
        }
        return "home";
    }

    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable(value = "id")Long id, Model model){
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImagesList());
        return "details";
    }


    @PostMapping("/product/create")
    public String createProduct(@RequestParam String title,
                                @RequestParam String description,
                                @RequestParam double price,
                                @RequestParam String city,
                                @RequestParam String author,
                                @RequestParam MultipartFile file1,
                                @RequestParam MultipartFile file2,
                                @RequestParam MultipartFile file3)throws IOException {
        Product product = new Product(title, description, price, city, author);
        productService.saveProduct(product, file1, file2, file3);
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id")Long id){
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
