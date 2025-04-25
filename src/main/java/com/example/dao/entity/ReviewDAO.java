package com.example.dao.entity;

import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.entitymodal.Review;
import jakarta.persistence.*;

import java.util.List;

public class ReviewDAO {

    public boolean addReview(Review review) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.persist(review);
            em.getTransaction().commit();
            return true;
        } catch (RollbackException e) {
            // Catch this if the transaction commit failed for some reason
            if (em != null && em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } catch (PersistenceException e) {
            // Catch other general JPA persistence errors as a fallback
            if (em != null && em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            if (em != null && em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void updateReview(Review review) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Review existingReview = em.find(Review.class, review.getReviewId());
            if (existingReview != null) {
                existingReview.setFeedback(review.getFeedback());
                existingReview.setRating(review.getRating());
            }
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            if (em != null && em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } catch (RollbackException e) {
            if (em != null && em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } catch (PersistenceException e) {
            if (em != null && em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } catch (Exception e) {
            if (em != null && em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public Review findReview(int id) throws DBException {
        EntityManager em = null;
        Review existingReview = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            existingReview = em.find(Review.class, id);
            em.getTransaction().commit();
            return existingReview;
        } catch (PersistenceException e) {
            if (em != null && em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } catch (Exception e) {
            if (em != null && em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public boolean deleteReview(int reviewID, int userId) throws DBException {
        EntityManager em = null;
        int deletedCount = 0; // To store the number of entities deleted

        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            String jpql = "DELETE FROM Review r WHERE r.reviewId = :reviewId AND r.user.userId = :userId";
            Query query = em.createQuery(jpql);
            query.setParameter("reviewId", reviewID);
            query.setParameter("userId", userId);
            deletedCount = query.executeUpdate();
            em.getTransaction().commit();

            // Log the result
            if (deletedCount > 0) {
               return true;
            } else {
                return false;
            }
        } catch (RollbackException e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);

        } catch (PersistenceException e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);

        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public List<Review> getReviews() throws DBException {
        return null;
    }
//
//    @Override
//    public List<Review> getReviewsByUserId(int userId) throws DBException {
//        return null;
//    }
//

    public boolean isValidReviewId(int id) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Review review = em.find(Review.class, id);
            return review != null;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
