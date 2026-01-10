package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.CompanyInfo;
import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.entity.PizzaSize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PriceCalculationService {

    private static final int PRICE_SCALE = 2;
    private static final RoundingMode PRICE_ROUNDING = RoundingMode.HALF_UP;

    private final CompanyInfoService companyInfoService;

    public PriceCalculationService(CompanyInfoService companyInfoService) {
        this.companyInfoService = companyInfoService;
    }

    public BigDecimal calculatePizzaTotal(Pizza pizza, PizzaSize size, int quantity, List<String> extraIngredientIds) {
        BigDecimal extraPrice = BigDecimal.valueOf(extraIngredientIds.size())
                .multiply(companyInfoService.getCompanyInfo().getExtraIngredientPrice());

        BigDecimal priceForSize = pizza.getBasePrice()
                .add(size.getPriceExtra());

        BigDecimal perPizza = priceForSize.add(extraPrice);

        return perPizza.multiply(BigDecimal.valueOf(quantity))
                .setScale(PRICE_SCALE, PRICE_ROUNDING);
    }

    public BigDecimal getExtraIngredientPrice() {
        return companyInfoService.getCompanyInfo().getExtraIngredientPrice()
                .setScale(PRICE_SCALE, PRICE_ROUNDING);
    }


    public BigDecimal calculateDeliveryFee(BigDecimal subtotal) {
        CompanyInfo companyInfo = companyInfoService.getCompanyInfo();
        
        if (subtotal.compareTo(companyInfo.getFreeDeliveryFrom()) >= 0) {
            return BigDecimal.ZERO.setScale(PRICE_SCALE, PRICE_ROUNDING);
        }
        
        return companyInfo.getDeliveryPrice().setScale(PRICE_SCALE, PRICE_ROUNDING);
    }

    public BigDecimal getVatRate() {
        return companyInfoService.getCompanyInfo().getDphRate()
                .setScale(PRICE_SCALE, PRICE_ROUNDING);
    }
}
