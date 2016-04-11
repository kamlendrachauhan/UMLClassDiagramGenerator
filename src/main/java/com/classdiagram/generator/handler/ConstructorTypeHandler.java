package com.classdiagram.generator.handler;

import java.util.ArrayList;
import java.util.List;

import com.classdiagram.generator.common.ClassRelationship;
import com.classdiagram.generator.common.DataType;
import com.classdiagram.generator.model.ConstructorType;
import com.classdiagram.generator.model.TypeContainer;
import com.classdiagram.generator.model.TypeRelationship;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.VoidType;

public class ConstructorTypeHandler {


	public void populateConstructorType(ConstructorDeclaration constructorDeclaration, TypeContainer currentTypeContainer) {
		ConstructorType constructorType = new ConstructorType();
		int modifier = constructorDeclaration.getModifiers();
		constructorType.setConstructorModifier(getModifier(modifier, "constructorModifier"));
		constructorType.setConstructorName(constructorDeclaration.getName());

		List<Node> childrenNodes = constructorDeclaration.getChildrenNodes();

		for (Node childNode : childrenNodes) {

			if (childNode instanceof VoidType || childNode instanceof PrimitiveType) {
				// to capture return type. If return type is custom object it
				// returns Object as ReferenceType
			} else if (childNode instanceof ReferenceType) {
				// if the return type is Reference

			} else if (childNode instanceof Parameter) {
				// Not handling "Type<E> value" types of params for now
				String[] splitParameters = childNode.toString().split(" ");
				if (!DataType.isValidDataType(splitParameters[0])) {
					// Handle Custom classes
					// Add to the TypeRelationship object
					handleCustomTypes(currentTypeContainer, splitParameters[0]);
				}
				ConstructorType.ParameterType parameterType = new ConstructorType.ParameterType();
				parameterType.setParameterType(splitParameters[0]);
				parameterType.setParameterValue(splitParameters[1]);
				constructorType.addToParameterList(parameterType);
			}
		}
		//adding constructors to the final list
		List<ConstructorType> typeConstructors = currentTypeContainer.getTypeConstructors();
		if(typeConstructors == null)
			typeConstructors = new ArrayList<ConstructorType>();
		typeConstructors.add(constructorType);
		currentTypeContainer.setTypeConstructors(typeConstructors);
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
			if (type == "constructorModifier") {
				modifierType = "+";
			}
			break;
		}
		return modifierType;
	}

}
