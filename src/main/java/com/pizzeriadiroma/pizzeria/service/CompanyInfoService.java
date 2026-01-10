package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.dto.CompanyInfoRequest;
import com.pizzeriadiroma.pizzeria.entity.CompanyInfo;
import com.pizzeriadiroma.pizzeria.exception.ResourceNotFoundException;
import com.pizzeriadiroma.pizzeria.exception.ValidationException;
import com.pizzeriadiroma.pizzeria.repository.CompanyInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyInfoService {

    private final CompanyInfoRepository companyInfoRepository;

    public CompanyInfoService(CompanyInfoRepository companyInfoRepository) {
        this.companyInfoRepository = companyInfoRepository;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfoRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("CompanyInfo not found (id=1)"));
    }

    public CompanyInfo saveCompanyInfo(CompanyInfo companyInfo) {
        if (companyInfo == null) throw new ValidationException("Company info cannot be null");
        companyInfo.setId(1);
        return companyInfoRepository.save(companyInfo);
    }

    public CompanyInfoRequest getForm() {
        return toForm(getCompanyInfo());
    }

    public void saveFromForm(CompanyInfoRequest form) {
        if (form == null) throw new ValidationException("Form cannot be null");

        CompanyInfo existing = getCompanyInfo();
        toEntity(form, existing);
        saveCompanyInfo(existing);
    }

    public CompanyInfoRequest toForm(CompanyInfo companyInfo) {
        if (companyInfo == null) throw new ValidationException("CompanyInfo cannot be null");

        CompanyInfoRequest request = new CompanyInfoRequest();
        request.setName(companyInfo.getName());
        request.setDescription(companyInfo.getDescription());
        request.setPhone(companyInfo.getPhone());
        request.setEmail(companyInfo.getEmail());
        request.setAddress(companyInfo.getAddress());
        request.setFacebookUrl(companyInfo.getFacebookUrl());
        request.setInstagramUrl(companyInfo.getInstagramUrl());
        request.setTwitterUrl(companyInfo.getTwitterUrl());
        request.setDphRate(companyInfo.getDphRate());
        request.setFreeDeliveryFrom(companyInfo.getFreeDeliveryFrom());
        request.setDeliveryPrice(companyInfo.getDeliveryPrice());
        request.setExtraIngredientPrice(companyInfo.getExtraIngredientPrice());
        return request;
    }

    private void toEntity(CompanyInfoRequest form, CompanyInfo target) {
        target.setName(form.getName());
        target.setDescription(form.getDescription());
        target.setPhone(form.getPhone());
        target.setEmail(form.getEmail());
        target.setAddress(form.getAddress());
        target.setFacebookUrl(form.getFacebookUrl());
        target.setInstagramUrl(form.getInstagramUrl());
        target.setTwitterUrl(form.getTwitterUrl());
        target.setDphRate(form.getDphRate());
        target.setFreeDeliveryFrom(form.getFreeDeliveryFrom());
        target.setDeliveryPrice(form.getDeliveryPrice());
        target.setExtraIngredientPrice(form.getExtraIngredientPrice());
    }
}
