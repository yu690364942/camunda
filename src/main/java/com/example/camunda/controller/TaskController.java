package com.example.camunda.controller;

import com.example.camunda.entity.ActivityTaskCancellationCmd;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.ProcessInstanceModificationBuilderImpl;
import org.camunda.bpm.engine.impl.cmd.AbstractProcessInstanceModificationCommand;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        // final List<Task> list = taskService.createTaskQuery().caseInstanceBusinessKey(businessKey).list();
        // return list;
        final List<Comment> taskComments = taskService.getTaskComments("e8eca4bc-d6d5-4111-b9f5-7c9fd28a28ac");
        final List<Object> app = taskService.createTaskQuery().list().stream().map(item -> {
            return taskService.getVariable(item.getId(), "app");
        }).collect(Collectors.toList());



        return null;
    }

    @RequestMapping("claim")
    public void accept(String businessKey,String assigner){
        final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        final Task task = taskService.createTaskQuery().caseExecutionId(processInstance.getId()).singleResult();
        taskService.createComment(task.getId(),processInstance.getId(),"同意");
        taskService.complete(task.getId());
    }



    @RequestMapping("refuse")
    public void refuse(String processInstanceId) {
        final ProcessInstanceModificationBuilderImpl modification = (ProcessInstanceModificationBuilderImpl) runtimeService.createProcessInstanceModification(processInstanceId);
        final List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        final ActivityTaskCancellationCmd activityTaskCancellationCmd = new ActivityTaskCancellationCmd(processInstanceId, list.get(0).getId(),"test12312");
        final ArrayList<AbstractProcessInstanceModificationCommand> commands = new ArrayList<>();
        commands.add(activityTaskCancellationCmd);
        modification.setModificationOperations(commands);
        modification.startBeforeActivity("node1").execute();
    }



}
