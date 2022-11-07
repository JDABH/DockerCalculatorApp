package sweng_assignment2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class CalculatorTest {

	@Test
	public void testInfixToPostFix() throws Exception{
		String equation = "14*3-7*35+22-56+100";
		ArrayList<String> list = Calculator.makeArrayList(equation);
		String[] expectedResult = {"14", "3", "*", "7", "35", "*", "-", "22", "+", "56", "-", "100", "+"};
		assertEquals(Arrays.toString(expectedResult), Calculator.infixToPostFix(list).toString());
	}

	@Test
	public void testEvaluatePostfix() throws Exception {
		String equation = "14*3-7*35+22-56+100";
		ArrayList<String> list = Calculator.makeArrayList(equation);
		int expectedResult = -137;
		assertEquals(expectedResult, Calculator.evaluatePostfix(Calculator.infixToPostFix(list)));
	}


}
