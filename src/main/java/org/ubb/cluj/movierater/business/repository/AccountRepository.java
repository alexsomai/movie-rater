package org.ubb.cluj.movierater.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ubb.cluj.movierater.business.model.Account;

@Repository
//@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    //
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Inject
//    private PasswordEncoder passwordEncoder;
//
//    @Transactional
//    public Account save(Account account) {
//        account.setPassword(passwordEncoder.encode(account.getPassword()));
//        entityManager.persist(account);
//        return account;
//    }
//
    Account findByUsername(String username);
//        try {
//            return entityManager.createNamedQuery(Account.FIND_BY_USERNAME, Account.class)
//                    .setParameter("username", username)
//                    .getSingleResult();
//        } catch (PersistenceException e) {
//            return null;
//        }
//    }
}
