package com.pizzeriadiroma.pizzeria.repository;

import com.pizzeriadiroma.pizzeria.entity.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Integer> {
}
