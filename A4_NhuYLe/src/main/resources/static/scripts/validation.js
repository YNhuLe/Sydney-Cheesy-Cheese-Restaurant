"use strict";

/* Wendi Jollymore
   syst10199, Feb 2016 
   modified for prog32758, Aug 2020
   updated to latest ES5 July 2021
   some validation for a registration form

Feel free to modify.
*/

// unobtrusive javascript!!!
document.addEventListener("DOMContentLoaded", init);
function init() {
	document.querySelector("form input[type=submit]").addEventListener("click", validator);
}

// not very reusable validation, but done quickly

// TODO: check password strength!!

let validator = {
	handleEvent(event) {
		let form = event.target.form;
		
		// in case browser doesn't support html5 constraint validation
		// (can test by removing required attribute)
		//checkEmpty(form.email, "Email", div);
		//checkEmpty(form.pass, "Password", div);
		//checkEmpty(form.verify, "Verify Password", div);
	
		// password must match verify password field
		if (form.pass.value !== form.verify.value) {
			form.verify.setCustomValidity("Passwords don't match.");
			form.verify.reportValidity();  // report to browser
		} else {
			// cancel/undo verify validity
			form.verify.setCustomValidity("");
			
			// in case browser doesn't support pattern="" attribute
			let emailPatt = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if (!emailPatt.test(form.email.value.toLowerCase())) {
				form.email.setCustomValidity("Invalid email format.");
				form.email.reportValidity();  // report to browser
			} else {
				// cancel/undo email validity
				form.email.setCustomValidity("");	
			}			
		}
		
		// don't submit if there are any errors
		if (!form.checkValidity()) {
			event.preventDefault();
		}
	}
	
};

// determines if an input field is empty and reports error if it is
function checkEmpty(field, fieldName) {
	if (field.value == null || field.value == "") {
		field.setCustomValidity(fieldName + " can't be empty.");
		field.reportValidity();
	} else {
		field.setCustomValidity("");
	}
}