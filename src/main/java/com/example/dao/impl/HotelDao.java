package com.example.dao.impl;

import com.example.common.utils.ManagerFactory;
import com.example.model.Hotel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class HotelDao {

    public Hotel saveHotel(Hotel hotel) {
        EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(hotel);
            tx.commit();
            return hotel;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public Hotel findHotelById(int hotelId) {
        EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Hotel.class, hotelId);
        } finally {
            em.close();
        }
    }
}
