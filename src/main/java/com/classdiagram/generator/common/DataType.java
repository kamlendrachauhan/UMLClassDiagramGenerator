package com.classdiagram.generator.common;

import java.util.ArrayList;
import java.util.List;

public class DataType {

	public static final String BYTE_PRIMITIVE = "byte";
	public static final String SHORT_PRIMITIVE = "short";
	public static final String INT_PRIMITIVE = "int";
	public static final String LONG_PRIMITIVE = "long";
	public static final String FLOAT_PRIMITIVE = "float";
	public static final String DOUBLE_PRIMITIVE = "double";
	public static final String BOOLEAN_PRIMITIVE = "boolean";
	public static final String CHAR_PRIMITIVE = "char";
	public static final String BYTE_WRAPPER = "Byte";
	public static final String SHORT_WRAPPER = "Short";
	public static final String INTEGER_WRAPPER = "Integer";
	public static final String LONG_WRAPPER = "Long";
	public static final String FLOAT_WRAPPER = "Float";
	public static final String CHARACTER_WRAPPER = "Character";
	public static final String BOOLEAN_WRAPPER = "Boolean";
	public static final String DOUBLE_WRAPPER = "Double";
	public static final String STRING = "String";
	public static final String STRING_ARRAY = "String[]";

	public static final String COLLECTION_CLASS = "Collection";
	public static final String HASHMAP_CLASS = "HashMap";
	public static final String MAP_CLASS = "Map";
	public static final String LIST_CLASS = "List";
	public static final String ARRAYLIST_CLASS = "ArrayList";

	private static List<String> listOfDataTypes = new ArrayList<String>();
	private static List<String> listOfCollections = new ArrayList<String>();

	static {
		listOfDataTypes.add(BYTE_PRIMITIVE);
		listOfDataTypes.add(SHORT_PRIMITIVE);
		listOfDataTypes.add(INT_PRIMITIVE);
		listOfDataTypes.add(LONG_PRIMITIVE);
		listOfDataTypes.add(FLOAT_PRIMITIVE);
		listOfDataTypes.add(DOUBLE_PRIMITIVE);
		listOfDataTypes.add(BOOLEAN_PRIMITIVE);
		listOfDataTypes.add(CHAR_PRIMITIVE);
		listOfDataTypes.add(BYTE_WRAPPER);
		listOfDataTypes.add(SHORT_WRAPPER);
		listOfDataTypes.add(INTEGER_WRAPPER);
		listOfDataTypes.add(LONG_WRAPPER);
		listOfDataTypes.add(FLOAT_WRAPPER);
		listOfDataTypes.add(CHARACTER_WRAPPER);
		listOfDataTypes.add(BOOLEAN_WRAPPER);
		listOfDataTypes.add(DOUBLE_WRAPPER);
		listOfDataTypes.add(STRING);
		listOfDataTypes.add(STRING_ARRAY);

		listOfCollections.add(COLLECTION_CLASS);
		listOfCollections.add(HASHMAP_CLASS);
		listOfCollections.add(MAP_CLASS);
		listOfCollections.add(LIST_CLASS);
		listOfCollections.add(ARRAYLIST_CLASS);
	}

	public static boolean isValidDataType(String inputDataType) {
		return listOfDataTypes.contains(inputDataType);
	}

	public static boolean isValidCollectionType(String inputDataType) {
		if (inputDataType.contains("<"))
			inputDataType = inputDataType.substring(0, inputDataType.indexOf("<"));
		return listOfCollections.contains(inputDataType);
	}
}
