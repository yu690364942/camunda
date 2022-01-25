package com.example.camunda.controller;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>TiTle: TaskController</p>
 * <p>Description: TaskController</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/1/25
 */
@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @RequestMapping("list")
    public List<Task> listAll(String businessKey){
        final List<Task> list = taskService.createTaskQuery().caseInstanceBusinessKey(businessKey).list();
        return list;
    }

    @RequestMapping("claim")
    public void accept(String businessKey,String assigner){
        final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        final Task task = taskService.createTaskQuery().caseExecutionId(processInstance.getId()).singleResult();
        taskService.createComment(task.getId(),processInstance.getId(),"同意");
        taskService.complete(task.getId());
    }

    @RequestMapping("refuse")
    public void refuse(String businessKey,String assigner){
        final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        final Task task = taskService.createTaskQuery().caseExecutionId(processInstance.getId()).singleResult();
        taskService.createComment(task.getId(),processInstance.getId(),"同意");
        taskService.complete(task.getId());
    }

}
