package com.example.dao.entity;

import com.example.common.Messages;
import com.example.common.enums.BookingStatus;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.dto.RoomDTO;
import com.example.entitymodal.Booking;
import com.example.entitymodal.User;
import com.example.model.Room;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.sql.*;
import java.util.List;

public class BookingDAO {

//    public Booking addBooking(Booking booking) throws DBException {
//        EntityManager em = null;
//        try {
//            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
//            em.getTransaction().begin();
//            em.persist(booking);
//            return booking;
//        } catch (RuntimeException e) {
//            throw new DBException(e);
//        } finally {
//            assert em != null;
//            if (em.getTransaction().isActive() && em.getTransaction() != null) {
//                em.getTransaction().rollback();
//                em.close();
//            }
//        }
//    }


    public Booking addBooking(Booking bookingService, int userId, int roomId) throws ApplicationException {
        EntityManager em = null;
        Booking newBookingObject;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            User user = em.find(User.class, userId);
            Room room = em.find(Room.class, roomId);
            if (user == null) {
                throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
            }
            if (room == null) {
                throw new ApplicationException(Messages.RoomError.ROOM_ID_NOT_FOUND);
            }

            newBookingObject = new Booking.Builder()
                    .setUser(user)
                    .setRoom(room)
                    .setCheckInTime(bookingService.getCheckInTime())
                    .setCheckOutTime(bookingService.getCheckOutTime())
                    .setTotalAmount(bookingService.getTotalAmount())
                    .setBookingStatus(bookingService.getBookingStatus())
                    .setCancellationDate(bookingService.getCancellationDate())
                    .setRefundAmount(bookingService.getRefundAmount())
                    .setGstRates(bookingService.getGstRates())
                    .setNumberOfGuests(bookingService.getNumberOfGuests())
                    .setCreatedAt(bookingService.getCreatedAt())
                    .setUpdatedAt(bookingService.getUpdatedAt()).build();

            em.persist(newBookingObject);
            /**
             * Force initialization of a proxy or persistent collection.
             * In the case of a many-valued association, only the collection itself is initialized.
             * It is not guaranteed that the associated entities held within the collection will be initialized.
             */
//            Hibernate.initialize(newBookingObject.getRoom().getRoomImages());
            newBookingObject.getRoom().getRoomImages();
            em.getTransaction().commit();

        } catch (RuntimeException e) {
            if (em != null && em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } catch (ApplicationException e) {
            throw new ApplicationException(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return newBookingObject;
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
        } finally {
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
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            String jpql = "UPDATE Booking b SET b.bookingStatus = :status, b.cancellationDate = :cancelDate, " +
                    "b.refundAmount = :refund WHERE b.bookingId = :id";
            Query query = em.createQuery(jpql);
            query.setParameter("status", BookingStatus.CANCELLATION);
            query.setParameter("cancelDate", cancellationDate != null ? cancellationDate.toLocalDate() : null);
            query.setParameter("refund", refundAmount);
            query.setParameter("id", bookingId);
            return query.executeUpdate() > 0;
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
        } finally {
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
