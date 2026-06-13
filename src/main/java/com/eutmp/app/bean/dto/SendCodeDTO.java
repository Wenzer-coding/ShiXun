package com.eutmp.app.bean.dto;

import lombok.Data;

/**
 * 发送验证码DTO
 */
@Data
public class SendCodeDTO {
    /**
     * 邮箱
     */
    private String email;
}
