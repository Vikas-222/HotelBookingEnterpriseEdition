package com.example.dao.entity;

import com.example.common.enums.RoomStatus;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.dao.IRoomDAO;
import com.example.dto.RoomDTO;
import com.example.model.Room;
import com.example.model.RoomImages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class RoomDAO implements IRoomDAO {

    @Override
    public boolean addRoom(RoomDTO room) throws DBException {
        EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Room room1 = new Room.Builder()
                    .setRoomNumber(room.getRoomNumber())
                    .setRoomType(room.getRoomType())
                    .setPricePerNight(room.getPricePerNight())
                    .setCapacity(room.getCapacity()).build();
            em.persist(room1);

            if (room.getImagePath() != null) {
                for (String s : room.getImagePath()) {
                    RoomImages roomImages = new RoomImages(s, room1);
                    em.persist(roomImages);     //not neccessary due to cascadeType.All
                    room1.getRoomImages().add(roomImages);
                }
                em.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return false;
    }

    @Override
    public boolean updateRoom(RoomDTO room) throws DBException {
        return false;
    }

    @Override
    public boolean isRoomNumberExists(int roomNumber) throws DBException {
        EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager();
        String jpql = "select r from Room r where r.roomNumber = :roomNum";
        try {
            em.getTransaction().begin();
            Query q1 = em.createQuery(jpql, Room.class);
            q1.setParameter("roomNum", roomNumber);
            return q1.getSingleResult() != null;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void updateRoomPrice(int roomNumber, Room room) throws DBException {

    }

    @Override
    public void updateRoomStatus(int roomId, RoomStatus status) throws ApplicationException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            String jpqlUpdate = "UPDATE Room r SET r.roomStatus = :newStatus WHERE r.id = :roomId";
            Query query = em.createQuery(jpqlUpdate);
            query.setParameter("newStatus", status);
            query.setParameter("roomId", roomId);
            query.executeUpdate();
            em.getTransaction().commit();
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

    @Override
    public boolean isCapacityValid(int roomNumber, int numberOfGuests) throws DBException {
        return false;
    }

    @Override
    public boolean isValidRoomId(int roomId) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Room room = em.find(Room.class, roomId);
            return room != null;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            throw new DBException(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<RoomDTO> getAllRoomWithImage() throws DBException {
            EntityManager em = null;
            List<RoomDTO> roomDTOs = new ArrayList<>();
            try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
                String jpql = "SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.serviceCharge sc LEFT JOIN FETCH r.roomImages i " +
                        "WHERE r.roomStatus = 'AVAILABLE'";

                TypedQuery<Room> query = em.createQuery(jpql, Room.class);
    }

    @Override
    public float getGstRatesByRoomPrice(float price) throws DBException {
        return 0;
    }

    @Override
    public RoomDTO getRoomById(int roomId) throws DBException {
        return null;
    }
}




