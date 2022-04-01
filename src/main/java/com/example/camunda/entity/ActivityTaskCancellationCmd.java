package com.example.camunda.entity;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.exception.NotValidException;
import org.camunda.bpm.engine.impl.ActivityExecutionTreeMapping;
import org.camunda.bpm.engine.impl.cmd.AbstractInstanceCancellationCmd;
import org.camunda.bpm.engine.impl.cmd.ActivityInstanceCancellationCmd;
import org.camunda.bpm.engine.impl.cmd.GetActivityInstanceCmd;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.db.entitymanager.DbEntityManager;
import org.camunda.bpm.engine.impl.db.entitymanager.cache.DbEntityCache;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionManager;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.util.CollectionUtil;
import org.camunda.bpm.engine.impl.util.EnsureUtil;
import org.camunda.bpm.engine.runtime.ActivityInstance;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class ActivityTaskCancellationCmd extends AbstractInstanceCancellationCmd {
    protected String activityInstanceId;
    protected String deleteReason = "deleted";
    protected String taskId;


    public ActivityTaskCancellationCmd(String processInstanceId, String taskId) {
        super(processInstanceId, processInstanceId);
        this.taskId = taskId;
        this.activityInstanceId = processInstanceId;
    }

    public ActivityTaskCancellationCmd(String processInstanceId, String taskId, String deleteReason) {
        this(processInstanceId, taskId);
        this.deleteReason = deleteReason;
        this.activityInstanceId = processInstanceId;
    }

    @Override
    protected ExecutionEntity determineSourceInstanceExecution(CommandContext commandContext) {
        // final DbEntityManager session = Context.getCommandContext().getSession(DbEntityManager.class);
        // MyExecutionEntity processInstance = session.selectById(MyExecutionEntity.class, processInstanceId);
        ExecutionEntity processInstance = commandContext.getExecutionManager().findExecutionById(processInstanceId);
         // processInstance = processInstanceSrc;
        // rebuild the mapping because the execution tree changes with every iteration
        ActivityExecutionTreeMapping mapping = new ActivityExecutionTreeMapping(commandContext, processInstanceId);
        ActivityInstance instance = commandContext.runWithoutAuthorization(new Callable<ActivityInstance>() {
            @Override
            public ActivityInstance call() throws Exception {
                return new GetActivityInstanceCmd(processInstanceId).execute(commandContext);
            }
        });

        ActivityInstance instanceToCancel = findActivityInstance(instance, activityInstanceId);
        EnsureUtil.ensureNotNull(NotValidException.class,
                describeFailure("Activity instance '" + activityInstanceId + "' does not exist"),
                "activityInstance",
                instanceToCancel);
        ExecutionEntity scopeExecution = getScopeExecutionForActivityInstance(processInstance, mapping, instanceToCancel);
        ExecutionEntity processInstanceDst = new MyExecutionEntity(taskId, deleteReason);
        transfer(scopeExecution, processInstanceDst, ExecutionEntity.class);
        return processInstanceDst;
    }

    private ExecutionEntity tranfAllExcution(ExecutionEntity execution){
        if(CollectionUtil.isEmpty(execution.getExecutions()))  {
        }
        return null;
    };
    private static <D, S> void transfer(S src, D dst, Class<?> clazz) {
        for (Field declaredField : clazz.getDeclaredFields()) {
            try {
                declaredField.setAccessible(true);
                final Object o = declaredField.get(src);
                declaredField.set(dst, o);
            } catch (Exception e) {

            }
        }
        if (clazz != Object.class) {
            transfer(src, dst, clazz.getSuperclass());
        }
    }

    @Override
    protected String describe() {
        return "Cancel activity task '" + taskId + "'";
    }

}
