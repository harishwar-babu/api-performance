package com.performance.api_performance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestDto {
    private String userName;
    private String mobileNumber;
}
