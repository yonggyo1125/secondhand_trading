package org.koreait.admin.product.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String register() {

        return "admin/product/register";
    }

    // 상품 정보 수정
    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq) {

        return "admin/product/update";
    }

    /**
     * 상품 등록, 수정 처리
     *
     * @return
     */
    @PostMapping("/save")
    public String saveProduct() {

        // 상품 등록 완료 후 상품 목록으로 이동
        return "redirect:/admin/product";
    }
}
