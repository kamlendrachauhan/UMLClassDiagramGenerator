/**
 * 
 */
package com.classdiagram.generator.handler;

import java.util.ArrayList;
import java.util.List;

import com.classdiagram.generator.common.Cardinality;
import com.classdiagram.generator.common.DataType;
import com.classdiagram.generator.model.FieldType;
import com.classdiagram.generator.model.TypeContainer;
import com.classdiagram.generator.model.TypeRelationship;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;

/**
 * @author Kamlendra Singh Chauhan
 *
 */
public class FieldTypeHandler {

	void populateFieldType(FieldDeclaration fieldDeclaration, TypeContainer currentTypeContainer) {

		FieldType fieldType = new FieldType();
		int modifiers = fieldDeclaration.getModifiers();
		String modifier = getModifier(modifiers, "");
		if ("#".equals(modifier) || "".equals(modifier)) {
			// Not handling variable protected and package scope
			return;
		}
		//Do not include Dependency and uses relationship in case of class-class and interface-interface
		fieldType.setVariableModifier(modifier);
		List<Node> childrenNodes = fieldDeclaration.getChildrenNodes();

		for (Node childNode : childrenNodes) {
			if (childNode instanceof ReferenceType) {
				ReferenceType referenceType = (ReferenceType) childNode;
				Type type = referenceType.getType();
				if (DataType.isValidDataType(type.toString())) {
					fieldType.setVariableDataType(type.toString());
				} else if (DataType.isValidCollectionType(type.toString())) {
					String referenceTypeString = type.toString();
					if (referenceTypeString.contains("<") && referenceTypeString.contains(">")) {

						// figuring out the generics type
						referenceTypeString = referenceTypeString.substring(referenceTypeString.indexOf("<") + 1,
								referenceTypeString.indexOf(">"));

						// checking till one level of generics
						if (DataType.isValidDataType(referenceTypeString)) {
							fieldType.setVariableDataType(referenceTypeString);
							fieldType.setCardinality("(*)");
						} else {
							fieldType = null;
							handleCustomTypes(referenceTypeString, currentTypeContainer, Cardinality.ONE_TO_ONE,
									Cardinality.ZERO_TO_MANY);
							break;
							// don't let custom class save as
							// FieldType Field, it should be part of
							// the TypeRelationship list.
						}
					}
				} else {
					fieldType = null;
					handleCustomTypes(type.toString(), currentTypeContainer, "", Cardinality.ONE_TO_ONE);
					break;
				}
			} else if (childNode instanceof PrimitiveType) {
				PrimitiveType primitiveType = (PrimitiveType) childNode;
				fieldType.setVariableDataType(primitiveType.toString());
			} else if (childNode instanceof VariableDeclarator) {
				VariableDeclarator variableDeclarator = (VariableDeclarator) childNode;
				fieldType.setVariableName(variableDeclarator.toString());
			}
			if (childNode.toString().contains("[]")) {
				fieldType.setCardinality("(*)");
			}
		}

		if (fieldType != null) {
			List<FieldType> typeAttributes = currentTypeContainer.getTypeAttributes();
			if (typeAttributes == null)
				typeAttributes = new ArrayList<FieldType>();
			typeAttributes.add(fieldType);
			currentTypeContainer.setTypeAttributes(typeAttributes);
		}
	}

	private void handleCustomTypes(String referenceTypeString, TypeContainer currentTypeContainer,
			String cardinalityOne, String cardinalityTwo) {

		TypeContainer newTypeContainer = new TypeContainer();
		newTypeContainer.setTypeName(referenceTypeString);

		TypeRelationship typeRelationship = new TypeRelationship();
		typeRelationship.setTypeContainerOne(currentTypeContainer);
		typeRelationship.setTypeContainerTwo(newTypeContainer);
		typeRelationship.setCardinalityOne(cardinalityOne);
		typeRelationship.setCardinalityTwo(cardinalityTwo);
		System.out.println(typeRelationship.toString());
		TypeContainerManager.addToTRList(typeRelationship);
		// check if the relationship or they typecontainer already exists.
		// Replace it.
	}

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
