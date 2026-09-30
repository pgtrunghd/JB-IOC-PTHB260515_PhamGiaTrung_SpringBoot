package com.homework.jbiocpthb260515_phamgiatrung.service.impl;

import com.homework.jbiocpthb260515_phamgiatrung.dto.BuildingRequest;
import com.homework.jbiocpthb260515_phamgiatrung.dto.PageResponse;
import com.homework.jbiocpthb260515_phamgiatrung.entity.Building;
import com.homework.jbiocpthb260515_phamgiatrung.exception.ApiException;
import com.homework.jbiocpthb260515_phamgiatrung.repository.BuildingRepository;
import com.homework.jbiocpthb260515_phamgiatrung.service.BuildingService;
import com.homework.jbiocpthb260515_phamgiatrung.service.FileStorageService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {
    private static final int PAGE_SIZE = 5;

    private final BuildingRepository buildingRepository;
    private final FileStorageService fileStorageService;

    private Pageable pageable(int page) {
        return PageRequest.of(Math.max(page, 0), PAGE_SIZE, Sort.by("id").ascending());
    }

    private Building findById(Long id) {
        return buildingRepository.findById(id).orElseThrow(() ->
                new ApiException(HttpStatus.NOT_FOUND, "Không tìm thấy toà nhà có mã " + id, null));
    }

    private ApiException duplicateName() {
        return new ApiException(HttpStatus.CONFLICT, "Tên toà nhà đã tồn tại", Map.of("buildingName", "Tên toà nhà không được trùng"));
    }

    private void mapToEntity(BuildingRequest request, Building building) {
        building.setBuildingName(request.getBuildingName().trim());
        building.setBuildingArea(request.getBuildingArea());
        building.setAreaUnit(request.getAreaUnit().trim());
        building.setStartDate(request.getStartDate());
        building.setTime(request.getTime());
        building.setTimeUnit(request.getTimeUnit().trim());
        building.setContent(request.getContent().trim());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<Building> findAll(int page) {
        return PageResponse.from(buildingRepository.findAll(pageable(page)));
    }

    @Override
    @Transactional
    public Building create(BuildingRequest request) {
        if (request.getDesign() == null || request.getDesign().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Dữ liệu không hợp lệ",
                    Map.of("design", "Đường dẫn ảnh thiết kế không được để trống"));
        }
        if (buildingRepository.existsByBuildingNameIgnoreCase(request.getBuildingName().trim())) {
            throw duplicateName();
        }
        Building building = new Building();
        mapToEntity(request, building);
        building.setDesign(request.getDesign().trim());
        building.setStatus((short) 1);
        return buildingRepository.save(building);
    }

    @Override
    @Transactional
    public Building update(Long id, BuildingRequest request, MultipartFile file) {
        Building building = findById(id);
        String buildingName = request.getBuildingName().trim();

        if (buildingRepository.existsByBuildingNameIgnoreCaseAndIdNot(buildingName, id)) {
            throw duplicateName();
        }
        mapToEntity(request, building);

        if (file != null && !file.isEmpty()) {
            building.setDesign(fileStorageService.saveImage(file));
        } else if (request.getDesign() != null && !request.getDesign().isBlank()) {
            building.setDesign(request.getDesign().trim());
        }
        return buildingRepository.save(building);
    }

    @Override
    public Building changeStatus(Long id, Short status) {
        Building building = findById(id);
        if (building.getStatus() == 2) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Toà nhà đã hoàn thành (status = 2), không thể thay đổi trạng thái",
                    Map.of("status", "Không được đổi trạng thái khi status = 2"));
        }
        building.setStatus(status);
        return buildingRepository.save(building);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<Building> search(String name, Short status, int page) {
        Specification<Building> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("buildingName")),
                        "%" + name.trim().toLowerCase() + "%"));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return PageResponse.from(buildingRepository.findAll(spec, pageable(page)));
    }
}
