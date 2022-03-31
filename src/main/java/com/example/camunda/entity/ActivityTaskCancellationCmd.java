package com.example.camunda.entity;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.cmd.ActivityInstanceCancellationCmd;
import org.camunda.bpm.engine.impl.db.entitymanager.cache.DbEntityCache;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
public class ActivityTaskCancellationCmd extends ActivityInstanceCancellationCmd {

    protected String deleteReason = "deleted";
    protected String taskId;

    public ActivityTaskCancellationCmd(String processInstanceId, String taskId) {
        super(processInstanceId, processInstanceId);
        this.taskId = taskId;
    }

    public ActivityTaskCancellationCmd(String processInstanceId, String taskId , String deleteReason) {
        this(processInstanceId, taskId);
        this.deleteReason = deleteReason;
    }

    public Void execute(CommandContext commandContext) {
        super.execute(commandContext);
        try {
            final DbEntityCache dbEntityCache = commandContext.getDbEntityManager().getDbEntityCache();
            final Class<TaskEntity> taskEntityClass = TaskEntity.class;
            final List<TaskEntity> entitiesByType = dbEntityCache.getEntitiesByType(taskEntityClass);
            final Field deleteReasonField = taskEntityClass.getDeclaredField("deleteReason");
            deleteReasonField.setAccessible(true);
            for (TaskEntity task : entitiesByType) {
                if (taskId.equals(task.getId())) {
                    deleteReasonField.set(task, deleteReason);
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    protected String describe() {
        return "Cancel activity task '" + taskId + "'";
    }

}
