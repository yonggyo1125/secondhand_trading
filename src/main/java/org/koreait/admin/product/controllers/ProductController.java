package org.koreait.admin.product.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class ProductController {

    // 상품 목록
    @GetMapping({"", "/list"})
    public String list() {

        return "admin/product/list";
    }

    // 상품 등록
    @GetMapping("/register")
    public String register(@ModelAttribute RequestProduct form, Model model) {

        return "admin/product/register";
    }

    // 상품 정보 수정
    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) {

        return "admin/product/update";
    }

    /**
     * 상품 등록, 수정 처리
     *
     * @return
     */
    @PostMapping("/save")
    public String saveProduct(@Valid RequestProduct form, Errors errors) {
        String mode = Objects.requireNonNullElse(form.getMode(), "add");
        if (errors.hasErrors()) {
            return "admin/product/" + (mode.equals("edit") ? "update" : "register");
        }

        // 상품 등록 완료 후 상품 목록으로 이동
        return "redirect:/admin/product";
    }
}
