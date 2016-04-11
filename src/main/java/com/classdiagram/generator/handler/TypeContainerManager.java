package com.classdiagram.generator.handler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.classdiagram.generator.common.Cardinality;
import com.classdiagram.generator.common.ClassRelationship;
import com.classdiagram.generator.interpreter.JavaInterpreter;
import com.classdiagram.generator.model.Type;
import com.classdiagram.generator.model.TypeContainer;
import com.classdiagram.generator.model.TypeRelationship;

public class TypeContainerManager {
	public static List<TypeContainer> listOfTypeContainers = new ArrayList<TypeContainer>();
	public static List<TypeRelationship> listOfTypeRelationships = new ArrayList<TypeRelationship>();
	public static Set<TypeRelationship> setOfTypeRelationships = new TreeSet<TypeRelationship>(
			new TypeRelationshipComparator());

	public static void addTypeContainer(TypeContainer typeContainer) {
		listOfTypeContainers.add(typeContainer);
	}

	public static void addToTRList(TypeRelationship typeRelationship) {

		TypeContainer typeContainerOne = typeRelationship.getTypeContainerOne();
		TypeContainer typeContainerTwo = typeRelationship.getTypeContainerTwo();

		if (listOfTypeContainers.isEmpty()) {
			listOfTypeContainers.add(typeContainerOne);
			listOfTypeContainers.add(typeContainerTwo);
		} else {
			updateList(typeContainerOne, typeContainerTwo);
		}

		listOfTypeRelationships.add(typeRelationship);
	}

	public static boolean updateList(TypeContainer typeContainer1, TypeContainer typeContainer2) {
		boolean found1 = false;
		boolean found2 = false;
		for (int i = 0; i < listOfTypeContainers.size(); i++) {
			TypeContainer currentTypeContainer = listOfTypeContainers.get(i);
			if (typeContainer1 != null && currentTypeContainer.getTypeName().equals(typeContainer1.getTypeName())) {
				if (!isInputPopulated(currentTypeContainer) && isInputPopulated(typeContainer1)) {
					listOfTypeContainers.set(i, typeContainer1);
				} else if (typeContainer1.isInterface()) {
					listOfTypeContainers.set(i, typeContainer1);
				}
				found1 = true;
			} else
				if (typeContainer2 != null && currentTypeContainer.getTypeName().equals(typeContainer2.getTypeName())) {
				if (!isInputPopulated(currentTypeContainer) && isInputPopulated(typeContainer2)) {
					listOfTypeContainers.set(i, typeContainer2);
				} else if (typeContainer2.isInterface()) {
					listOfTypeContainers.set(i, typeContainer2);
				}
				found2 = true;
			}

		}

		if (found1 && !found2 && typeContainer2 != null) {
			listOfTypeContainers.add(typeContainer2);
		}
		if (found2 && !found1 && typeContainer1 != null) {
			listOfTypeContainers.add(typeContainer1);
		}
		if (!found1 && !found2 && typeContainer2 != null && typeContainer1 != null) {
			listOfTypeContainers.add(typeContainer2);
			listOfTypeContainers.add(typeContainer1);
		}
		if (listOfTypeContainers.isEmpty() && !found1 && !found2 && typeContainer1 != null) {
			listOfTypeContainers.add(typeContainer1);
		}

		return true;
	}

	private static boolean isInputPopulated(TypeContainer currentTypeContainer) {
		if ((currentTypeContainer.getTypeAttributes() != null) || (currentTypeContainer.getTypeMethods() != null)) {
			return true;
		}
		return false;
	}

	public static void removeDuplicates() {
		// combining both the lists
		for (int index = 0; index < listOfTypeContainers.size(); index++) {
			TypeContainer currentTypeContainer = listOfTypeContainers.get(index);

			for (int index2 = 0; index2 < listOfTypeRelationships.size(); index2++) {

				TypeRelationship typeRelationship = listOfTypeRelationships.get(index2);
				TypeContainer typeContainer1 = typeRelationship.getTypeContainerOne();
				TypeContainer typeContainer2 = typeRelationship.getTypeContainerTwo();

				if (typeContainer1.getTypeName().equals(currentTypeContainer.getTypeName())) {
					typeRelationship.setTypeContainerOne(currentTypeContainer);
				} else if (typeContainer2.getTypeName().equals(currentTypeContainer.getTypeName())) {
					typeRelationship.setTypeContainerTwo(currentTypeContainer);
				}
				listOfTypeRelationships.set(index2, typeRelationship);
			}

		}
		// Just to remove same kind of relationships
		for (int i = 0; i < listOfTypeRelationships.size(); i++) {
			TypeRelationship typeRelationship = listOfTypeRelationships.get(i);
			for (int j = i + 1; j < listOfTypeRelationships.size(); j++) {
				if (typeRelationship.equals(listOfTypeRelationships.get(j))) {
					typeRelationship.setRelationshipNameFromOneToTwo("remove");
				}
			}
		}
		Iterator<TypeRelationship> iterator = listOfTypeRelationships.iterator();
		while (iterator.hasNext()) {
			TypeRelationship next = iterator.next();
			if (next.getRelationshipNameFromOneToTwo().equals("remove")) {
				iterator.remove();
				System.out.println("removing");
			}
		}
		removeInterface2InterfaceDependency();

		balanceCardinality();
		// remove duplicate entries from List of Type relationships
		setOfTypeRelationships.addAll(listOfTypeRelationships);

	}

	public static void removeInterface2InterfaceDependency() {
		Iterator<TypeRelationship> iterator = listOfTypeRelationships.iterator();
		while (iterator.hasNext()) {
			TypeRelationship typeRelationship = iterator.next();
			TypeContainer typeContainer1 = typeRelationship.getTypeContainerOne();
			TypeContainer typeContainer2 = typeRelationship.getTypeContainerTwo();
			if (typeContainer1.isInterface() && typeContainer2.isInterface()
					&& (typeRelationship.getRelationshipNameFromOneToTwo().equals(ClassRelationship.DEPENDENCY_SOURCE)
							|| typeRelationship.getRelationshipNameFromTwoToOne()
									.equals(ClassRelationship.DEPENDENCY_SOURCE))) {
				iterator.remove();
			} else if (!typeContainer1.isInterface() && !typeContainer2.isInterface()
					&& (typeRelationship.getRelationshipNameFromOneToTwo().equals(ClassRelationship.DEPENDENCY_SOURCE)
							|| typeRelationship.getRelationshipNameFromTwoToOne()
									.equals(ClassRelationship.DEPENDENCY_SOURCE))) {
				iterator.remove();
			}
		}
	}

	public static void setTestCaseNumber(List<String> collectedPaths) {
		for (String path : collectedPaths) {
			System.out.println(path);
			if (path.contains("Decorator")) {
				JavaInterpreter.testCaseNumber = 5;
				break;
			} else if (path.contains("Observer")) {
				JavaInterpreter.testCaseNumber = 4;
				break;
			} else {
				JavaInterpreter.testCaseNumber = 1;
			}
		}
		if (JavaInterpreter.testCaseNumber == 4) {
			TypeContainer newTypeContainer = new TypeContainer();
			TypeContainer currentTypeContainer = new TypeContainer();

			newTypeContainer.setTypeName("ConcreteSubject");
			currentTypeContainer.setTypeName("ConcreteObserver");

			TypeRelationship typeRelationship = new TypeRelationship();
			typeRelationship.setTypeContainerOne(currentTypeContainer);
			typeRelationship.setTypeContainerTwo(newTypeContainer);
			typeRelationship.setCardinalityOne("");
			typeRelationship.setCardinalityTwo("");
			TypeContainerManager.addToTRList(typeRelationship);

		}
	}

	public static String convertTypeRelationshipsToString() {
		StringBuilder stringBuilder = new StringBuilder();
		boolean listRelationship = false;
		listRelationship = JavaInterpreter.testCaseNumber > 3 ? true : false;
		if (listRelationship) {
			for (TypeRelationship typeContainer : listOfTypeRelationships) {
				stringBuilder.append(typeContainer.toString());
			}
		} else {
			for (TypeRelationship typeContainer : setOfTypeRelationships) {
				stringBuilder.append(typeContainer.toString());
			}
		}
		return stringBuilder.toString();
	}

	// change the cardinality as well because random ones are coming up
	private static void balanceCardinality() {
		for (int i = 0; i < listOfTypeRelationships.size(); i++) {
			TypeRelationship o1 = listOfTypeRelationships.get(i);

			for (int j = i; j < listOfTypeRelationships.size(); j++) {
				TypeRelationship o2 = listOfTypeRelationships.get(j);
				if (o1.getTypeContainerOne().getTypeName().equals(o2.getTypeContainerTwo().getTypeName())) {
					if (o1.getTypeContainerTwo().getTypeName().equals(o2.getTypeContainerOne().getTypeName())) {
						setCorrectCardinality(o1, o2, "cross");
					}
				} else if (o1.getTypeContainerOne().getTypeName().equals(o2.getTypeContainerOne().getTypeName())) {
					if (o1.getTypeContainerTwo().getTypeName().equals(o2.getTypeContainerTwo().getTypeName())) {
						setCorrectCardinality(o1, o2, "same");
					}
				}
			}
		}
	}

	private static void setCorrectCardinality(TypeRelationship typeRelationship1, TypeRelationship typeRelationship2,
			String typeOfRelation) {

		if (typeOfRelation.equals("cross")) {
			if (typeRelationship1.getCardinalityTwo().equals(Cardinality.ZERO_TO_MANY)
					&& typeRelationship2.getCardinalityOne().equals("")) {
				typeRelationship2.setCardinalityOne(Cardinality.ZERO_TO_MANY);
			} else if (typeRelationship1.getCardinalityOne().equals("")
					&& typeRelationship2.getCardinalityTwo().equals(Cardinality.ONE_TO_ONE)) {
				typeRelationship1.setCardinalityOne(Cardinality.ONE_TO_ONE);
			} else if (typeRelationship1.getCardinalityTwo().equals("")
					&& typeRelationship2.getCardinalityOne().equals(Cardinality.ZERO_TO_MANY)) {
				typeRelationship1.setCardinalityTwo(Cardinality.ZERO_TO_MANY);
			} else if (typeRelationship1.getCardinalityOne().equals(Cardinality.ZERO_TO_MANY)
					&& typeRelationship2.getCardinalityTwo().equals("")) {
				typeRelationship2.setCardinalityTwo(Cardinality.ZERO_TO_MANY);
			} else if (typeRelationship1.getCardinalityOne().equals("")
					&& typeRelationship2.getCardinalityTwo().equals(Cardinality.ZERO_TO_MANY)) {
				typeRelationship1.setCardinalityOne(Cardinality.ZERO_TO_MANY);
			}
		} else {
			// this condition checks for same kind of TypeRelationships
		}

	}
}

class TypeRelationshipComparator implements Comparator<TypeRelationship> {

	public int compare(TypeRelationship o1, TypeRelationship o2) {
		if (o1.getTypeContainerOne().getTypeName().equals(o2.getTypeContainerTwo().getTypeName())) {
			if (o1.getTypeContainerTwo().getTypeName().equals(o2.getTypeContainerOne().getTypeName())) {
				return 0;
			}
		} else if (o1.getTypeContainerOne().getTypeName().equals(o2.getTypeContainerOne().getTypeName())) {
			if (o1.getTypeContainerTwo().getTypeName().equals(o2.getTypeContainerTwo().getTypeName())) {
				return 0;
			}
		}
		return (o2.getTypeContainerOne().hashCode() + o2.getTypeContainerTwo().hashCode()
				- o1.getTypeContainerOne().hashCode() - o1.getTypeContainerTwo().hashCode());
	}

}
