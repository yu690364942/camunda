package com.example.camunda.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>TiTle: AsyncService</p>
 * <p>Description: AsyncService</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/2/22
 */
@Service
@Slf4j
public class AsyncService {
    @Async
    public boolean test() throws InterruptedException {
        log.info("123");
        TimeUnit.SECONDS.sleep(60);
        return true;
    };
}
