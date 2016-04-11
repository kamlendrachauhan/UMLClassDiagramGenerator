package com.classdiagram.generator.model;

import java.util.List;

public class TypeContainer {

	private boolean isInterface;
	private String typeName;
	private String typeExtends;
	private List<String> typeImplements;
	private List<FieldType> typeAttributes;
	private List<MethodType> typeMethods;
	private List<ConstructorType> typeConstructors;

	public boolean isInterface() {
		return isInterface;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeExtends() {
		return typeExtends;
	}

	public void setTypeExtends(String typeExtends) {
		this.typeExtends = typeExtends;
	}

	public List<String> getTypeImplements() {
		return typeImplements;
	}

	public void setTypeImplements(List<String> typeImplements) {
		this.typeImplements = typeImplements;
	}

	public List<FieldType> getTypeAttributes() {
		return typeAttributes;
	}

	public void setTypeAttributes(List<FieldType> typeAttributes) {
		this.typeAttributes = typeAttributes;
	}

	public List<MethodType> getTypeMethods() {
		return typeMethods;
	}

	public void setTypeMethods(List<MethodType> typeMethods) {
		this.typeMethods = typeMethods;
	}

	public List<ConstructorType> getTypeConstructors() {
		return typeConstructors;
	}

	public void setTypeConstructors(List<ConstructorType> typeConstructors) {
		this.typeConstructors = typeConstructors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isInterface ? 1231 : 1237);
		result = prime * result + ((typeAttributes == null) ? 0 : typeAttributes.hashCode());
		result = prime * result + ((typeExtends == null) ? 0 : typeExtends.hashCode());
		result = prime * result + ((typeImplements == null) ? 0 : typeImplements.hashCode());
		result = prime * result + ((typeMethods == null) ? 0 : typeMethods.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeContainer other = (TypeContainer) obj;

		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");

		if (this.isInterface) {
			stringBuilder.append("＜＜interface＞＞");
			stringBuilder.append(";");
		}
		stringBuilder.append(this.typeName);
		stringBuilder.append("|");

		if (this.typeAttributes != null && !this.typeAttributes.isEmpty()) {
			for (FieldType field : this.typeAttributes) {
				stringBuilder.append(field.toString());
				stringBuilder.append(";");
			}
		}
		// boolean partitionPresent = false;
		stringBuilder.append("|");

		if (this.typeConstructors != null && !this.typeConstructors.isEmpty()) {
			for (ConstructorType constructor : this.typeConstructors) {
				stringBuilder.append(constructor.toString());
				stringBuilder.append(";");
			}
			// partitionPresent = true;
		}
		if (this.typeMethods != null && !this.typeMethods.isEmpty()) {
			// if (!partitionPresent)
			// stringBuilder.append("|");

			for (MethodType method : this.typeMethods) {
				stringBuilder.append(method.toString());
				stringBuilder.append(";");
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();

	}

	public int compareTo(String anotherString) {
		return typeName.compareTo(anotherString);
	}

	public int compareTo(TypeContainer typeContainer) {
		return typeName.compareTo(typeContainer.getTypeName());
	}
}
