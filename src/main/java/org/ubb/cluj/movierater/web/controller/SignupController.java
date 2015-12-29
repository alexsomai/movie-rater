package org.ubb.cluj.movierater.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ubb.cluj.movierater.business.model.Account;
import org.ubb.cluj.movierater.business.repository.AccountRepository;
import org.ubb.cluj.movierater.business.service.UserService;
import org.ubb.cluj.movierater.web.commandobject.SignupForm;
import org.ubb.cluj.movierater.web.support.AjaxUtils;
import org.ubb.cluj.movierater.web.support.MessageHelper;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

@Controller
public class SignupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupController.class);

    private static final String SIGNUP_VIEW_NAME = "signup/signup";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "signup")
    public String signup(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        model.addAttribute(new SignupForm());
        if (AjaxUtils.isAjaxRequest(requestedWith)) {
            return SIGNUP_VIEW_NAME.concat(" :: signupForm");
        }
        return SIGNUP_VIEW_NAME;
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return SIGNUP_VIEW_NAME;
        }

        Account account;
        try {
            account = accountRepository.save(signupForm.createAccount());
        } catch (PersistenceException e) {
            if (e.getCause().getCause().getLocalizedMessage().matches(MessageHelper.DUPLICATE_ENTRY_MESSAGE)) {
                LOGGER.warn("Trying to create account with the same username", e);
                errors.rejectValue("username", "message.error.username.unique",
                        "There is already another account with the same username");
                return SIGNUP_VIEW_NAME;
            }
            LOGGER.error("An unexpected error occurred while creating account", e);
            errors.rejectValue("username", "message.error.unexpected",
                    "An unexpected error occurred!");
            return SIGNUP_VIEW_NAME;
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred while creating account", e);
            errors.rejectValue("username", "message.error.unexpected",
                    "An unexpected error occurred!");
            return SIGNUP_VIEW_NAME;
        }

        userService.signin(account);
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
        MessageHelper.addSuccessAttribute(ra, "signup.success");
        return "redirect:/";
    }
}
