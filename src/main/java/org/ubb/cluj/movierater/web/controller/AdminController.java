package org.ubb.cluj.movierater.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alex Somai on 09.12.2014
 */

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

    @ModelAttribute("page")
    public String module() {
        return "admin";
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String admin() {
        return "admin/admin_page";
    }
}
