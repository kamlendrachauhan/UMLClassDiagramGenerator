package com.classdiagram.generator.handler;

import java.util.List;

import com.classdiagram.generator.common.ClassRelationship;
import com.classdiagram.generator.model.TypeContainer;
import com.classdiagram.generator.model.TypeRelationship;

public class InheritenceHandler {

	public void populateListForInheritence(TypeContainer typeContainer) {
		String typeExtends = typeContainer.getTypeExtends();
		if (typeExtends != null) {
			handleInheritence(typeContainer, typeExtends, ClassRelationship.INHERITENCE_EXTENDS_CHILD,
					ClassRelationship.INHERITENCE_EXTENDS_PARENT);
		}
		List<String> typeImplements = typeContainer.getTypeImplements();
		if (typeImplements != null) {
			for (String interfaceType : typeImplements) {
				handleInheritence(typeContainer, interfaceType, ClassRelationship.INHERITENCE_IMPLEMENTS_CHILD,
						ClassRelationship.INHERITENCE_IMPLEMENTS_PARENT);
			}
		}
	}

	private void handleInheritence(TypeContainer currentTypeContainer, String typeInherited, String relationshipOne2Two,
			String relationshipTwo2One) {
		TypeContainer newTypeContainer = new TypeContainer();
		newTypeContainer.setTypeName(typeInherited);

		TypeRelationship typeRelationship = new TypeRelationship();
		typeRelationship.setTypeContainerOne(currentTypeContainer);
		typeRelationship.setTypeContainerTwo(newTypeContainer);
		typeRelationship.setRelationshipNameFromOneToTwo(relationshipOne2Two);
		typeRelationship.setRelationshipNameFromTwoToOne(relationshipTwo2One);
		System.out.println(typeRelationship.toString());
		TypeContainerManager.addToTRList(typeRelationship);
	}
}
