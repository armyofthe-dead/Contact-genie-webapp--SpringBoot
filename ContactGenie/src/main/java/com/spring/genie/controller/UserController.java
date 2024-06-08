package com.spring.genie.controller;

import java.io.File;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.genie.Dao.ContactsRepository;
import com.spring.genie.Dao.UserRepository;
import com.spring.genie.entities.ContactDetail;
import com.spring.genie.entities.Message;
import com.spring.genie.entities.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
//@MultipartConfig
public class UserController {

	@Autowired
	private UserRepository uRepo;

	@Autowired
	private ContactsRepository cRepo;

	@ModelAttribute
	public void handleCommonRequest(Model m, Principal p) {

		System.out.println("intellij idea");

		String username = p.getName();
		System.out.println("USER " + username);
		User u = uRepo.getUserByUserName(username);
		System.out.println(u);
		m.addAttribute("user", u);

	}

	@RequestMapping("/index")
	public String userPage(Model m, Principal p) {

		return "normal/user_dashboard";
	}

	@GetMapping("/add-contact")
	public String getAddContactForm(Model m) {
		m.addAttribute("title", "add contact - Contact Genie");
		m.addAttribute("contact", new ContactDetail());
		return "normal/add_contact_form";
	}

	@PostMapping("/handleContact")
	public String handleContactDetails(@ModelAttribute ContactDetail c, Principal p,
			@RequestParam("profileImage") MultipartFile profileImage, HttpSession session) throws IOException {
		try {
			String username = p.getName();
			User u = uRepo.getUserByUserName(username);
			c.setUser(u);
			u.getContact().add(c);

			if (profileImage.isEmpty()) {
				
				c.setImage("default.png");

				System.out.println("Profile Image is Empty !!");

			} else {

				c.setImage(profileImage.getOriginalFilename());
				File saveProfile = new ClassPathResource("static/img/").getFile();

				Path path = Paths
						.get(saveProfile.getAbsolutePath() + File.separator + profileImage.getOriginalFilename());

				Files.copy(profileImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image Uploaded Successfully");
			}
			this.uRepo.save(u);
			System.out.println("Contact Data" + c);
			System.out.println("data saved to database");
			session.setAttribute("message", new Message("Contact Added Successfully !! Add More...", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something Went Wrong !!", "danger"));

		}

		return "normal/add_contact_form";

	}

	@GetMapping("/showContacts/{page}")
	public String showContacts(@PathVariable("page") Integer page,Model m, Principal p) {
		System.out.println("show contact controller executed");
		String userName = p.getName();
		User u = uRepo.getUserByUserName(userName);
		Pageable pageable=PageRequest.of(page, 5);
		Page<ContactDetail> contacts = cRepo.findContactsByUser(u.getId(),pageable);

		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage",page);
		m.addAttribute("totalPages",contacts.getTotalPages());
		m.addAttribute("title", "Show-Contacts-Page");

		return "normal/show_contacts";
	}
	
	
	@GetMapping("/contactDetail/{id}")
	public String showContactDetails(@PathVariable("id") int id, Model m) {
		
		Optional<ContactDetail> contactDetail=cRepo.findById(id);
		ContactDetail c=contactDetail.get();
		
		m.addAttribute("contactDetail", c);
		
		
		
		
		return "normal/show_contact_detail";
		
		
	}
}
