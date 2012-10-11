package org.adempiere.jbpm;

import java.util.List;
import java.util.Properties;

import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jpdl.el.impl.JbpmExpressionEvaluator;
import org.jbpm.taskmgmt.exe.TaskInstance;

public class ManagedJbpmContext {

	private JbpmContext jbpmContext;
	private Long processId;
	private Long taskId;

	/** Logger */
	private static CLogger log = CLogger.getCLogger(ManagedJbpmContext.class);

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public ManagedJbpmContext() {
		jbpmContext = Jbpm.instance().getConfiguration().createJbpmContext();
//		JbpmExpressionEvaluator.setExpressionEvaluator( new AdempiereExpressionEvaluator() );
	}

	public List<TaskInstance> getTaskInstanceList(String actorId) {
		if (actorId == null)
			return null;

		return jbpmContext.getTaskList(actorId);
	}

	//TODO : close context
	public void createProcess(String processDefinitionName,
			boolean shouldSignalProcess) {
		
		ProcessInstance process = jbpmContext
				.newProcessInstanceForUpdate(processDefinitionName);
		
		afterCreateProcess(processDefinitionName, process, shouldSignalProcess);
	}

	//TODO : set variable in a better way
	private void afterCreateProcess(String processDefinitionName,
			ProcessInstance process, boolean shouldSignalProcess) {
		setProcessId(process.getId());
		// need to set process variables before the signal
		setProcessVariables(process);
		if (shouldSignalProcess) {
			process.signal();
		}

	}

	private void setProcessVariables(ProcessInstance process) {
		ContextInstance contextInstance = 
				  process.getContextInstance();
		Properties ctx = Env.getCtx();
		contextInstance.setVariable("AD_Org_ID", Env.getAD_Org_ID(ctx));
		contextInstance.setVariable("AD_Client_ID", Env.getAD_Client_ID(ctx));
		contextInstance.setVariable("AD_Role_ID", Env.getAD_Role_ID(ctx));
		contextInstance.setVariable("AD_User_ID", Env.getAD_User_ID(ctx));
	}

	public void startTask() {
		String actorId = Actor.getInstance().getId();
		TaskInstance task = jbpmContext.getTaskInstanceForUpdate(getTaskId());
		if (actorId != null) {
			task.start(actorId);
		} else {
			task.start();
		}

	}

	public void endTask(String transitionName) {
		TaskInstance task = jbpmContext.getTaskInstanceForUpdate(getTaskId());
		if (task == null) {
			throw new IllegalStateException(
					"no task instance associated with context");
		}

		if (transitionName == null || "".equals(transitionName)) {
			transitionName = new Transition().getName();
		}

		if (transitionName == null) {
			task.end();
		} else {
			task.end(transitionName);
		}

		setTaskId(null); // TODO: do I really need this???!

		ProcessInstance process = jbpmContext
				.getProcessInstance(getProcessId());
		if (process.hasEnded()) {
		}
	}

	private void deployProcess(JbpmContext jbpmCtx,
			String definitionResource) {
		ProcessDefinition processDefinition = ProcessDefinition
				.parseXmlResource(definitionResource);
		log.info("deploying process definition : "
				+ processDefinition.getName());
		jbpmCtx.deployProcessDefinition(processDefinition);
	}

	public void installProcessDefinitions(String[] processDefinitions) {
		try {
			if (processDefinitions != null) {
				for (String definitionResource : processDefinitions) {
					deployProcess(jbpmContext, definitionResource);
				}
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("could not deploy a process definition",
					e);
		} 
	}
	
	//TODO : check if graphSession is close
	public boolean isSessionClosed(){
		return true;
	}
	
	

}
