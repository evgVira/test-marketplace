package com.example.simple_marketplace.services;

import com.example.simple_marketplace.models.Images;
import com.example.simple_marketplace.models.Product;
import com.example.simple_marketplace.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Iterable<Product> allProducts(){
        return productRepository.findAll();
    }



    public void saveProduct(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException{
        Images image1, image2, image3;
        if(file1.getSize() != 0){
            image1 = toImageEntity(file1);
            image1.setPreview(true);
            product.addImageToProduct(image1);
        }
        if(file2.getSize() != 0){
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if(file3.getSize() != 0){
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        log.info("Saving new Product Title:{}; Author{}", product.getTitle(), product.getAuthor());
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImagesList().get(0).getId());
        productRepository.save(product);
    }
    private Images toImageEntity(MultipartFile file) throws IOException {
        Images image = new Images();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id){
        log.info("Delete product: {}", productRepository.findById(id));
        productRepository.deleteById(id);
    }
    public Product getProduct(Long id){
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }


}
