package com.example.camunda.controller;

import com.example.camunda.controller.request.TestVo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>TiTle: TestController</p>
 * <p>Description: TestController</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/2/23
 */
@Controller
@RequestMapping("test")
public class TestController {
    @RequestMapping("test")
    public void test(@RequestBody @Validated TestVo testVo){
        System.out.println(testVo);
    }

}
