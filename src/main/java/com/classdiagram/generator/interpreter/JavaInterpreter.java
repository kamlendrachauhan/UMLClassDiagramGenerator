package com.classdiagram.generator.interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.classdiagram.generator.handler.InheritenceHandler;
import com.classdiagram.generator.handler.TypeFactory;
import com.classdiagram.generator.model.TypeContainer;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class JavaInterpreter {

	private CompilationUnit compilationUnit;

	public static int testCaseNumber = 0;

	public TypeContainer interpretTypeAndInheritence(String filePath) throws ParseException, FileNotFoundException {

		FileInputStream fileInputStream = new FileInputStream(new File(filePath));
		compilationUnit = JavaParser.parse(fileInputStream);

		List<Node> childrenNodes = compilationUnit.getChildrenNodes();

		TypeContainer typeContainer = new TypeContainer();

		for (Node node : childrenNodes) {
			String typeName = null;
			ClassOrInterfaceDeclaration classOrInterfaceDeclaration = null;

			if (node instanceof ClassOrInterfaceDeclaration) {
				classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) node;
				typeName = classOrInterfaceDeclaration.getName();
				typeContainer.setTypeName(typeName);
				if (isInterface(classOrInterfaceDeclaration))
					typeContainer.setInterface(Boolean.TRUE);
			}

			if (classOrInterfaceDeclaration != null && !isInterface(classOrInterfaceDeclaration)) {

				if (classOrInterfaceDeclaration.getExtends() != null) {
					typeContainer.setTypeExtends(classOrInterfaceDeclaration.getExtends().toString()
							.replaceAll("\\[", "").replaceAll("\\]", ""));
				}

				if (classOrInterfaceDeclaration.getImplements() != null) {
					List<ClassOrInterfaceType> implementsInterfaces = classOrInterfaceDeclaration.getImplements();
					List<String> listOfImplementsInterfaces = new ArrayList<String>();

					for (ClassOrInterfaceType classOrInterfaceType : implementsInterfaces) {
						listOfImplementsInterfaces.add(classOrInterfaceType.toString());
					}

					typeContainer.setTypeImplements(listOfImplementsInterfaces);
				}
			}
		}
		// Populating Inheritence List
		InheritenceHandler inheritenceHandler = new InheritenceHandler();
		inheritenceHandler.populateListForInheritence(typeContainer);
		
		return typeContainer;
	}

	public TypeContainer interpretTypeMembers(TypeContainer typeContainer) {
		if (compilationUnit != null) {
			List<TypeDeclaration> types = compilationUnit.getTypes();

			for (TypeDeclaration typeDeclaration : types) {

				List<BodyDeclaration> members = typeDeclaration.getMembers();
				TypeFactory typeFactory = new TypeFactory();
				for (BodyDeclaration member : members) {
					typeFactory.convertTypeContainerToSpecificType(member, typeContainer);
				}

			}

		}
		return typeContainer;
	}

	private boolean isInterface(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
		if (classOrInterfaceDeclaration.toString().contains("interface"))
			return true;
		return false;
	}

}
