package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.ImageRepository;
import com.example.springsecurityapplication.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }
    //данный метод позволяет вернуть всех пользователей
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    //возвращаем по id
    public Product getProductId(int id){
        Optional<Product> optionalProduct=productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public Image getImageId(int id){
        Optional<Image> optionalImage=imageRepository.findById(id);
        return optionalImage.orElse(null);
    }

    //сохраняем
    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    //обновляем
    @Transactional
    public void updateProduct(int id,Product product){
        product.setId(id);
        productRepository.save(product);
    }
    //удаляем
    @Transactional
    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }
}
