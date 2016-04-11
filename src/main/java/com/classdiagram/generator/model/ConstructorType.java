package com.classdiagram.generator.model;

import java.util.ArrayList;
import java.util.List;

public class ConstructorType implements Type {

	private String constructorModifier;
	private String constructorName;
	private List<ParameterType> listOfParameters;

	public ConstructorType() {
		listOfParameters = new ArrayList<ConstructorType.ParameterType>();
	}

	public String getConstructorModifier() {
		return constructorModifier;
	}

	public void setConstructorModifier(String constructorModifier) {
		this.constructorModifier = constructorModifier;
	}

	public String getConstructorName() {
		return constructorName;
	}

	public void setConstructorName(String constructorName) {
		this.constructorName = constructorName;
	}

	public void addToParameterList(ParameterType parameterType) {
		listOfParameters.add(parameterType);
	}

	public static class ParameterType {
		private String parameterType;
		private String parameterValue;

		public String getParameterType() {
			return parameterType;
		}

		public void setParameterType(String parameterType) {
			this.parameterType = parameterType;
		}

		public String getParameterValue() {
			return parameterValue;
		}

		public void setParameterValue(String parameterValue) {
			this.parameterValue = parameterValue;
		}

	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.constructorModifier);
		stringBuilder.append(this.constructorName);
		stringBuilder.append("(");

		for (ParameterType parameterSignature : this.listOfParameters) {
			stringBuilder.append(parameterSignature.parameterValue);
			stringBuilder.append(":");
			stringBuilder.append(parameterSignature.parameterType);
			stringBuilder.append(",");
		}
		if (stringBuilder.toString().contains(",")) {
			// replacing last , from the string
			stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
		}
		stringBuilder.append(")");
		return stringBuilder.toString();
	}

}
