package com.classdiagram.generator.interpreter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilePathReader {

	public List<String> collectPaths(String inputDirectoryPath) {
		List<String> listOfInputFilePaths = new ArrayList<String>();

		File filesLocation = new File(inputDirectoryPath);
		File[] filePaths = filesLocation.listFiles();
		for (File filePath : filePaths) {
			if (filePath.getName().contains(".java"))
				listOfInputFilePaths.add(filePath.getAbsolutePath());
		}

		return listOfInputFilePaths;
	}
}
