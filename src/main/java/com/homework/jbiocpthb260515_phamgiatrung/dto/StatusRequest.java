package com.homework.jbiocpthb260515_phamgiatrung.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusRequest {
    @NotNull(message = "status không được để trống")
    @Min(value = 0, message = "status chỉ nhận giá trị 0, 1 hoặc 2")
    @Max(value = 2, message = "status chỉ nhận giá trị 0, 1 hoặc 2")
    private Short status;
}
