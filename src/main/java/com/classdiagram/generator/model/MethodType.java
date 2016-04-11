package com.classdiagram.generator.model;

import java.util.ArrayList;
import java.util.List;

public class MethodType implements Type {
	private String methodModifier;
	private String methodName;
	private String methodReturnType;
	private List<ParameterType> listOfParameters;

	public MethodType() {
		listOfParameters = new ArrayList<MethodType.ParameterType>();
	}

	public String getMethodModifier() {
		return methodModifier;
	}

	public void setMethodModifier(String methodModifier) {
		this.methodModifier = methodModifier;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodReturnType() {
		return methodReturnType;
	}

	public void setMethodReturnType(String methodReturnType) {
		this.methodReturnType = methodReturnType;
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
		stringBuilder.append(this.methodModifier);
		stringBuilder.append(this.methodName);
		stringBuilder.append("(");
		
		for (ParameterType parameterSignature : this.listOfParameters) {
			stringBuilder.append(parameterSignature.parameterValue);
			stringBuilder.append(":");
			stringBuilder.append(parameterSignature.parameterType);
			stringBuilder.append(",");
		}
		if (stringBuilder.toString().contains(",")) {
			//replacing last , from the string
			stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
		}
		stringBuilder.append(")");
		stringBuilder.append(":");
		stringBuilder.append(this.methodReturnType);
		return stringBuilder.toString();
	}
}
