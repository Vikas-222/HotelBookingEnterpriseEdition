package com.example.dao.entity;

import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.dao.IRoomDAO;
import com.example.dto.RoomDTO;
import com.example.model.Room;
import com.example.model.RoomImages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

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
       return false;
    }

    @Override
    public void updateRoomPrice(int roomNumber, Room room) throws DBException {

    }

    @Override
    public void updateRoomStatus(int roomNumber, String status) throws ApplicationException {

    }

    @Override
    public boolean isCapacityValid(int roomNumber, int numberOfGuests) throws DBException {
        return false;
    }

    @Override
    public boolean isValidRoomId(int roomId) throws DBException {
        return false;
    }

    @Override
    public List<RoomDTO> getAllRoomWithImage() throws DBException {
        return null;
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
