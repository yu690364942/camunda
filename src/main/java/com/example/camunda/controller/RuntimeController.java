package com.example.camunda.controller;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.ProcessInstanceModificationBuilderImpl;
import org.camunda.bpm.engine.runtime.ModificationBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceModificationBuilder;
import org.camunda.bpm.engine.task.Task;
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

    @Autowired
    private TaskService taskService;
    @RequestMapping("start")
    public String start(String processKey,String businessKey,@RequestBody Map<String,Object> map){
        return runtimeService.createProcessInstanceByKey(processKey).businessKey(businessKey).setVariables(map).execute().getId();
    }

    @RequestMapping("restart")
    public String start(String processId){
        final ProcessInstanceModificationBuilderImpl processInstanceModification =
                (ProcessInstanceModificationBuilderImpl) runtimeService.createProcessInstanceModification(processId);
        final List<Task> list = taskService.createTaskQuery().processInstanceId(processId).list();
        final Task task = list.get(0);
        taskService.complete(task.getId());
        log.info(task.getId());
        taskService.deleteTask(task.getId(),"refuse");
        //
        // //
        processInstanceModification.cancelActivityInstance(processId)
                .startBeforeActivity("process_start").execute();
        return "success";
    }

    @RequestMapping("list")
    public void list(String processKey){
        final Map<String, Object> variables = runtimeService.getVariables("404ced9f-92b2-11ec-a73c-9405bb139dbe");
        final List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        final ProcessInstance processInstance = list.get(0);
        log.info("{}",list);
    }

    @RequestMapping("list2")
    public void revertTask(String processId,String startBeforeActivityName){
        final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey("").singleResult();
        final Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
        runtimeService.createProcessInstanceModification(processId).cancelActivityInstance(processId).startBeforeActivity(startBeforeActivityName).execute();
    }

    @RequestMapping("clear")
    public void clearProcess(String processId,String startBeforeActivityName){
        final List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        list.forEach(item->{
            runtimeService.deleteProcessInstance(item.getId(),"sa");
        });
    }
}
