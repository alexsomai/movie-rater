package org.ubb.cluj.movierater.business.repository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.model.Account;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Repository
@Transactional(readOnly = true)
public class AccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        entityManager.persist(account);
        return account;
    }

    public Account findByUsername(String username) {
        try {
            return entityManager.createNamedQuery(Account.FIND_BY_USERNAME, Account.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }


}
