package com.homework.jbiocpthb260515_phamgiatrung.service;

import com.homework.jbiocpthb260515_phamgiatrung.dto.BuildingRequest;
import com.homework.jbiocpthb260515_phamgiatrung.dto.PageResponse;
import com.homework.jbiocpthb260515_phamgiatrung.entity.Building;
import org.springframework.web.multipart.MultipartFile;

public interface BuildingService {
    PageResponse<Building> findAll(int page);
    Building create(BuildingRequest request);
    Building update(Long id, BuildingRequest request, MultipartFile file);
    Building changeStatus(Long id, Short status);
    PageResponse<Building> search(String name, Short status, int page);
}
