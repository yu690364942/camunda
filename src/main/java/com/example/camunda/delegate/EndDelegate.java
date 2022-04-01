package com.example.camunda.delegate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * <p>TiTle: EndDelegate</p>
 * <p>Description: EndDelegate</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/1/25
 */
@Slf4j
@Component
@Data
public class EndDelegate implements JavaDelegate {
    private Expression text1;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final Object value = text1.getValue(delegateExecution);
        log.info("{}:{}", text1.hashCode(),value);
        log.info("end {}",delegateExecution);
    }
}
