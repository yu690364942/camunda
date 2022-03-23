package com.example.camunda.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>TiTle: TestVo</p>
 * <p>Description: TestVo</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/2/23
 */
@Data
public class TestVo {
    @NotNull
    private String a;
}
