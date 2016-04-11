package com.classdiagram.generator.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.classdiagram.generator.common.ClassRelationship;
import com.classdiagram.generator.common.DataType;
import com.classdiagram.generator.model.FieldType;
import com.classdiagram.generator.model.MethodType;
import com.classdiagram.generator.model.TypeContainer;
import com.classdiagram.generator.model.TypeRelationship;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.VoidType;

public class MethodTypeHandler {

	public void populateMethodType(MethodDeclaration methodDeclaration, TypeContainer currentTypeContainer) {
		MethodType methodType = new MethodType();
		int modifier = methodDeclaration.getModifiers();
		String methodModifier = getModifier(modifier, "methodModifier");

		if ("-".equals(methodModifier) || "#".equals(methodModifier) || "".equals(methodModifier)) {
			// Not handling methods with private, protected and package scope
			return;
		}

		methodType.setMethodModifier(methodModifier);
		String methodName = methodDeclaration.getName();

		if (methodName.equals("main")) {
			handleMainClass(methodDeclaration, currentTypeContainer);
		}
		// Check for getter and setter for an attribute
		boolean isSetterGetterValid = checkForGetterSetterAttribute(methodName, currentTypeContainer);
		if (isSetterGetterValid)
			return;

		methodType.setMethodName(methodName);

		List<Node> childrenNodes = methodDeclaration.getChildrenNodes();

		for (Node childNode : childrenNodes) {

			if (childNode instanceof VoidType || childNode instanceof PrimitiveType) {
				// to capture return type. If return type is custom object it
				// returns Object as ReferenceType
				methodType.setMethodReturnType(childNode.toString());
			} else if (childNode instanceof ReferenceType) {
				// if the return type is Reference
				ReferenceType referenceType = (ReferenceType) childNode;
				methodType.setMethodReturnType(referenceType.toString());
			} else if (childNode instanceof Parameter) {
				// Not handling "Type<E> value" types of params for now
				String[] splitParameters = childNode.toString().split(" ");

				if (!DataType.isValidDataType(splitParameters[0])) {

					// Handle Custom classes
					// Add to the TypeRelationship object
					handleCustomTypes(currentTypeContainer, splitParameters[0]);
				} else {
					System.out.println(
							"---------------------" + childNode.toString().split(" ")[0] + "---------------------");

				}
				MethodType.ParameterType parameterType = new MethodType.ParameterType();
				parameterType.setParameterType(splitParameters[0].replace("[", "［").replace("]", "］"));
				parameterType.setParameterValue(splitParameters[1]);
				methodType.addToParameterList(parameterType);
			}
		}
		// adding methods to the final list
		List<MethodType> typeMethods = currentTypeContainer.getTypeMethods();
		if (typeMethods == null)
			typeMethods = new ArrayList<MethodType>();
		typeMethods.add(methodType);
		currentTypeContainer.setTypeMethods(typeMethods);
	}

	/*
	 * The expected output for Class Source with Java Setters/Getters is to
	 * interpret as a "Public Attribute" instead of Private with public
	 * setter/getter methods. Also, the public setter and getter methods should
	 * not be visible (i.e. not on class diagram) in the operations compartment.
	 */
	private boolean checkForGetterSetterAttribute(String methodName, TypeContainer currentTypeContainer) {
		if (methodName.startsWith("get") || methodName.startsWith("set")) {
			String attributeName = methodName.substring(3, methodName.length()).toLowerCase();
			System.out.println("Attribute Name :: " + attributeName);
			List<FieldType> listOfTypeAttributes = currentTypeContainer.getTypeAttributes();
			Iterator<FieldType> iterator = listOfTypeAttributes.iterator();

			while (iterator.hasNext()) {
				FieldType typeAttribute = iterator.next();
				String currentAttributeName = typeAttribute.getVariableName();
				if (currentAttributeName.toLowerCase().equals(attributeName)) {
					typeAttribute.setVariableModifier("+");
					return true;
				}
			}
		}
		return false;
	}

	private void handleCustomTypes(TypeContainer currentTypeContainer, String customClassName) {
		TypeContainer newTypeContainer = new TypeContainer();
		newTypeContainer.setTypeName(customClassName);

		TypeRelationship typeRelationship = new TypeRelationship();
		typeRelationship.setTypeContainerOne(currentTypeContainer);
		typeRelationship.setTypeContainerTwo(newTypeContainer);
		typeRelationship.setRelationshipNameFromOneToTwo(ClassRelationship.DEPENDENCY_SOURCE);
		typeRelationship.setRelationshipNameFromTwoToOne(ClassRelationship.DEPENDENCY_TARGET);
		System.out.println(typeRelationship.toString());
		TypeContainerManager.addToTRList(typeRelationship);
	}

	private void handleMainClass(MethodDeclaration methodDeclaration, TypeContainer currentTypeContainer) {
		List<Statement> bodyStatements = methodDeclaration.getBody().getStmts();
		String bodyStatementStrings[] = bodyStatements.toString().split(" ", 2);

		TypeContainer newTypeContainer = new TypeContainer();
		newTypeContainer.setTypeName(bodyStatementStrings[0].replace("[", "").replace("]", ""));

		TypeRelationship typeRelationship = new TypeRelationship();
		typeRelationship.setTypeContainerOne(currentTypeContainer);
		typeRelationship.setTypeContainerTwo(newTypeContainer);
		typeRelationship.setRelationshipNameFromOneToTwo(ClassRelationship.DEPENDENCY_SOURCE);
		typeRelationship.setRelationshipNameFromTwoToOne(ClassRelationship.DEPENDENCY_TARGET);
		System.out.println(typeRelationship.toString());
		TypeContainerManager.addToTRList(typeRelationship);

	}

	// TODO change this method
	public static String getModifier(int modifier, String type) {
		String modifierType = "";
		switch (modifier) {
		case ModifierSet.PRIVATE:
			modifierType = "-";
			break;
		case ModifierSet.PUBLIC:
			modifierType = "+";
			break;
		case ModifierSet.PROTECTED:
			modifierType = "#";
			break;
		default:
			if (type == "methodModifier") {
				modifierType = "+";
			}
			break;
		}
		return modifierType;
	}
}
