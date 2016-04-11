package com.classdiagram.generator.handler;

import com.classdiagram.generator.model.TypeContainer;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class TypeFactory {

	public void convertTypeContainerToSpecificType(BodyDeclaration bodyDeclaration, TypeContainer typeContainer) {

		if (bodyDeclaration == null)
			return;

		if (bodyDeclaration instanceof FieldDeclaration) {
			FieldTypeHandler fieldTypeHandler = new FieldTypeHandler();
			fieldTypeHandler.populateFieldType((FieldDeclaration) bodyDeclaration, typeContainer);
		} else if (bodyDeclaration instanceof MethodDeclaration) {
			MethodTypeHandler methodTypeHandler = new MethodTypeHandler();
			methodTypeHandler.populateMethodType((MethodDeclaration) bodyDeclaration, typeContainer);
		}else if (bodyDeclaration instanceof ConstructorDeclaration) {
			ConstructorTypeHandler constructorTypeHandler = new ConstructorTypeHandler();
			constructorTypeHandler.populateConstructorType((ConstructorDeclaration) bodyDeclaration, typeContainer);
		}

	}
}
