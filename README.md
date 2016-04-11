# UML Class Diagram Generator

A tool to generate UML Class diagram given a set of classes. 

## Compile and run in eclipse:

In Eclipse, Run -> Run Configurations -> Java Application -> New Launch Configuration ->

 Select the project as ‘UMLClassDiagramGenerator’,
 Main class as “com.classdiagram.generatore.main.Application”

 Name for the configuration. Example - UMLParser

 Under arguments - Give program arguments:

o First argument is the complete path of the folder containing the .java source files to be parsed.

o Second argument is the name of to be generated UML class diagram.

-> Apply -> Run.

Running the jar file on command line: 
The project folder also contains runnable jar name under CMPE202_UML_Class_Diagram_Generator/
Executable jar/umlclassdiagramgenerator.jar

The parser is executable on the command line with the following format:
 umlclassdiagramgenerator.jar <path to java files folder> <output file name>.png
Ex: umlclassdiagramgenerator.jar “C:\Users\Kamlendra\Desktop\uml-parser-test-1\” classdiagram1.png

Pre-requisites:
 Java 1.6(and above) JRE and JDK.

Libraries used:
 Javaparser
 YUML

Note: The project is a maven project, so to fetch the required dependency just go to the project
folder and run “mvn clean install”. This will fetch the dependency.
