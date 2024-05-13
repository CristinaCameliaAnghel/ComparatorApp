package etti.comparator.controller;

import etti.comparator.dto.ProductDto;
import etti.comparator.model.Product;
import etti.comparator.repositories.ProductsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.*;


@Controller
public class MainController {

    @Autowired
    private ProductsRepository productsRepository;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/servicii")
    public String loadServiciiPage() {
        return "/products/servicii";
    }

    @RequestMapping("/utilitati")
    public String loadUtilitatiPage() {
        return "utilitati";
    }





}
