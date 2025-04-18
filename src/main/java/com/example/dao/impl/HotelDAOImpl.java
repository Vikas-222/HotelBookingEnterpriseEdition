package com.example.dao.impl;

import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.dao.IHotelDAO;
import com.example.model.Hotel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;

public class HotelDAOImpl implements IHotelDAO {

    @Override
    public Hotel saveHotel(Hotel hotel) throws DBException {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            LocalDateTime now = LocalDateTime.now();
            hotel.setCreatedAt(now);
            hotel.setUpdatedAt(now);
            em.persist(hotel);
            tx.commit();
            return hotel;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Hotel findHotelById(int hotelId) throws DBException {
        EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Hotel.class, hotelId);
        } catch (Exception e) {
            throw new DBException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public void updateHotel(Hotel hotel) throws DBException {
        EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            hotel.setUpdatedAt(LocalDateTime.now());
            em.merge(hotel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DBException(e);
        } finally {
            em.close();
        }
    }
}
