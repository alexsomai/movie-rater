package org.ubb.cluj.movierater.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ubb.cluj.movierater.business.model.Account;
import org.ubb.cluj.movierater.business.repository.AccountRepository;

import javax.annotation.PostConstruct;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    public void initialize() {
        if (accountRepository.findByUsername("admin") == null) {
            accountRepository.save(new Account("user", "demo", "ROLE_USER"));
            accountRepository.save(new Account("admin", "admin", "ROLE_ADMIN"));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createUser(account);
    }

    public void signin(Account account) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(account));
    }

    private Authentication authenticate(Account account) {
        return new UsernamePasswordAuthenticationToken(createUser(account), null, account.getAuthorities());
    }

    private User createUser(Account account) {
        return new User(account);
    }

    private static class User extends org.springframework.security.core.userdetails.User {

        private final Account account;

        public User(Account account) {
            super(account.getUsername(), account.getPassword(), account.getAuthorities());
            this.account = account;
        }

        public Account getAccount() {
            return account;
        }

        public boolean isAdmin() {
            return getAccount().isAdmin();
        }
    }

}
