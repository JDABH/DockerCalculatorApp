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
        }
    }


}
