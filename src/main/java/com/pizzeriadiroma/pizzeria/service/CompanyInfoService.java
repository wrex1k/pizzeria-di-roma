package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.CompanyInfo;
import com.pizzeriadiroma.pizzeria.repository.CompanyInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompanyInfoService {

    private final CompanyInfoRepository companyInfoRepository;

    public CompanyInfoService(CompanyInfoRepository companyInfoRepository) {
        this.companyInfoRepository = companyInfoRepository;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfoRepository.findById(1)
                .orElseThrow(() -> new EntityNotFoundException("CompanyInfo not found"));
    }

    public CompanyInfo saveCompanyInfo(CompanyInfo companyInfo) {
        companyInfo.setId(1);
        return companyInfoRepository.save(companyInfo);
    }
}