package org.ubb.cluj.movierater.web.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.ubb.cluj.movierater.business.config.WebAppConfigurationAware;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Ignore
public class SignupControllerTest extends WebAppConfigurationAware {

    @Test
    public void displaysSignupForm() throws Exception {
        mockMvc.perform(get("/signup").header("X-Requested-With", "XMLHttpRequest"))
                .andExpect(model().attributeExists("signupForm"))
                .andExpect(view().name("signup/signup :: signupForm"))
                .andExpect(content().string(containsString("<legend>Please Sign Up</legend>")));
    }
}