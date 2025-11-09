package com.pizzeriadiroma.pizzeria.config;

import com.pizzeriadiroma.pizzeria.entity.CompanyInfo;
import com.pizzeriadiroma.pizzeria.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    private final CompanyInfoService companyInfoService;

    @Autowired
    public GlobalModelAttributes(CompanyInfoService companyInfoService) {
        this.companyInfoService = companyInfoService;
    }

    @ModelAttribute("companyInfo")
    public CompanyInfo addCompanyInfoToModel() {
        return companyInfoService.getCompanyInfo();
    }
}
