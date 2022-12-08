package com.example.springsecurityapplication.controllers.admin;

import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.repositories.PersonRepository;
import com.example.springsecurityapplication.repositories.RoleRepository;
import com.example.springsecurityapplication.services.ImageService;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/seller")

public class SellerController {
    @Value("${upload.path}")
    private String uploadPath;

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private  final PersonRepository personRepository;
    private  final PersonService personService;
    private  final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private  final ImageService imageService;

    @Autowired
    public SellerController(ProductService productService, CategoryRepository categoryRepository, PersonRepository personRepository, PersonService personService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
        this.personService = personService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("")
    public String admin(Model model, Person person){
        model.addAttribute("products",productService.getAllProduct());
        return "admin/seller";
    }

    //http://localhost:8080/admin/product/add
    @GetMapping("/product/add")
    public String addProduct(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProductSeller";
    }
    //метод добавления в бд через сервис-->репозиторий
    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_fore") MultipartFile file_fore, @RequestParam("file_five") MultipartFile file_five) throws IOException {
        if (bindingResult.hasErrors()){
            return "product/addProductSeller";
        }
        if (file_one !=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." +file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" +resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if (file_two !=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." +file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" +resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if (file_three !=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." +file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" +resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if (file_fore !=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." +file_fore.getOriginalFilename();
            file_fore.transferTo(new File(uploadPath + "/" +resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if (file_five !=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." +file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" +resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        productService.saveProduct(product);
        return "redirect:/seller";
    }
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/seller";
    }

    @GetMapping("/product/delete/img/{id}")
    public String deleteImage(@PathVariable("id") int id){
        imageService.deleteImage(id);
        return "redirect:/seller";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(Model model,@PathVariable("id") int id){
        model.addAttribute("product",productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        model.addAttribute("image", imageService.getImageId(id));
        return "product/editProductSeller";
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product,BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors()){
            return "product/editProductSeller";
        }
        productService.updateProduct(id,product);
        return "redirect:/seller";
    }

    @PostMapping("/product/edit/img/{id}")
    public String editImage(@ModelAttribute("product") @Valid Product product, @ModelAttribute("image") Image image,@RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_fore") MultipartFile file_fore, @RequestParam("file_five") MultipartFile file_five, BindingResult bindingResult, @PathVariable("id") int id) throws IOException {
        if (bindingResult.hasErrors()) {
            return "product/editProduct";
        }
        if (file_one != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image new_image = new Image();
//            new_image.getProduct();
            new_image.setFileName(resultFileName);
            product.updateImageToProduct(new_image);
        }

        imageService.updateImage(id, image);
        return "redirect:/seller";
    }

}
