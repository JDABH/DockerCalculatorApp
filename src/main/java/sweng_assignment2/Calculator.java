package sweng_assignment2;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
//import java.lang.*;


public class Calculator {

	public static final float eValue= 2.7182881828f;
	
	static ArrayList<String> makeArrayList(String equation) {
	    ArrayList<String> list = new ArrayList<String>();
	    
        String[] splitted = equation.split("");
        ArrayList<String> charList = new ArrayList<String>(Arrays.asList(splitted));
        
        String currentNum = "";
        
        for(int i = 0; i < charList.size(); i++) 
        {
			String currentChar;
			if(charList.get(i).equals("e") && charList.get(i+1).equals("x") && charList.get(i+2).equals("p")){
				currentChar = "exp";
				i+= 2;
			}
			else if(charList.get(i).equals("l") && charList.get(i+1).equals("n")){
				currentChar = "ln";
				i+= 1;
			}
			else{
           	 	currentChar = charList.get(i);
			}
            if(currentChar.matches("[0-9]+")) 
            {
                currentNum = currentNum + currentChar;
            }
			else if(currentChar.equals(".")){
				currentNum += currentChar;
			}
            else if(precedence(currentChar) != -1 || currentChar.equals(")") || currentChar.equals("("))
            {
                if(!currentNum.equals(""))               
                {
                    list.add(currentNum);
                    currentNum = "";
                }
                list.add(currentChar);
            }
            else if(currentChar.equals(" ")) {}
            else 
            {
                System.err.println("You typed in an invalid expression.");
            }
                    
        }
        if(!currentNum.equals(""))               
        {
            list.add(currentNum);
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
		case "/":
		    return 2;
		case "^":
		case "exp":
		case "ln":
		    return 3;
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
		case("/"):
		case("^"):
		case("exp"):
		case("ln"):
			return true;
		}
		return false;
	}

	static float evaluatePostfix(ArrayList<String> postExpression)
	{
		Stack<Float> postFix = new Stack<>();    // Create postfix stack
		int n = postExpression.size();
		

		for(int i = 0; i < n; i++)
		{
			if(isOperator(postExpression.get(i)))
			{
				// pop top 2 operands.
				Float op1 = postFix.pop();
				Float op2;
				if(postExpression.get(i) == "exp" || postExpression.get(i) == "ln"){
					op2 = eValue;
				}
				else{
					op2 = postFix.pop();
				}

				// evaluate in reverse order i.e. op2 operator op1.
				switch(postExpression.get(i))
				{
				case "+": postFix.push(op2 + op1);
				break;

				case "-": postFix.push(op2 - op1);
				break;

				case "*": postFix.push(op2 * op1);
				break;
				
				case "/": postFix.push(op2 / op1);
                break;
                
				case "^": postFix.push((float)Math.pow(op2, op1));
                break;

				case "exp": postFix.push((float)Math.pow(op2, op1)); //2.7182881828
				break;

				case "ln": postFix.push((float)Math.log(op1));
				break;
				}

			}
			// Current Char is Operand simple push into stack
			else
			{
				// convert to integer
				Float operand =  Float.valueOf(postExpression.get(i));
				postFix.push(operand);
			}
		}

		// Stack at End will contain result.
		DecimalFormat df = new DecimalFormat("#.000");

		return Float.valueOf(df.format(postFix.pop()));
	}





	private static boolean errorHandling(ArrayList<String> list) {
		if(list.get(0).equals("+") || list.get(0).equals("-") || list.get(0).equals("*")|| list.get(0).equals("/")
		        || list.get(0).equals("^"))
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
					if(!symbol.matches("[\\+\\-\\*\\/\\(\\)\\^\\exp\\ln]"))
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
			System.out.println(arr.toString());

			//if(errorHandling(arr))
			if(true)
			{
				ArrayList<String> postExpression = infixToPostFix(arr);
				System.out.println(postExpression.toString());
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
