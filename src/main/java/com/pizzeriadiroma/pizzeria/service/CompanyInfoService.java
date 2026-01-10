package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.CompanyInfo;
import com.pizzeriadiroma.pizzeria.exception.ResourceNotFoundException;
import com.pizzeriadiroma.pizzeria.repository.CompanyInfoRepository;
import com.pizzeriadiroma.pizzeria.exception.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class CompanyInfoService {

    private final CompanyInfoRepository companyInfoRepository;

    public CompanyInfoService(CompanyInfoRepository companyInfoRepository) {
        this.companyInfoRepository = companyInfoRepository;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfoRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("CompanyInfo not found"));
    }

    public CompanyInfo saveCompanyInfo(CompanyInfo companyInfo) {
        if (companyInfo == null) {
            throw new ValidationException("Company info cannot be null");
        }

        companyInfo.setId(1);
        return companyInfoRepository.save(companyInfo);
    }
}