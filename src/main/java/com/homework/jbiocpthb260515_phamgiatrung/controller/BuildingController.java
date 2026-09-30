package com.homework.jbiocpthb260515_phamgiatrung.controller;

import com.homework.jbiocpthb260515_phamgiatrung.dto.ApiResponse;
import com.homework.jbiocpthb260515_phamgiatrung.dto.BuildingRequest;
import com.homework.jbiocpthb260515_phamgiatrung.dto.PageResponse;
import com.homework.jbiocpthb260515_phamgiatrung.dto.StatusRequest;
import com.homework.jbiocpthb260515_phamgiatrung.entity.Building;
import com.homework.jbiocpthb260515_phamgiatrung.service.BuildingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<Building>>> getAll(@RequestParam(defaultValue = "0") int page) {
        return ApiResponse.success(HttpStatus.OK, "Lấy danh sách toà nhà thành công",
                buildingService.findAll(page));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Building>> create(@Valid @RequestBody BuildingRequest request) {
        return ApiResponse.success(HttpStatus.CREATED, "Thêm mới toà nhà thành công",
                buildingService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Building>> update(
            @PathVariable Long id,
            @Valid @ModelAttribute BuildingRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return ApiResponse.success(HttpStatus.OK, "Cập nhật toà nhà thành công",
                buildingService.update(id, request, file));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Building>> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusRequest request
    ) {
        return ApiResponse.success(HttpStatus.OK, "Thay đổi trạng thái thành công",
                buildingService.changeStatus(id, request.getStatus()));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<Building>>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Short status,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ApiResponse.success(HttpStatus.OK, "Tìm kiếm thành công",
                buildingService.search(name, status, page));
    }
}
