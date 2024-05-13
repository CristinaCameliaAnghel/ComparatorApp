package etti.comparator.controller;

import etti.comparator.dto.ProductDto;
import jakarta.validation.Valid;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import etti.comparator.model.Product;
import etti.comparator.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {


    @Autowired
    private ProductsRepository productsRepository; // alows us to read the product from the DB

    @GetMapping({"", "/"}) // o sa fie accesibila la /products sau /products/
    public String showProductList(Model model) {
        List<Product> products = productsRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        model.addAttribute("products", products);
        return "products/servicii";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/CreateProduct"; // returneaza un string care este numele HTML file
        //metoda aceata va fi accesibila la URL /product/create
        // va fi accesibila using HTTP getMethod
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result) {
        if (productDto.getImageFileName().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFileName", "Mandatory field"));
        }

        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
                //Această modificare va itera prin toate erorile existente
                // în BindingResult și le va afișa în consolă, folosind
                // getDefaultMessage() pentru a obține mesajul eroarei.
                // Apoi, întoarce pagina "products/CreateProduct" pentru
                // a afișa utilizatorului formularul de creare a produsului
                // cu mesajul de eroare corespunzător.
            }
            return "products/CreateProduct";
        }


// save image file
        MultipartFile image = productDto.getImageFileName();
        Date createdAt = new Date();
        String storageFileName = /* createdAt.getTime() + "_" + */image.getOriginalFilename();


        try {
            String uploadDir = "static/images/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Copiază fișierul din locația originală în folderul de imagini
            FileUtils.copyInputStreamToFile(image.getInputStream(), new File(uploadDir + storageFileName));
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
            // Tratează eroarea într-un mod adecvat
        }
/*
            try (InputStream inputStream = image.getInputStream()) {

                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {

            System.out.println("Exception: " + ex.getMessage());
        }*/

        //save the rest of param
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(createdAt);
        product.setImageFileName(storageFileName);

        productsRepository.save(product);


        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {

        try {
            Product product = productsRepository.findById(id).get();
            model.addAttribute("product", product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());

            model.addAttribute("productDto", productDto);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/products";
        }
        return "products/EditProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result) {

        try {
            Product product = productsRepository.findById(id).get();
            model.addAttribute("product", product);

            if (result.hasErrors()) {
                return "products/EditProduct";
            }
            if (result.hasErrors()) {
                for (ObjectError error : result.getAllErrors()) {
                    System.out.println(error.getDefaultMessage());
                }
            }

            if (!productDto.getImageFileName().isEmpty()) {
// delete old image
                String uploadDir = "static/images/";
                Path oldImagePath = Paths.get(uploadDir + product.getImageFileName());
                try {

                    Files.delete(oldImagePath);
                } catch (Exception ex) {

                    System.out.println("Exception:" + ex.getMessage());
                }
// save new image file

                MultipartFile image = productDto.getImageFileName();
                Date createdAt = new Date();
                String storageFileName = /*createdAt.getTime() + "_" + */image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {

                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImageFileName(storageFileName);

            }

            product.setName(productDto.getName());
            product.setBrand(productDto.getBrand());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());

            productsRepository.save(product);
        } catch (Exception ex) {
            System.out.println("Exception2: " + ex.getMessage());
        }
        return "redirect:/products";
    }


    @GetMapping("/delete")
    public String deleteProduct(
            @RequestParam int id
    ) {
        try {
            Product product = productsRepository.findById(id).get();


// delete product image
            Path imagePath = Paths.get("static/images/" + product.getImageFileName());
            try {
            Files.delete(imagePath);}
catch (Exception ex) {

            System.out.println("Exception: " +ex.getMessage());

              }

// delete the product
        productsRepository.delete (product);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/products";}


    }
