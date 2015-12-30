package org.ubb.cluj.movierater.business.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
//@NamedQuery(name = Account.FIND_BY_USERNAME, query = "SELECT a FROM Account a WHERE a.username = :username")
public class Account implements Serializable {

//    public static final String FIND_BY_USERNAME = "Account.findByUsername";

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    private String role = ROLE_USER;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.account")
    private Set<MovieAccount> movieAccounts = new HashSet<>(0);

    protected Account() {

    }

    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Set<MovieAccount> getMovieAccounts() {
        return movieAccounts;
    }

    public void setMovieAccounts(Set<MovieAccount> movieAccounts) {
        this.movieAccounts = movieAccounts;
    }

    public Long getId() {
        return id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(getRole()));

    }

    public boolean isAdmin() {
        return ROLE_ADMIN.equals(getRole());
    }

}
