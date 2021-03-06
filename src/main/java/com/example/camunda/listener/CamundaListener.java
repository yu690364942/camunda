package com.example.camunda.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>TiTle: CamundaListener</p>
 * <p>Description: CamundaListener</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/1/25
 */
@Slf4j
@Component
public class CamundaListener implements TaskListener {

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;
    @Override
    public void notify(DelegateTask delegateTask) {

        log.info("{} {}",delegateTask.getId(),delegateTask.getEventName());
        log.info("reason {}",delegateTask.getDeleteReason());
    }
}
