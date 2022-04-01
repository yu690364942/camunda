package com.example.camunda.entity;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateListener;
import org.camunda.bpm.engine.impl.cmmn.entity.runtime.CaseExecutionEntity;
import org.camunda.bpm.engine.impl.cmmn.execution.CmmnExecution;
import org.camunda.bpm.engine.impl.cmmn.model.CmmnCaseDefinition;
import org.camunda.bpm.engine.impl.core.instance.CoreExecution;
import org.camunda.bpm.engine.impl.core.model.CoreModelElement;
import org.camunda.bpm.engine.impl.core.operation.CoreAtomicOperation;
import org.camunda.bpm.engine.impl.core.variable.CoreVariableInstance;
import org.camunda.bpm.engine.impl.core.variable.event.VariableEvent;
import org.camunda.bpm.engine.impl.core.variable.scope.VariableInstanceFactory;
import org.camunda.bpm.engine.impl.core.variable.scope.VariableInstanceLifecycleListener;
import org.camunda.bpm.engine.impl.core.variable.scope.VariableStore;
import org.camunda.bpm.engine.impl.interceptor.AtomicOperationInvocation;
import org.camunda.bpm.engine.impl.jobexecutor.TimerDeclarationImpl;
import org.camunda.bpm.engine.impl.persistence.entity.*;
import org.camunda.bpm.engine.impl.pvm.PvmActivity;
import org.camunda.bpm.engine.impl.pvm.PvmProcessDefinition;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.camunda.bpm.engine.impl.pvm.runtime.AtomicOperation;
import org.camunda.bpm.engine.impl.pvm.runtime.ProcessInstanceStartContext;
import org.camunda.bpm.engine.impl.pvm.runtime.PvmExecutionImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowElement;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>TiTle: MyExecutionEntity</p>
 * <p>Description: MyExecutionEntity</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/4/1
 */
public class MyExecutionEntity2 extends ExecutionEntity {
    private String taskId;
    private String deleteReason;
    private ExecutionEntity execution;


    public MyExecutionEntity2(ExecutionEntity execution, String taskId, String deleteReason) {
     this.taskId = taskId;
     this.deleteReason = deleteReason;
     this.execution =execution;
    }

    @Override
    public Map<String, Class> getDependentEntities() {
        return execution.getDependentEntities();
    }

    @Override
    public void removeAllTasks() {
        // delete all the tasks
        removeTasks(null);

        // delete external tasks
        removeExternalTasks();
    }
    @Override
    protected void removeTasks(String reason) {
        if (reason == null) {
            reason = TaskEntity.DELETE_REASON_DELETED;
        }
        for (TaskEntity task : getTasks()) {
            if (isReplacedByParent()) {
                if (task.getExecution() == null || task.getExecution() != replacedBy) {
                    // All tasks should have been moved when "replacedBy" has been set.
                    // Just in case tasks where added,
                    // wo do an additional check here and move it
                    task.setExecution(replacedBy);
                    this.getReplacedBy().addTask(task);
                }
            } else {
                task.delete(reason, false, skipCustomListeners);
            }
        }
    }


    @Override
    public ExecutionEntity createExecution() {
        return execution.createExecution();
    }

    @Override
    public ExecutionEntity createExecution(boolean initializeExecutionStartContext) {
        return execution.createExecution(initializeExecutionStartContext);
    }

    @Override
    public ExecutionEntity createSubProcessInstance(PvmProcessDefinition processDefinition, String businessKey, String caseInstanceId) {
        return execution.createSubProcessInstance(processDefinition, businessKey, caseInstanceId);
    }


    @Override
    public CaseExecutionEntity createSubCaseInstance(CmmnCaseDefinition caseDefinition) {
        return execution.createSubCaseInstance(caseDefinition);
    }

    @Override
    public CaseExecutionEntity createSubCaseInstance(CmmnCaseDefinition caseDefinition, String businessKey) {
        return execution.createSubCaseInstance(caseDefinition, businessKey);
    }

    @Override
    public void fireHistoricActivityInstanceUpdate() {
        execution.fireHistoricActivityInstanceUpdate();
    }

    @Override
    public void initialize() {
        execution.initialize();
    }

    @Override
    public void initializeTimerDeclarations() {
        execution.initializeTimerDeclarations();
    }

    @Override
    public void start(Map<String, Object> variables, VariableMap formProperties) {
        execution.start(variables, formProperties);
    }

    @Override
    public void startWithoutExecuting(Map<String, Object> variables) {
        execution.startWithoutExecuting(variables);
    }


    @Override
    public void fireHistoricProcessStartEvent() {
        execution.fireHistoricProcessStartEvent();
    }

    @Override
    public void destroy() {
        execution.destroy();
    }


    @Override
    public void removeVariablesLocalInternal() {
        execution.removeVariablesLocalInternal();
    }

    @Override
    public void interrupt(String reason, boolean skipCustomListeners, boolean skipIoMappings, boolean externallyTerminated) {
        execution.interrupt(reason, skipCustomListeners, skipIoMappings, externallyTerminated);
    }

    @Override
    public <T extends CoreExecution> void performOperation(CoreAtomicOperation<T> operation) {
        execution.performOperation(operation);
    }

    @Override
    public <T extends CoreExecution> void performOperationSync(CoreAtomicOperation<T> operation) {
        execution.performOperationSync(operation);
    }

    @Override
    public void performOperation(AtomicOperation executionOperation) {
        execution.performOperation(executionOperation);
    }

    @Override
    public void performOperationSync(AtomicOperation executionOperation) {
        execution.performOperationSync(executionOperation);
    }

    @Override
    public void scheduleAtomicOperationAsync(AtomicOperationInvocation executionOperationInvocation) {
        execution.scheduleAtomicOperationAsync(executionOperationInvocation);
    }

    @Override
    public boolean isActive(String activityId) {
        return execution.isActive(activityId);
    }

    @Override
    public void inactivate() {
        execution.inactivate();
    }

    @Override
    public void addExecutionObserver(ExecutionObserver observer) {
        execution.addExecutionObserver(observer);
    }

    @Override
    public void removeExecutionObserver(ExecutionObserver observer) {
        execution.removeExecutionObserver(observer);
    }

    @Override
    public List<ExecutionEntity> getExecutions() {
        return execution.getExecutions();
    }

    @Override
    public List<ExecutionEntity> getExecutionsAsCopy() {
        return execution.getExecutionsAsCopy();
    }

    @Override
    public void setExecutions(List<ExecutionEntity> executions) {
        execution.setExecutions(executions);
    }

    @Override
    public String getProcessBusinessKey() {
        return execution.getProcessBusinessKey();
    }

    @Override
    public ProcessDefinitionEntity getProcessDefinition() {
        return execution.getProcessDefinition();
    }

    @Override
    public void setProcessDefinitionId(String processDefinitionId) {
        execution.setProcessDefinitionId(processDefinitionId);
    }

    @Override
    public String getProcessDefinitionId() {
        return execution.getProcessDefinitionId();
    }

    @Override
    public void setProcessDefinition(ProcessDefinitionImpl processDefinition) {
        execution.setProcessDefinition(processDefinition);
    }

    @Override
    public ExecutionEntity getProcessInstance() {
        return execution.getProcessInstance();
    }


    @Override
    public boolean isProcessInstanceStarting() {
        return execution.isProcessInstanceStarting();
    }

    @Override
    public void setProcessInstance(PvmExecutionImpl processInstance) {
        execution.setProcessInstance(processInstance);
    }

    @Override
    public boolean isProcessInstanceExecution() {
        return execution.isProcessInstanceExecution();
    }

    @Override
    public ActivityImpl getActivity() {
        return execution.getActivity();
    }

    @Override
    public String getActivityId() {
        return execution.getActivityId();
    }

    @Override
    public void setActivity(PvmActivity activity) {
        execution.setActivity(activity);
    }

    @Override
    public ExecutionEntity getParent() {
        return execution.getParent();
    }


    @Override
    public void setParentExecution(PvmExecutionImpl parent) {
        execution.setParentExecution(parent);
    }


    @Override
    public ExecutionEntity getSubProcessInstance() {
        return execution.getSubProcessInstance();
    }

    @Override
    public void setSubProcessInstance(PvmExecutionImpl subProcessInstance) {
        execution.setSubProcessInstance(subProcessInstance);
    }

    @Override
    public CaseExecutionEntity getSubCaseInstance() {
        return execution.getSubCaseInstance();
    }

    @Override
    public void setSubCaseInstance(CmmnExecution subCaseInstance) {
        execution.setSubCaseInstance(subCaseInstance);
    }



    @Override
    public void remove() {
        execution.remove();
    }


    @Override
    public void removeEventSubscriptions() {
        execution.removeEventSubscriptions();
    }


    @Override
    public ExecutionEntity getReplacedBy() {
        return execution.getReplacedBy();
    }

    @Override
    public ExecutionEntity resolveReplacedBy() {
        return execution.resolveReplacedBy();
    }

    @Override
    public void replace(PvmExecutionImpl execution) {
        execution.replace(execution);
    }

    @Override
    public void onConcurrentExpand(PvmExecutionImpl scopeExecution) {
        execution.onConcurrentExpand(scopeExecution);
    }




    @Override
    public void addVariableListener(VariableInstanceLifecycleListener<VariableInstanceEntity> listener) {
        execution.addVariableListener(listener);
    }

    @Override
    public void removeVariableListener(VariableInstanceLifecycleListener<VariableInstanceEntity> listener) {
        execution.removeVariableListener(listener);
    }

    @Override
    public boolean isExecutingScopeLeafActivity() {
        return execution.isExecutingScopeLeafActivity();
    }

    @Override
    public Collection<VariableInstanceEntity> provideVariables() {
        return execution.provideVariables();
    }

    @Override
    public Collection<VariableInstanceEntity> provideVariables(Collection<String> variableNames) {
        return execution.provideVariables(variableNames);
    }


    @Override
    public void restoreProcessInstance(Collection<ExecutionEntity> executions, Collection<EventSubscriptionEntity> eventSubscriptions, Collection<VariableInstanceEntity> variables, Collection<TaskEntity> tasks, Collection<JobEntity> jobs, Collection<IncidentEntity> incidents, Collection<ExternalTaskEntity> externalTasks) {
        execution.restoreProcessInstance(executions, eventSubscriptions, variables, tasks, jobs, incidents, externalTasks);
    }

    @Override
    public Object getPersistentState() {
        return execution.getPersistentState();
    }

    @Override
    public void insert() {
        execution.insert();
    }

    @Override
    public int getRevisionNext() {
        return execution.getRevisionNext();
    }

    @Override
    public void forceUpdate() {
        execution.forceUpdate();
    }

    @Override
    public String toString() {
        return execution.toString();
    }


    @Override
    public List<EventSubscriptionEntity> getEventSubscriptionsInternal() {
        return execution.getEventSubscriptionsInternal();
    }

    @Override
    public List<EventSubscriptionEntity> getEventSubscriptions() {
        return execution.getEventSubscriptions();
    }

    @Override
    public List<EventSubscriptionEntity> getCompensateEventSubscriptions() {
        return execution.getCompensateEventSubscriptions();
    }

    @Override
    public List<EventSubscriptionEntity> getCompensateEventSubscriptions(String activityId) {
        return execution.getCompensateEventSubscriptions(activityId);
    }

    @Override
    public void addEventSubscription(EventSubscriptionEntity eventSubscriptionEntity) {
        execution.addEventSubscription(eventSubscriptionEntity);
    }

    @Override
    public void removeEventSubscription(EventSubscriptionEntity eventSubscriptionEntity) {
        execution.removeEventSubscription(eventSubscriptionEntity);
    }




    @Override
    public List<JobEntity> getJobs() {
        return execution.getJobs();
    }

    @Override
    public void addJob(JobEntity jobEntity) {
        execution.addJob(jobEntity);
    }

    @Override
    public void removeJob(JobEntity job) {
        execution.removeJob(job);
    }



    @Override
    public List<IncidentEntity> getIncidents() {
        return execution.getIncidents();
    }

    @Override
    public void addIncident(IncidentEntity incident) {
        execution.addIncident(incident);
    }

    @Override
    public void removeIncident(IncidentEntity incident) {
        execution.removeIncident(incident);
    }

    @Override
    public IncidentEntity getIncidentByCauseIncidentId(String causeIncidentId) {
        return execution.getIncidentByCauseIncidentId(causeIncidentId);
    }




    @Override
    public List<TaskEntity> getTasks() {
        return execution.getTasks();
    }

    @Override
    public void addTask(TaskEntity taskEntity) {
        execution.addTask(taskEntity);
    }

    @Override
    public void removeTask(TaskEntity task) {
        execution.removeTask(task);
    }



    @Override
    public void addExternalTask(ExternalTaskEntity externalTask) {
        execution.addExternalTask(externalTask);
    }

    @Override
    public void removeExternalTask(ExternalTaskEntity externalTask) {
        execution.removeExternalTask(externalTask);
    }

    @Override
    public List<ExternalTaskEntity> getExternalTasks() {
        return execution.getExternalTasks();
    }





    @Override
    public VariableInstanceLifecycleListener<CoreVariableInstance> getVariablePersistenceListener() {
        return execution.getVariablePersistenceListener();
    }

    @Override
    public Collection<VariableInstanceEntity> getVariablesInternal() {
        return execution.getVariablesInternal();
    }

    @Override
    public void removeVariableInternal(VariableInstanceEntity variable) {
        execution.removeVariableInternal(variable);
    }

    @Override
    public void addVariableInternal(VariableInstanceEntity variable) {
        execution.addVariableInternal(variable);
    }

    @Override
    public void handleConditionalEventOnVariableChange(VariableEvent variableEvent) {
        execution.handleConditionalEventOnVariableChange(variableEvent);
    }

    @Override
    public void dispatchEvent(VariableEvent variableEvent) {
        execution.dispatchEvent(variableEvent);
    }

    @Override
    public void setCachedEntityState(int cachedEntityState) {
        execution.setCachedEntityState(cachedEntityState);
    }

    @Override
    public int getCachedEntityState() {
        return execution.getCachedEntityState();
    }

    @Override
    public int getCachedEntityStateRaw() {
        return execution.getCachedEntityStateRaw();
    }

    @Override
    public String getRootProcessInstanceId() {
        return execution.getRootProcessInstanceId();
    }

    @Override
    public String getRootProcessInstanceIdRaw() {
        return execution.getRootProcessInstanceIdRaw();
    }

    @Override
    public void setRootProcessInstanceId(String rootProcessInstanceId) {
        execution.setRootProcessInstanceId(rootProcessInstanceId);
    }

    @Override
    public String getProcessInstanceId() {
        return execution.getProcessInstanceId();
    }

    @Override
    public void setProcessInstanceId(String processInstanceId) {
        execution.setProcessInstanceId(processInstanceId);
    }

    @Override
    public String getParentId() {
        return execution.getParentId();
    }

    @Override
    public void setParentId(String parentId) {
        execution.setParentId(parentId);
    }

    @Override
    public int getRevision() {
        return execution.getRevision();
    }

    @Override
    public void setRevision(int revision) {
        execution.setRevision(revision);
    }

    @Override
    public void setActivityId(String activityId) {
        execution.setActivityId(activityId);
    }



    @Override
    public Set<String> getReferencedEntityIds() {
        return execution.getReferencedEntityIds();
    }

    @Override
    public Map<String, Class> getReferencedEntitiesIdAndClass() {
        return execution.getReferencedEntitiesIdAndClass();
    }

    @Override
    public int getSuspensionState() {
        return execution.getSuspensionState();
    }

    @Override
    public void setSuspensionState(int suspensionState) {
        execution.setSuspensionState(suspensionState);
    }

    @Override
    public boolean isSuspended() {
        return execution.isSuspended();
    }

    @Override
    public ProcessInstanceStartContext getProcessInstanceStartContext() {
        return execution.getProcessInstanceStartContext();
    }

    @Override
    public String getCurrentActivityId() {
        return execution.getCurrentActivityId();
    }

    @Override
    public String getCurrentActivityName() {
        return execution.getCurrentActivityName();
    }

    @Override
    public FlowElement getBpmnModelElementInstance() {
        return execution.getBpmnModelElementInstance();
    }

    @Override
    public BpmnModelInstance getBpmnModelInstance() {
        return execution.getBpmnModelInstance();
    }

    @Override
    public ProcessEngineServices getProcessEngineServices() {
        return execution.getProcessEngineServices();
    }

    @Override
    public ProcessEngine getProcessEngine() {
        return execution.getProcessEngine();
    }

    @Override
    public String getEventName() {
        return execution.getEventName();
    }

    @Override
    public void setEventName(String eventName) {
        execution.setEventName(eventName);
    }

    @Override
    public CoreModelElement getEventSource() {
        return execution.getEventSource();
    }

    @Override
    public void setEventSource(CoreModelElement eventSource) {
        execution.setEventSource(eventSource);
    }

    @Override
    public int getListenerIndex() {
        return execution.getListenerIndex();
    }

    @Override
    public void setListenerIndex(int listenerIndex) {
        execution.setListenerIndex(listenerIndex);
    }

    @Override
    public void invokeListener(DelegateListener listener) throws Exception {
        execution.invokeListener(listener);
    }

    @Override
    public String getId() {
        return execution.getId();
    }

    @Override
    public void setId(String id) {
        execution.setId(id);
    }

    @Override
    public String getBusinessKeyWithoutCascade() {
        return execution.getBusinessKeyWithoutCascade();
    }

    @Override
    public void setBusinessKey(String businessKey) {
        execution.setBusinessKey(businessKey);
    }

    @Override
    public String getTenantId() {
        return execution.getTenantId();
    }

    @Override
    public void setTenantId(String tenantId) {
        execution.setTenantId(tenantId);
    }

    @Override
    public boolean isSkipCustomListeners() {
        return execution.isSkipCustomListeners();
    }

    @Override
    public void setSkipCustomListeners(boolean skipCustomListeners) {
        execution.setSkipCustomListeners(skipCustomListeners);
    }

    @Override
    public boolean isSkipIoMappings() {
        return execution.isSkipIoMappings();
    }

    @Override
    public void setSkipIoMappings(boolean skipIoMappings) {
        execution.setSkipIoMappings(skipIoMappings);
    }

    @Override
    public boolean isSkipSubprocesses() {
        return execution.isSkipSubprocesses();
    }

    @Override
    public void setSkipSubprocesseses(boolean skipSubprocesses) {
        execution.setSkipSubprocesseses(skipSubprocesses);
    }

    @Override
    public int hashCode() {
        return execution.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return execution.equals(obj);
    }
}
