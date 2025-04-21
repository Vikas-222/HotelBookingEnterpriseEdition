package com.example.dao.impl;

import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.entitymodal.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean isUserEmailExists(String emailId) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT u.email FROM user u WHERE u.email = :email";
            TypedQuery<String> query = em.createQuery(jpql, String.class);
            query.setParameter("email", emailId);
            String result = query.getSingleResult();
            return result != null;    //return true, if email exists
        } catch (jakarta.persistence.NoResultException e) {
            return false;   //return false, if email does not exists
        } catch (PersistenceException e) {
            throw new DBException(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public void addUser(User user) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public User loggedInUser(String email, String password) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            String jpql = "select u from user u where email = :email and password = :password";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;   //return null, if user does not exists
        } catch (PersistenceException e) {
            throw new DBException(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public User fetchUserDetailsById(int id) throws DBException {
        User user = null;
        try (EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager()) {
            String jpql = "select u from user u where userId = :userId";
            TypedQuery<User> query = em.createQuery(jpql, User.class).setParameter("userId", id);
            user = query.getSingleResult();
            return user;
        }catch (PersistenceException e) {
            throw new DBException(e);
        }
    }

    public List<User> getAllUser() throws DBException {
        List<User> list = new ArrayList<>();
        try (EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager()) {
            String fetch = "SELECT u FROM user u";
            TypedQuery<User> query = em.createQuery(fetch, User.class);
            list = query.getResultList();
            return list;
        } catch (PersistenceException e) {
            throw new DBException(e);
        }
    }

}
