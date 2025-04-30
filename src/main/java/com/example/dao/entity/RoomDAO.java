package com.example.dao.entity;

import com.example.common.enums.RoomStatus;
import com.example.common.enums.RoomType;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.dao.IRoomDAO;
import com.example.dto.RoomDTO;
import com.example.model.Room;
import com.example.model.RoomImages;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class RoomDAO implements IRoomDAO {

    @Override
    public Room addRoom(RoomDTO roomDTO) throws DBException {
        EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager();
        String jpql = "select rsc.chargePerNight from RoomServiceCharge rsc where rsc.roomType";
        try {
            em.getTransaction().begin();
            Room room1 = new Room.Builder()
                    .setRoomNumber(roomDTO.getRoomNumber())
                    .setRoomType(roomDTO.getRoomType())
                    .setPricePerNight(roomDTO.getPricePerNight())
                    .setCapacity(roomDTO.getCapacity()).build();
            em.persist(room1);
            if (roomDTO.getImagePath() != null && !roomDTO.getImagePath().isEmpty()) {
                for (String imagePath : roomDTO.getImagePath()) {
                    RoomImages roomImage = new RoomImages(); // Create RoomImages entity
                    roomImage.setImagepath(imagePath);
                    room1.addRoomImage(roomImage); // Assumes addRoomImage method exists on Room
                    em.persist(roomImage);
                }
            }
            em.getTransaction().commit();
            return room1;
        }catch (PersistenceException e) {
            throw new DBException(e);
        }
        catch (Exception e) {
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
    public boolean updateRoom(RoomDTO room) throws DBException {
        EntityManager em = null;
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Room existingRoom = em.find(Room.class, room.getRoomId());
            Query updateRoomQuery = em.createQuery("UPDATE Room r SET r.roomNumber = :roomNumber, r.roomType = :roomType," +
                    " r.capacity = :capacity, r.pricePerNight = :pricePerNight, r.roomStatus = :roomStatus WHERE r.roomId = :roomId");
            updateRoomQuery.setParameter("roomNumber", room.getRoomNumber() == 0 ? existingRoom.getRoomNumber() : room.getRoomNumber());
            updateRoomQuery.setParameter("roomType", room.getRoomType() == null ? existingRoom.getRoomType() : room.getRoomType());
            updateRoomQuery.setParameter("capacity", room.getCapacity() == 0 ? existingRoom.getCapacity() : room.getCapacity());
            updateRoomQuery.setParameter("pricePerNight", room.getPricePerNight() == 0.0f ? existingRoom.getPricePerNight() : room.getPricePerNight());
            updateRoomQuery.setParameter("roomStatus", room.getRoomStatus() == null ? existingRoom.getRoomStatus() : room.getRoomStatus());
            updateRoomQuery.setParameter("roomId", room.getRoomId());
            int roomUpdateResult = updateRoomQuery.executeUpdate();

            if (roomUpdateResult > 0) {
                Query deleteImagesQuery = em.createQuery("DELETE FROM RoomImages ri WHERE ri.room.roomId = :roomId");
                deleteImagesQuery.setParameter("roomId", room.getRoomId());
                deleteImagesQuery.executeUpdate();

                if (room.getImagePath() != null && !room.getImagePath().isEmpty()) {
                    for (String imagePath : room.getImagePath()) {
                        RoomImages roomImage = new RoomImages();
                        roomImage.setImagepath(imagePath);
                        Room roomEntity = em.find(Room.class, room.getRoomId());
                        roomImage.setRoom(roomEntity);
                        em.persist(roomImage);
                    }
                    transaction.commit();
                    return true;
                } else {
                    transaction.commit();
                    return true;
                }
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);
        }
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
    public boolean isCapacityValid(int roomId, int numberOfGuests) throws DBException {
        String jpql = "select r.capacity from Room r where r.roomId = :roomId";
        try(EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager()){
        TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
        query.setParameter("roomId", roomId);
        int capacity = query.getSingleResult();
        return numberOfGuests <= capacity;
        }catch (PersistenceException e) {
            throw new DBException(e);
        }catch(Exception e) {
            throw new DBException(e);
        }
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
    public List<RoomDTO> getAllRoomWithImage() throws DBException {
        EntityManager em = null;
        List<RoomDTO> roomDTOs = new ArrayList<>();
        try {
            em = ManagerFactory.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.serviceCharge sc LEFT JOIN FETCH r.roomImages i " +
                    "WHERE r.roomStatus = 'AVAILABLE'";
            em.getTransaction().begin();
            TypedQuery<Room> query = em.createQuery(jpql, Room.class);
            List<Room> roomList = query.getResultList();
            for (Room room : roomList) {
                List<String> imagePaths = new ArrayList<>();
                if (room.getRoomImages() != null) {
                    for (RoomImages image : room.getRoomImages()) {
                        imagePaths.add(image.getImagepath());
                    }
                }
                RoomDTO roomDTO = new RoomDTO.Builder()
                        .setRoomId(room.getRoomId())
                        .setRoomNumber(room.getRoomNumber())
                        .setRoomType(room.getRoomType())
                        .setCapacity(room.getCapacity())
                        .setPricePerNight(room.getPricePerNight())
                        .setRoomStatus(room.getRoomStatus())
                        .setRoomServiceCharge(room.getServiceCharge() != null ? room.getServiceCharge().getChargePerNight() : 0.0f)
                        .setImagePath(imagePaths)
                        .build();
                roomDTOs.add(roomDTO);
            }
            em.getTransaction().commit();
        } catch (NoResultException e) {
            return new ArrayList<>();
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
        return roomDTOs;
    }


    @Override
    public float getGstRatesByRoomPrice(float price) throws DBException {
        return 0;
    }

    @Override
    public RoomDTO getRoomById(int roomId) throws DBException {
//        try(EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager()){
//
//        }
        return null;
    }

    @Override
    public Float getRoomServiceCharge(RoomType roomType) throws DBException {
        String jpql = "select r.chargePerNight from RoomServiceCharge r where r.roomType = :roomType";
        try(EntityManager em = ManagerFactory.getEntityManagerFactory().createEntityManager()){
            em.getTransaction().begin();
            Query query = em.createQuery(jpql);
            query.setParameter("roomType",roomType);
            return (Float) query.getSingleResult();
        }catch(NoResultException e){
            throw new DBException(e);
        }
        catch(PersistenceException e){
            throw new DBException(e);
        }
        catch(Exception e){
            throw new DBException(e);
        }
    }
}












