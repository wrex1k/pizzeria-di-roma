package com.pizzeriadiroma.pizzeria.config;

import com.pizzeriadiroma.pizzeria.entity.CompanyInfo;
import com.pizzeriadiroma.pizzeria.entity.User;
import com.pizzeriadiroma.pizzeria.service.CartService;
import com.pizzeriadiroma.pizzeria.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    private final CompanyInfoService companyInfoService;
    private final CartService cartService;

    @Autowired
    public GlobalModelAttributes(
            CompanyInfoService companyInfoService,
            CartService cartService
    ) {
        this.companyInfoService = companyInfoService;
        this.cartService = cartService;
    }

    @ModelAttribute("companyInfo")
    public CompanyInfo addCompanyInfoToModel() {
        return companyInfoService.getCompanyInfo();
    }

    @ModelAttribute("cartCount")
    public int addCartCountToModel(@AuthenticationPrincipal User user) {
        return cartService.getCartItemCount(user);
    }
}
