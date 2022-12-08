package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("")
    public String getAllProduct(Model model){
        model.addAttribute("products",productService.getAllProduct());
        return "product/product";
    }

    @GetMapping("/info/{id}")
    public String infoUser(@PathVariable("id") int id, Model model){
        model.addAttribute("product",productService.getProductId(id));
        return "product/infoProduct";
    }

    //при помощи value указываем не обязательные параметры
    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search,@RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "price",required = false, defaultValue = "") String price, @RequestParam(value = "contact", required = false, defaultValue = "") String contact, Model model){
        //если обе границы не пустые то сортируем
        if (!ot.isEmpty() & !Do.isEmpty()){
            //является ли цена пустой
            if (!price.isEmpty()){
                //если сортируем по возрастанию
                if (price.equals("sorted_by_ascending_price")){
                    //если категория непустая
                    if (!contact.isEmpty()){
                        //если в ней лежит мебель
                        if (contact.equals("furniture")){
                            //выше мы записали параметр "ot" и "Do" как String но можем их перевести во float через команду Float.parseFloat(ot)
                            model.addAttribute("search_product",productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                            // 1 - выбрали мебель
                        } else if (contact.equals("appliances")){
                            model.addAttribute("search_product",productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        } else if (contact.equals("clothes")){
                            model.addAttribute("search_product",productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        }
                    }
                }
                //и если по убыванию
                else if (price.equals("sorted_by_descending_price")){
                    if (!contact.isEmpty()){
                        //если в ней лежит мебель
                        if (contact.equals("furniture")){
                            model.addAttribute("search_product",productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        } else if (contact.equals("appliances")){
                            model.addAttribute("search_product",productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        } else if (contact.equals("clothes")){
                            model.addAttribute("search_product",productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        }
                    }
                } else {
                    //если воспользовался только сортировкой по цене
                    model.addAttribute("search_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search,Float.parseFloat(ot), Float.parseFloat(Do)));
                }
            }
        } else{
        //если чел не воспользовался сортировкой по цене то просто ищем по кусочку тайтла
        model.addAttribute("search_product", productRepository.findByTitleContainingIgnoreCase(search));
    }
    //возвращаем введенные ранее значения при перезагрузке страницы
        model.addAttribute("value_search",search);
        model.addAttribute("value_price_ot",ot);
        model.addAttribute("value_price_do",Do);
        model.addAttribute("products",productService.getAllProduct());
        return "/product/product";
    }
}
