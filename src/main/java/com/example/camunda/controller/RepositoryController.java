package com.example.camunda.controller;

import com.example.camunda.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>TiTle: RepositoryController</p>
 * <p>Description: RepositoryController</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/1/25
 */

@Slf4j
@RestController
@RequestMapping("repository")
public class RepositoryController {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private AsyncService asyncService;
    @RequestMapping("test")
    public boolean test() throws InterruptedException {
        return asyncService.test();
    }
}
