package com.example.dao.entity;

import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.dto.RoomDTO;
import com.example.entitymodal.Booking;
import jakarta.persistence.*;

import java.sql.*;
import java.util.List;

public class BookingDAO {

    public Booking addBooking(Booking booking, RoomDTO room) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.persist(booking);
            return booking;
        } catch (RuntimeException e) {
            throw new DBException(e);
        } finally {
            assert em != null;
            if (em.getTransaction().isActive() && em.getTransaction() != null) {
                em.getTransaction().rollback();
                em.close();
            }
        }
    }

    public Booking getBookingDetails(int id) throws DBException {
       EntityManager em = null;
       Booking booking = null;
            try {
              em = ManagerFactory.getEntityManagerFactory().createEntityManager();
                booking = em.find(Booking.class, id);
                return booking;
            } catch (RuntimeException e) {
                throw new DBException(e);
            }finally {
                assert em != null;
                if (em.getTransaction().isActive() && em.getTransaction() != null) {
                    em.getTransaction().rollback();
                    em.close();
                }
            }
    }

    public void updateBookingStatus(int id, String status) throws DBException {

    }

    public List<Booking> getAllBookingDetails() throws DBException {
        return null;
    }

    public boolean isValidBookingId(int bookingId) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Booking booking = em.find(Booking.class, bookingId);
            return booking != null;
        } catch (Exception e) {
            throw new DBException(e);
        } finally {
            assert em != null;
            if (em.getTransaction().isActive() && em.getTransaction() != null) {
                em.getTransaction().rollback();
                em.close();
            }
        }
    }

    public boolean cancelBooking(int bookingId, Date cancellationDate, float refundAmount) throws DBException {
        return false;
    }

    public boolean modifyBooking(Booking booking, RoomDTO room) throws DBException {
        return false;
    }

    public boolean isValidUserIdAndBookingId(int userId, int bookingId) throws DBException {
        String jpql = "SELECT COUNT(b) FROM Booking b WHERE b.bookingId = :bookingId AND b.user.userId = :userId";
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("bookingId", bookingId);
            query.setParameter("userId", userId);
            Long count = query.getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            throw new DBException(e);
        }finally {
            assert em != null;
            if (em.getTransaction().isActive() && em.getTransaction() != null) {
                em.getTransaction().rollback();
                em.close();
            }
        }
    }

    public List<Booking> getBookingDetailsByUserId(int userId) throws DBException {
        return null;
    }

    public float calculateRevenue() throws DBException {
        return 0;
    }
}
