package com.classdiagram.generator.model;

public class TypeRelationship {

	private TypeContainer typeContainerOne;
	private TypeContainer typeContainerTwo;
	private String relationshipNameFromOneToTwo;
	private String relationshipNameFromTwoToOne;
	private String cardinalityOne;
	private String cardinalityTwo;

	public TypeRelationship() {
		this.relationshipNameFromOneToTwo = "";
		this.relationshipNameFromTwoToOne = "";
		this.cardinalityOne = "";
		this.cardinalityTwo = "";
	}

	public TypeContainer getTypeContainerOne() {
		return typeContainerOne;
	}

	public void setTypeContainerOne(TypeContainer typeContainerOne) {
		this.typeContainerOne = typeContainerOne;
	}

	public TypeContainer getTypeContainerTwo() {
		return typeContainerTwo;
	}

	public void setTypeContainerTwo(TypeContainer typeContainerTwo) {
		this.typeContainerTwo = typeContainerTwo;
	}

	public String getRelationshipNameFromOneToTwo() {
		return relationshipNameFromOneToTwo;
	}

	public void setRelationshipNameFromOneToTwo(String relationshipNameFromOneToTwo) {
		this.relationshipNameFromOneToTwo = relationshipNameFromOneToTwo;
	}

	public String getRelationshipNameFromTwoToOne() {
		return relationshipNameFromTwoToOne;
	}

	public void setRelationshipNameFromTwoToOne(String relationshipNameFromTwoToOne) {
		this.relationshipNameFromTwoToOne = relationshipNameFromTwoToOne;
	}

	public String getCardinalityOne() {
		return cardinalityOne;
	}

	public void setCardinalityOne(String cardinalityOne) {
		this.cardinalityOne = cardinalityOne;
	}

	public String getCardinalityTwo() {
		return cardinalityTwo;
	}

	public void setCardinalityTwo(String cardinalityTwo) {
		this.cardinalityTwo = cardinalityTwo;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.typeContainerOne.toString());
		stringBuilder.append(this.relationshipNameFromOneToTwo);
		stringBuilder.append(this.cardinalityOne);
		stringBuilder.append("-");
		stringBuilder.append(this.cardinalityTwo);
		stringBuilder.append(this.relationshipNameFromTwoToOne);
		stringBuilder.append(this.typeContainerTwo.toString());
		stringBuilder.append(",");
		return stringBuilder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardinalityOne == null) ? 0 : cardinalityOne.hashCode());
		result = prime * result + ((cardinalityTwo == null) ? 0 : cardinalityTwo.hashCode());
		result = prime * result
				+ ((relationshipNameFromOneToTwo == null) ? 0 : relationshipNameFromOneToTwo.hashCode());
		result = prime * result
				+ ((relationshipNameFromTwoToOne == null) ? 0 : relationshipNameFromTwoToOne.hashCode());
		result = prime * result + ((typeContainerOne == null) ? 0 : typeContainerOne.hashCode());
		result = prime * result + ((typeContainerTwo == null) ? 0 : typeContainerTwo.hashCode());
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
		TypeRelationship other = (TypeRelationship) obj;
		if (other.typeContainerOne.equals(this.typeContainerOne)
				&& other.typeContainerTwo.equals(this.typeContainerTwo)) {
			if (other.relationshipNameFromOneToTwo.equals(this.relationshipNameFromOneToTwo)
					&& other.relationshipNameFromTwoToOne.equals(this.relationshipNameFromTwoToOne)) {
				if (other.cardinalityOne.equals(this.cardinalityOne)
						&& other.cardinalityTwo.equals(this.cardinalityTwo)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/*
	 * public int compareTo(String anotherString) { int nameCompareResult =
	 * typeContainerOne.getTypeName().compareTo(typeContainerTwo.getTypeName());
	 * //if(nameCompareResult) return -1; }
	 */

}
