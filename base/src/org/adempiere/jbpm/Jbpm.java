package org.adempiere.jbpm;

import org.jbpm.JbpmConfiguration;

public class Jbpm {

	private JbpmConfiguration jbpmConfiguration;
	private static Jbpm jbpm;

	private Jbpm() {
		if (jbpmConfiguration == null) {
			initJbpmConfiguration();
		}

	}

	private void initJbpmConfiguration() {
		jbpmConfiguration = JbpmConfiguration.getInstance("org/adempiere/jbpm/jbpm.cfg.xml");
		// TODO: use jndi
	}

	public static synchronized Jbpm instance() {
		if (jbpm == null) {
			jbpm = new Jbpm();
		}
		return jbpm;
	}

	public JbpmConfiguration getConfiguration() {
		return jbpmConfiguration;
	}

}
