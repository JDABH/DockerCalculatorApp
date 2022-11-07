package sweng_assignment2.com.spring.javawebserver.webserver;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SpringBootApplication
public class WebserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebserverApplication.class, args);
	}

}
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class Note {
    @Id
    private String id;
    private String description;


    @Override
    public String toString() {
        return description+" "+id;
    }
}







@Controller
class NoteController {

		//@Autowired
		private ArrayList<String> notesList = new ArrayList<>();
		private Calculator calculator;


    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }


		@PostMapping("/note")
    public String saveNotes(@RequestParam String description,
                            @RequestParam(required = false) String add,
                            Model model) throws Exception {

        if (add != null && add.equals("Add equation")) {
					ArrayList<String> arr = calculator.makeArrayList(description);

					if(calculator.errorHandling(arr)){
						ArrayList<String> postFix = calculator.infixToPostFix(arr);
						String result = calculator.evaluatePostfix(postFix);
						notesList.add(description + " = " + result);
						//model.addAttribute("string", description + " = " + result);
            //saveNote(description, model);
            //getAllNotes(model);
						//model.addAttribute("strings", description);
						//model.addAttribute("string", description);
            //return "redirect:/";
					}
					arr = new ArrayList<>(notesList);
					Collections.reverse(arr);
					model.addAttribute("strings", arr);
					return "index";
        }
        return "index";
    }


    private void getAllNotes(Model model) {
        List<String> notes = notesList;
        Collections.reverse(notes);
        model.addAttribute("strings", notes);
    }



    private void saveNote(String description, Model model) {
        if (description != null && !description.trim().isEmpty()) {
        	java.util.Date timeStamp=new java.util.Date();
            notesList.add(description.trim());
            model.addAttribute("description", "");
						//model.addAttribute("string", description.trim());
        }
    }


}

class Calculator {

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
						ArrayList<String> emptyList = new ArrayList<String>();
						return emptyList;
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

	static String evaluatePostfix(ArrayList<String> postExpression) throws Exception
	{
		try {
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

			Float result = Float.valueOf(df.format(postFix.pop()));
			return result.toString();
		}
		catch(Exception e) {
			return "invalid";
		}
	}





	static boolean errorHandling(ArrayList<String> list) {
		if(list.size() < 2) {
			return false;
		}
		else if(list.get(0).equals("+") || list.get(0).equals("-") || list.get(0).equals("*")|| list.get(0).equals("/")
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
			boolean result = true;
			for(int i = 0; i < list.size(); i++)
			{

					String symbol = list.get(i);
					if(!symbol.matches("([0-9]+)|exp|ln|\\*|\\+|\\/|\\(|\\)|\\^|\\.|\\-"))
					{
						result = false;
					}
					if(symbol.matches("/^\\d\\.?\\d$/")) {
						result = true;
					}
					else {
						result = true;
					}

				if(!result){
					System.out.println(list.get(i));
					return result;
				}
			}
			return true;
		}
	}


	private static void printErrorMessage() {
		System.out.println("You typed in an invalid expression.");
	}


	public static void main(String[] args) throws Exception {
        //Test comment
		boolean exit = false;
		while(!exit)
		{
			System.out.println("Please enter an equation you want to solve (only addition, subtraction, and multiplication)");
			Scanner input = new Scanner(System.in);
			String equation = input.nextLine();
			ArrayList<String> arr = makeArrayList(equation);
			System.out.println(arr.toString());

			if(errorHandling(arr))
			//if(true)
			//if(arr.size()!=0)
			{
				try {
					ArrayList<String> postExpression = infixToPostFix(arr);
					System.out.println(postExpression.toString());
					String result = evaluatePostfix(postExpression);
					if (result != "invalid"){
						System.out.println("The answer is " + evaluatePostfix(postExpression));
					}
					else {
						printErrorMessage();
					}
			    exit = true;
					input.close();
				}
				catch(Exception e){
					break;
				}
			}
			else
			{
				printErrorMessage();
				exit = true;
				input.close();
			}
		}
	}

}
