package com.pizzeriadiroma.pizzeria.controller;

import com.pizzeriadiroma.pizzeria.dto.CompanyInfoRequest;
import com.pizzeriadiroma.pizzeria.service.CompanyInfoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/company-info")
public class AdminCompanyInfoController {

    private final CompanyInfoService companyInfoService;

    public AdminCompanyInfoController(CompanyInfoService companyInfoService) {
        this.companyInfoService = companyInfoService;
    }

    @GetMapping
    public String page(Model model) {
        if (!model.containsAttribute("companyInfoRequest")) {
            model.addAttribute("companyInfoRequest", companyInfoService.getForm());
        }
        return "admin/company-info";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute("companyInfoRequest") CompanyInfoRequest companyInfoRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("companyInfoRequest", companyInfoRequest);
            return "admin/company-info";
        }

        companyInfoService.saveFromForm(companyInfoRequest);
        redirectAttributes.addFlashAttribute("success", "Company info saved.");
        return "redirect:/admin/company-info";
    }
}
