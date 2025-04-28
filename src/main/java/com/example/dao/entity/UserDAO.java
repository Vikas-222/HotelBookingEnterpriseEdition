package com.example.dao.entity;

import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.entitymodal.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserDAO {

    public boolean isUserEmailExists(String emailId) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT u.email FROM User u WHERE u.email = :email";
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
            String jpql = "select u from User u where email = :email and password = :password";
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
        try (EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager()) {
            User user = em.find(User.class,id);
            return user;
        } catch (PersistenceException e) {
            throw new DBException(e);
        }
    }

    public List<User> getAllUser() throws DBException {
        List<User> list;
        try (EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager()) {
            String fetch = "SELECT u FROM User u";
            TypedQuery<User> query = em.createQuery(fetch, User.class);
            list = query.getResultList();
            return list;
        } catch (PersistenceException e) {
            throw new DBException(e);
        }
    }

    public boolean isValidUserId(int Id) throws DBException {
        try (EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager()) {
            User existingUser = em.find(User.class, Id);
            return existingUser != null;
        } catch (jakarta.persistence.NoResultException e) {
            return false;
        } catch (PersistenceException e) {
            throw new DBException(e);
        }
    }

    public void updateUserDetails(User user) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            User existingUser = em.find(User.class, user.getUserId());
            if (existingUser != null) {
                User updatedUser = new User.Builder()
                        .setUserId(existingUser.getUserId())
                        .setLastName(user.getLastName() != null ? user.getLastName() : existingUser.getLastName())
                        .setContactNumber(user.getContactNumber() != null ? user.getContactNumber() : existingUser.getContactNumber())
                        .setGender(user.getGender() != null ? user.getGender() : existingUser.getGender())
                        .setProfilePic(user.getProfilePic() != null ? user.getProfilePic() : existingUser.getProfilePic())
                        .setFirstName(existingUser.getFirstName())
                        .setEmail(existingUser.getEmail())
                        .setPassword(existingUser.getPassword())
                        .setRole(existingUser.getRole())
                        .setIsActive(existingUser.getIsActive())
                        .build();
                em.merge(updatedUser);
                em.getTransaction().commit();
            }
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


    public void updateUserActiveStatus(int userId, boolean status) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("UPDATE User u SET u.isActive = :status WHERE u.userId = :userId");
            query.setParameter("status", status);
            query.setParameter("userId", userId);
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    public boolean isValidCurrentPassword(int userId, String password) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            Query query = em.createQuery("SELECT u.password FROM User u WHERE u.userId = :userId");
            query.setParameter("userId", userId);
            String storedPassword = (String) query.getSingleResult();
            if (!storedPassword.isEmpty()) {
                return storedPassword.equals(password);
            }
            return false;
        } catch (PersistenceException e) {
            throw new DBException(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public boolean updatePassword(int userId, String password) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("UPDATE User u SET u.password = :password WHERE u.userId = :userId");
            query.setParameter("password", password);
            query.setParameter("userId", userId);
            int updatedCount = query.executeUpdate();
            em.getTransaction().commit();
            return updatedCount > 0;
        } catch (PersistenceException e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

}
