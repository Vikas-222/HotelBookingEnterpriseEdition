package com.example.dao.entity;

import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.dao.IRoomDAO;
import com.example.dto.RoomDTO;
import com.example.model.Room;
import com.example.model.RoomImages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

public class RoomDAO implements IRoomDAO {

    @Override
    public boolean addRoom(RoomDTO roomDTO) throws DBException {
        EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager();
        boolean success = false;

        try {
           em.getTransaction().begin();

            Room.Builder room = new Room.Builder();
            room.setRoomNumber(roomDTO.getRoomNumber());
            room.setRoomType(roomDTO.getRoomType());
            room.setCapacity(roomDTO.getCapacity());
            room.setPricePerNight(roomDTO.getPricePerNight());

            // Persist the Room entity first to generate the ID
            em.persist(room);

            List<RoomImages> images = new ArrayList<>();
            for (String imagePath : roomDTO.getImagePath()) {
                RoomImages roomImage = new RoomImages();
                roomImage.setImagepath(imagePath);
                roomImage.setRoom(room.build()); // Set the relationship
                em.persist(roomImage);
                images.add(roomImage);
            }

//            room.setRoomImages(images); // Optional: If you want to maintain the relationship in the Room entity as well

            em.getTransaction().commit();
            success = true;

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
        return success;
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
