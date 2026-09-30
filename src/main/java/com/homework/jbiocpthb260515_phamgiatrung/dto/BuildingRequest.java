package com.homework.jbiocpthb260515_phamgiatrung.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class BuildingRequest {
    @NotBlank(message = "Tên toà nhà không được để trống")
    @Size(max = 100, message = "Tên toà nhà tối đa 100 ký tự")
    private String buildingName;

    @NotNull(message = "Diện tích xây dựng không được để trống")
    @Positive(message = "Diện tích xây dựng phải lớn hơn 0")
    private Double buildingArea;

    @NotBlank(message = "Đơn vị diện tích không được để trống")
    @Size(max = 100, message = "Đơn vị diện tích tối đa 100 ký tự")
    private String areaUnit;

    @NotNull(message = "Ngày khởi công không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Thời gian xây dựng không được để trống")
    @Positive(message = "Thời gian xây dựng phải lớn hơn 0")
    private Integer time;

    @NotBlank(message = "Đơn vị thời gian không được để trống")
    @Size(max = 10, message = "Đơn vị thời gian tối đa 10 ký tự")
    private String timeUnit;

    @Size(max = 255, message = "Đường dẫn ảnh tối đa 255 ký tự")
    private String design;

    @NotBlank(message = "Nội dung xây dựng không được để trống")
    @Size(max = 255, message = "Nội dung xây dựng tối đa 100 ký tự")
    private String content;
}
