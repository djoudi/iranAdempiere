package org.adempiere.jbpm;

public class Transition {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Transition(" + name + ")";
	}

}
