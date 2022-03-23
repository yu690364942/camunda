package com.example.camunda.controller;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p>TiTle: HistoryController</p>
 * <p>Description: HistoryController</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/1/25
 */
@RestController
@Slf4j
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    HistoryService historyService;

    @RequestMapping("queryHistory")
    public List<Map> list(String id){
        // final List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(id).orderByHistoricTaskInstanceEndTime().asc().list();
        final List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId("dee93e14-e1dc-4c48-a9c4-19f45dfbc26f")
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        return null;
    }


}
