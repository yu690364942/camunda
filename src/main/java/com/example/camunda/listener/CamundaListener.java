package com.example.camunda.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

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

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
