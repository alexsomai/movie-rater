package org.ubb.cluj.movierater.web.commandobject;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.ubb.cluj.movierater.business.model.Account;

public class SignupForm {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
    private static final String LENGTH_NOT_VALID = "{length.message}";

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
    @Length(min = 6, max = 255, message = SignupForm.LENGTH_NOT_VALID)
    private String username;

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
    @Length(min = 6, max = 255, message = SignupForm.LENGTH_NOT_VALID)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account createAccount() {
        return new Account(getUsername(), getPassword(), "ROLE_USER");
    }

}
