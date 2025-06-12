package org.koreait.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.global.libs.Utils;
import org.koreait.restaurant.entities.Restaurant;
import org.koreait.restaurant.repositories.RestaurantRepository;
import org.koreait.restaurant.services.RestaurantInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantRepository repository;
    private final RestaurantInfoService infoService;
    private final Utils utils;

    @GetMapping({"", "/list"})
    public String list(@ModelAttribute RestaurantSearch search) {

        return utils.tpl("restaurant/list");
    }

    @ResponseBody
    @GetMapping("/train")
    public List<Restaurant> train() {
        return repository.findAll();
    }
}
