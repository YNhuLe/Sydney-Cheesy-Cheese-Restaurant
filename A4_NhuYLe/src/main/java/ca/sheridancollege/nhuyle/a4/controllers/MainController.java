package ca.sheridancollege.nhuyle.a4.controllers;

/*Name: NhuY Le
 * Assignment: Sydney's Cheesey Cheese 2.2
Date: December 15, 2022

Description: This application allow clients visit the Sydney's Cheesey Cheese 
website,view cheese and information about us.But only authenticated users with 
 correct name and password can log in and see the adding cheese to the inventory
calculate the value, and display cheese's information to the table.  */
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.nhuyle.a4.beans.Cheese;
import ca.sheridancollege.nhuyle.a4.database.DatabaseAccess;



@Controller
public class MainController {

	@Autowired @Lazy
	private DatabaseAccess da;	
	
	@GetMapping("/register")
	public String loadRegisterForm() {
		return "register.html";
	}
	
     @PostMapping("/register")
    public String processForm(@RequestParam String userName,
    @RequestParam String email, @RequestParam String pass) {    	 
    long userId = da.addUser(userName, email, pass);    
    da.addUserRole(userId, 2);
    return "redirect:/login";
     }
     
	/* Map to the index page */
	@GetMapping("/")
	public String doForm() {
    return "index.html";
	}

	@GetMapping("/login")
	public String login() {
		return "login.html";
	}

	//process the form to add new cheese
	@PostMapping("/login")
	public String loadLogin() {
		return "inventory/index.html";
	}
	
	
	/* Map to the newIndex page in the inventory */
	@GetMapping("/inventory/index")
	public String addCheese(Model model, HttpSession session) {
		model.addAttribute("newCheese", new Cheese());	
		if (session.getAttribute("unitsList") == null) {
			session.setAttribute("unitsList", da.getUnits());
		}
		session.getAttribute("unitMap");
		 return "inventory/index.html"; 
	}

	// handler maps to View cheese inventory page
	@GetMapping("/inventory/view")
	public String viewOurCheese(Model model, HttpSession session) {
	model.addAttribute("newCheese", da.getCheese());
	model.addAttribute("cheeseInv", da.getCheeseInventory());

		if (session.getAttribute("unitsList") == null) {
			session.setAttribute("unitsList", da.getUnits());
		}
	return "inventory/view.html";
	}

	/* Map to Add cheese inventory page */
	@PostMapping("/inventory/index")
	public String addCheese(Model model, @ModelAttribute Cheese newCheese) {
		// add new cheese into the database table
	da.insertCheese(newCheese);	
	model.addAttribute("unitsList",da.getUnits());
	model.addAttribute("unitMap", da.getUnitMap());		
	model.addAttribute("newCheese", da.getCheese());		
	model.addAttribute("cheeseInv", da.getCheeseInventory());
	return "redirect:/inventory/view";
	}

	/* handler that maps to the list of cheese in the VIew our cheese page */
	@GetMapping("/view")
	public String viewCheese(Model model, HttpSession session) {

		if (session.getAttribute("unitsList") == null) {
			session.setAttribute("unitsList", da.getUnits());
		}
	model.addAttribute("newCheese", da.getCheese());
	model.addAttribute("cheeseInv", da.getCheeseInventory());
	return "view.html";
	}
}
