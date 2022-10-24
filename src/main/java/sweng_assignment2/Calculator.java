package sweng_assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;


public class Calculator {
	
	static ArrayList<String> makeArrayList(String equation) {
		ArrayList<String> list = new ArrayList<String>();
		String tmpStr = equation;
		boolean exit = false;

		while(!exit)
		{
			int[] arr = new int[3];
			int add = tmpStr.indexOf('+');
			arr[0] = add;
			int sub = tmpStr.indexOf('-');
			arr[1] = sub;
			int mul = tmpStr.indexOf('*');
			arr[2] = mul;
			Arrays.sort(arr);
			int pos = arr[0];

			if(pos == -1)
			{
				pos = arr[1];
				if(pos == -1)
				{
					pos = arr[2];
				}
			}

			if(pos == 0)
			{
				String str = tmpStr.substring(0, 1);
				list.add(str);
				tmpStr = tmpStr.substring(1);
			}
			else if(pos == -1)
			{
				exit = true;
				list.add(tmpStr);
			}
			else
			{
				String str = tmpStr.substring(0, pos);
				list.add(str);
				tmpStr = tmpStr.substring(pos);
			}
		}

		return list;
	}

	static int precedence(String s){
		switch (s)
		{
		case "+":
		case "-":
			return 1;
		case "*":
			return 2;
		}
		return -1;
	}

	static ArrayList<String> infixToPostFix(ArrayList<String> list){

		ArrayList<String> result = new ArrayList<String>();
		Stack<String> stack = new Stack<>();
		for (int i = 0; i < list.size() ; i++) 
		{
			String s = list.get(i);

			//check if char is operator
			if(precedence(s)>0)
			{
				while(stack.isEmpty() == false && precedence(stack.peek()) >= precedence(s))
				{
					result.add(stack.pop()) ;
				}
				stack.push(s);
			}else if(s.equals(")"))
			{
				String x = stack.pop();
				while(!x.equals("("))
				{
					result.add(x);
					x = stack.pop();
				}
			}
			else if(s.equals("("))
			{
				stack.push(s);
			}
			else
			{
				//character is neither operator nor ( 
				result.add(s);
			}
		}
		for (int i = 0; i <= stack.size() ; i++) 
		{
			result.add(stack.pop());
		}
		return result;

	}


	static boolean isOperator(String s)
	{
		switch (s){
		case("+"):
		case("-"):
		case("*"):
			return true;
		}
		return false;
	}

	static int evaluatePostfix(ArrayList<String> postExpression)
	{
		Stack<Integer> postFix = new Stack<>();    // Create postfix stack
		int n = postExpression.size();

		for(int i = 0; i < n; i++)
		{
			if(isOperator(postExpression.get(i)))
			{
				// pop top 2 operands.
				int op1 = postFix.pop();
				int op2 = postFix.pop();

				// evaluate in reverse order i.e. op2 operator op1.
				switch(postExpression.get(i))
				{
				case "+": postFix.push(op2 + op1);
				break;

				case "-": postFix.push(op2 - op1);
				break;

				case "*": postFix.push(op2 * op1);
				break;
				}

			}
			// Current Char is Operand simple push into stack
			else
			{
				// convert to integer
				int operand =  Integer.valueOf(postExpression.get(i));
				postFix.push(operand);
			}
		}

		// Stack at End will contain result.
		return postFix.pop();
	}


	private static boolean errorHandling(ArrayList<String> list) {
		if(list.get(0).equals("+") || list.get(0).equals("-") || list.get(0).equals("*"))
		{
			return false;
		}
		else if(list.get(list.size() - 1).equals(""))
		{
			return false;
		}
		else
		{
			for(int i = 0; i < list.size(); i++)
			{
				if(i % 2 == 0)
				{
					String num = list.get(i);
					if(!num.matches("[0-9]+"))
					{
						return false;
					}
				}
				else
				{
					String symbol = list.get(i);
					if(!symbol.matches("[\\+\\-\\*]"))
					{
						return false;
					}
				}
			}
			return true;
		}
	}


	private static void printErrorMessage() {
		System.out.println("You typed in an invalid expression.");
	}


	public static void main(String[] args) {
        //Test comment
		boolean exit = false;
		while(!exit)
		{
			System.out.println("Please enter an equation you want to solve (only addition, subtraction, and multiplication)");
			Scanner input = new Scanner(System.in);
			String equation = input.nextLine();
			ArrayList<String> arr = makeArrayList(equation);

			if(errorHandling(arr))
			{
				ArrayList<String> postExpression = infixToPostFix(arr);
				System.out.println("The answer is " + evaluatePostfix(postExpression));
				exit = true;
				input.close();
			}
			else
			{
				printErrorMessage();
			}
		}


	}

}
