package com.example.camunda.controller;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceModificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>TiTle: RuntimeController</p>
 * <p>Description: RuntimeController</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/1/25
 */
@Slf4j
@RestController
@RequestMapping("/runtime")
public class RuntimeController {
    @Autowired
    private RuntimeService runtimeService;

    @RequestMapping("start")
    public String start(String processKey,String businessKey,@RequestBody Map<String,Object> map){
        return runtimeService.createProcessInstanceByKey(processKey).businessKey(businessKey).setVariables(map).execute().getId();
    }

    @RequestMapping("list")
    public void list(String processKey){
        final List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        log.info("{}",list);
    }

    @RequestMapping("list")
    public void revertTask(String processId){
        final ProcessInstanceModificationBuilder processInstanceModification = runtimeService.createProcessInstanceModification(processId);
    }

}
