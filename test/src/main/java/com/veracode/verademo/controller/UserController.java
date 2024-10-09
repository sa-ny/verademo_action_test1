package com.veracode.verademo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author johnadmin
 */
@Controller
@Scope("request")
public class UserController {
	private static final Logger logger = LogManager.getLogger("VeraDemo:UserController");

	/**
	 * @param target
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogin(
			@RequestParam(value = "target", required = false) String target,
			@RequestParam(value = "username", required = false) String username,
			Model model,
			HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		// Check if user is already logged in


		if (username == null) {
			username = "";
		}

		if (target == null) {
			target = "";
		}
		//..

		logger.info("Entering showLogin with username " + username + " and target " + target);

		model.addAttribute("username", username);
		model.addAttribute("target", target);
		return "login";
	}
}
