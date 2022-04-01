package com.example.camunda.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * <p>TiTle: MyExecutionListener</p>
 * <p>Description: MyExecutionListener</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/3/25
 */
@Component
@Slf4j
public class MyExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        log.info("notify:{}",execution.getEventName());
    }

    public void test(DelegateExecution execution,String type){
      log.info("test------------------:{}",type);
    }

}
