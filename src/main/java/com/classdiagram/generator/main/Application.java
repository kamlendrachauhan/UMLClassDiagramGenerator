package com.classdiagram.generator.main;

import java.io.FileNotFoundException;
import java.util.List;

import com.classdiagram.generator.handler.ImageHandler;
import com.classdiagram.generator.handler.TypeContainerManager;
import com.classdiagram.generator.interpreter.FilePathReader;
import com.classdiagram.generator.interpreter.JavaInterpreter;
import com.classdiagram.generator.model.TypeContainer;
import com.github.javaparser.ParseException;

public class Application {

	public static void main(String[] args) {
		FilePathReader filePathReader = new FilePathReader();
		if (args.length < 2) {
			System.out.println("Usage: umlparser <classpath> <output file name>");
			System.exit(1);
		}

		String inputFileLocation = args[0];
		String outPutFileName = args[1];

		List<String> collectPaths = filePathReader.collectPaths(inputFileLocation);
		JavaInterpreter javaInterpreter = new JavaInterpreter();

		TypeContainerManager.setTestCaseNumber(collectPaths);
		try {
			for (String path : collectPaths) {
				TypeContainer typeContainer = javaInterpreter.interpretTypeAndInheritence(path);
				javaInterpreter.interpretTypeMembers(typeContainer);
				TypeContainerManager.updateList(typeContainer, null);

				if (typeContainer.getTypeAttributes() == null || typeContainer.getTypeMethods() == null) {

					// Couldn't update the listOfTypeContainers above add here.
					TypeContainerManager.addTypeContainer(typeContainer);
				}
				System.out.println(typeContainer.toString());
				typeContainer = new TypeContainer();
			}
			TypeContainerManager.removeDuplicates();
			String yumlInputRules = TypeContainerManager.convertTypeRelationshipsToString();
			System.out.println(yumlInputRules);
			ImageHandler.generateClassDiagram(yumlInputRules, outPutFileName);
			Thread.sleep(10000);
			System.exit(0);
		} catch (FileNotFoundException e) {
			System.out.println("Exception Has Occured " + e.getMessage());
		} catch (ParseException e) {
			System.out.println("Exception Has Occured " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception Has Occured " + e.getMessage());
		}
	}

}
