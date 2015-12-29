package org.ubb.cluj.movierater.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.model.Category;

/**
 * Created by somai on 22.12.2014.
 */
@Repository
@Transactional(readOnly = true)
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Transactional
//    public Category save(Category category) {
//        entityManager.persist(category);
//        return category;
//    }
//
//    @Transactional
//    public List<Category> getAllCategories() {
//        return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
//    }
//
//    @Transactional
//    public Set<Category> getCategoriesById(Long... categoryId) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
//        Root<Category> categoryRoot = criteriaQuery.from(Category.class);
//
//        criteriaQuery.select(categoryRoot);
//
//        criteriaQuery.where(categoryRoot.get("id").in((Object[]) categoryId));
//        TypedQuery<Category> query = entityManager.createQuery(criteriaQuery);
//        return new HashSet<>(query.getResultList());
//    }

}
