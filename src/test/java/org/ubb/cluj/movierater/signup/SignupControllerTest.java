package org.ubb.cluj.movierater.signup;

import org.junit.Ignore;
import org.junit.Test;
import org.ubb.cluj.movierater.business.config.WebAppConfigurationAware;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SignupControllerTest extends WebAppConfigurationAware {

    @Test
    @Ignore
    public void displaysSignupForm() throws Exception {
        mockMvc.perform(get("/signup").header("X-Requested-With", ""))
                .andExpect(model().attributeExists("signupForm"))
                .andExpect(view().name("signup/signup"))
                .andExpect(content().string(
                                allOf(
                                        containsString("<title>Signup</title>"),
                                        containsString("<legend>Please Sign Up</legend>")
                                ))
                );
    }
}