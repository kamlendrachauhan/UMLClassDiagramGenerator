package com.classdiagram.generator.model;

public class FieldType implements Type {

	private String variableModifier;
	private String variableDataType;
	private String variableName;
	private String cardinality = new String();

	public String getVariableDataType() {
		return variableDataType;
	}

	public void setVariableDataType(String variableDataType) {
		this.variableDataType = variableDataType;
	}

	public String getVariableModifier() {
		return variableModifier;
	}

	public void setVariableModifier(String variableModifier) {
		this.variableModifier = variableModifier;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getCardinality() {
		return cardinality;
	}

	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
	}

	@Override
	public String toString() {
		return variableModifier + variableName + ":" + variableDataType + cardinality;

	}

}
