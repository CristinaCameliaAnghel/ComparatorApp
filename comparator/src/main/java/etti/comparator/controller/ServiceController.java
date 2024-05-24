package etti.comparator.controller;
import etti.comparator.model.Service;
import etti.comparator.repositories.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class ServiceController {


    @Autowired
    private ServicesRepository servicesRepository; // alows us to read the product from the DB



}
