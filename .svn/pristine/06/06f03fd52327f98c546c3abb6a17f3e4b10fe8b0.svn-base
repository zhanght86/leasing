package org.jbpm.pvm.internal.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import org.jbpm.api.JbpmException;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.id.DbidGenerator;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.query.ProcessDefinitionQueryImpl;
import org.jbpm.pvm.internal.session.RepositorySession;
import org.jbpm.pvm.internal.util.CollectionUtil;

public class RepositorySessionImpl implements RepositorySession {

	private static Log log = Log.getLog(RepositorySessionImpl.class.getName());

	protected Session session;
	protected RepositoryCache repositoryCache;
	protected DeployerManager deployerManager;

	public String deploy(NewDeployment deployment) {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		DeploymentImpl deploymentImpl = (DeploymentImpl) deployment;

		long dbid = DbidGenerator.getDbidGenerator().getNextId();
		deploymentImpl.setDbid(dbid);
		deploymentImpl.initResourceLobDbids();

		session.save(deploymentImpl); // will also save the attached resources
		deployerManager.deploy(deploymentImpl);

		return deploymentImpl.getId();
	}

	public void updateDeploymentResource(String deploymentId, String resourceName, byte[] bytes) {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		DeploymentImpl deployment = getDeployment(deploymentId);
		deployerManager.updateResource(deployment, resourceName, bytes);
	}

	public void cascadeDeploymentSuspend(DeploymentImpl deployment) {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		// cascade to all executions in this deployment
		Set<String> processDefinitionIds = deployment.getProcessDefinitionIds();
		if (!processDefinitionIds.isEmpty()) {
			Query query = session.createQuery("select execution " + "from " + ExecutionImpl.class.getName() + " as execution "
					+ "where execution.processDefinitionId in (:processDefinitionIds) " + "  and execution.state != '"
					+ ExecutionImpl.STATE_SUSPENDED + "'");
			query.setParameterList("processDefinitionIds", processDefinitionIds);
			List<?> executions = query.list();
			for (ExecutionImpl execution : CollectionUtil.checkList(executions, ExecutionImpl.class)) {
				execution.suspend();
			}
		}

		// TODO cleaning this cache should actually be done as a synchronization
		// after the transaction. If a concurrent transaction for an execution
		// starts between clearing the cache and committing the transaction,
		// then
		// that transaction could potentially re-initialize the process
		// definition
		// in the cache.
		repositoryCache.remove(deployment.getId());
	}

	public void cascadeDeploymentResume(DeploymentImpl deployment) {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		// cascade to all executions in this deployment
		Set<String> processDefinitionIds = deployment.getProcessDefinitionIds();
		if (!processDefinitionIds.isEmpty()) {
			Query query = session.createQuery("select execution " + "from " + ExecutionImpl.class.getName() + " as execution "
					+ "where execution.processDefinitionId in (:processDefinitionIds) " + "  and execution.state = '" + ExecutionImpl.STATE_SUSPENDED
					+ "'");
			query.setParameterList("processDefinitionIds", processDefinitionIds);
			List<?> executions = query.list();
			for (ExecutionImpl execution : CollectionUtil.checkList(executions, ExecutionImpl.class)) {
				execution.resume();
			}
		}
	}

	public DeploymentImpl getDeployment(String deploymentId) {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		return (DeploymentImpl) session.get(DeploymentImpl.class, Long.parseLong(deploymentId));
	}

	public Object getObject(String deploymentId, String objectName) {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		Object object = repositoryCache.get(deploymentId, objectName);
		if (object != null) {
			log.trace("repository cache hit");

		} else if (deploymentId == null) {
			throw new JbpmException("deploymentId is null");

		} else {
			log.trace("loading deployment " + deploymentId + " from db");
			DeploymentImpl deployment = (DeploymentImpl) session.load(DeploymentImpl.class, Long.parseLong(deploymentId));
			deployerManager.deploy(deployment);
			object = repositoryCache.get(deploymentId, objectName);
			if (object == null) { throw new JbpmException("deployment " + deploymentId + " doesn't contain object " + objectName); }
		}
		return object;
	}

	public byte[] getBytes(String deploymentId, String resourceName) {
		DeploymentImpl deployment = getDeployment(deploymentId);
		if (deployment == null) { return null; }
		return deployment.getBytes(resourceName);
	}

	// queries
	// //////////////////////////////////////////////////////////////////

	public ProcessDefinitionQueryImpl createProcessDefinitionQuery() {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		return new ProcessDefinitionQueryImpl();
	}

	public ProcessDefinitionImpl findProcessDefinitionByKey(String processDefinitionKey) {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		ProcessDefinition processDefinition = createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey)
				.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).page(0, 1).uniqueResult();

		if (processDefinition != null) {
			return findProcessDefinitionById(processDefinition.getId());
		} else {
			validateRepositoryCache();
		}

		return null;
	}

	public ProcessDefinitionImpl findProcessDefinitionById(String processDefinitionId) {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		DeploymentProperty deploymentProperty = findDeploymentPropertyByProcessDefinitionId(processDefinitionId);

		if (deploymentProperty != null) {
			String deploymentId = deploymentProperty.getDeployment().getId();
			String objectName = deploymentProperty.getObjectName();

			return (ProcessDefinitionImpl) getObject(deploymentId, objectName);
		} else {
			validateRepositoryCache();
		}

		return null;
	}

	public ProcessDefinitionImpl findLatestProcessDefinitionByName(String processDefinitionName) {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		ProcessDefinition processDefinition = createProcessDefinitionQuery().processDefinitionName(processDefinitionName)
				.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).page(0, 1).uniqueResult();

		if (processDefinition != null) {
			return findProcessDefinitionById(processDefinition.getId());
		} else {
			validateRepositoryCache();
		}

		return null;
	}

	public DeploymentProperty findDeploymentPropertyByProcessDefinitionId(String processDefinitionId) {
		JBPMCACHE.setRepositoryCache(repositoryCache);
		DeploymentProperty deploymentProperty = (DeploymentProperty) session
				.createQuery(
						"select deploymentProperty " + "from " + DeploymentProperty.class.getName() + " as deploymentProperty "
								+ "where deploymentProperty.key = '" + DeploymentImpl.KEY_PROCESS_DEFINITION_ID + "' "
								+ "  and deploymentProperty.stringValue = '" + processDefinitionId + "' ").setMaxResults(1).uniqueResult();
		return deploymentProperty;
	}

	/**
	 * Checks if every entry in the repositoryCache is still valid.
	 * If the entry is not found in the database, it is deleted from the cache.
	 */
	@SuppressWarnings("unchecked")
	private void validateRepositoryCache() {
		JBPMCACHE.setRepositoryCache(repositoryCache);

		log.trace("Validating repository cache ... ");
		Set<Long> dbIds = new HashSet<Long>(session.createQuery("select dbid from " + DeploymentImpl.class.getName() + " as deployment ")
				.setReadOnly(true).list());

		Set<String> cachedIds = repositoryCache.getCachedDeploymentIds();
		for (String cachedId : cachedIds) {
			if (!dbIds.contains(Long.valueOf(cachedId))) {
				log.trace("Invalid entry in repositorycache found, removing now deployment id " + cachedId);
				repositoryCache.remove(cachedId);
			}
		}

	}

}
