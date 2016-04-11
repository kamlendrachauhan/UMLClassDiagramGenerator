package testcase.input;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import com.classdiagram.generator.interpreter.FilePathReader;
import com.classdiagram.generator.interpreter.JavaInterpreter;
import com.classdiagram.generator.model.TypeContainer;
import com.github.javaparser.ParseException;

public class JavaInterpreterTest {

	@Test
	public void test() {
		FilePathReader filePathReader = new FilePathReader();
		List<String> collectPaths = filePathReader.collectPaths(
				"C:\\Stuff\\SJSU\\2nd Sem\\202\\UML Class Diagram Generator -- Personal Project\\Input Test Cases\\uml-parser-test-1");
		for (String path : collectPaths) {
			System.out.println(path);
		}
		JavaInterpreter javaInterpreter = new JavaInterpreter();
		try {
			TypeContainer interpretTypeAndInheritence = javaInterpreter
					.interpretTypeAndInheritence(collectPaths.get(0));
			javaInterpreter.interpretTypeMembers(interpretTypeAndInheritence);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
