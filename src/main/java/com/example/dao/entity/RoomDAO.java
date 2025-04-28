package com.example.dao.entity;

import com.example.common.enums.RoomStatus;
import com.example.common.enums.RoomType;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.ManagerFactory;
import com.example.config.DbConnect;
import com.example.dao.IRoomDAO;
import com.example.dto.RoomDTO;
import com.example.model.Room;
import com.example.model.RoomImages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        return true;
//      EntityManager em = null;
//      try{
//          em = ManagerFactory.getEntityManagerFactory().createEntityManager();
//          em.getTransaction().begin();
//          Room.Builder roomEntity = em.find(Room.Builder.class, room.getRoomId());
//          if (roomEntity == null) {
//              // Room not found, rollback and return false or throw a specific exception
//              em.getTransaction().rollback();
//              // Consider throwing an ApplicationException like RoomNotFoundException
//              throw new DBException("Room with ID " + room.getRoomId() + " not found.");
//              // return false; // Or just let the exception propagate
//          }
//
//          // 2. Update Room properties
//          // JPA tracks changes to managed entities. Just set the new values.
//          roomEntity.setRoomNumber(room.getRoomNumber());
//          roomEntity.setRoomType(room.getRoomType()); // Assuming RoomType is an Enum
//          roomEntity.setCapacity(room.getCapacity());
//          roomEntity.setPricePerNight(room.getPricePerNight());
//          roomEntity.setRoomStatus(room.getRoomStatus()); // Assuming RoomStatus is an Enum
//
//          // 3. Delete existing images and insert new ones
//          // This is where the power of JPA relationships and orphanRemoval shines.
//          // Clearing the collection of managed entities triggers deletion on commit
//          // thanks to orphanRemoval=true on the @OneToMany annotation.
//          roomEntity.getImages().clear(); // This marks existing images for deletion
//
//          // Add the new images to the collection
//          if (room.getImagePath() != null && !room.getImagePath().isEmpty()) {
//              for (String imagePath : room.getImagePath()) {
//                  RoomImage newImage = new RoomImage();
//                  newImage.setImagepath(imagePath);
//                  newImage.setRoom(roomEntity); // Set the Many-ToOne relationship
//                  // Add the new image to the Room's collection.
//                  // Due to cascade=PERSIST (or ALL) on the @OneToMany,
//                  // persisting the Room or adding to its collection will cause
//                  // the new RoomImage entities to be persisted automatically.
//                  roomEntity.getImages().add(newImage);
//
//                  // Explicit persist is not strictly necessary if cascade=PERSIST or ALL is used,
//                  // but some developers prefer to explicitly persist new entities.
//                  // em.persist(newImage);
//              }
//          }
//
//          // 4. Commit the transaction
//          // JPA flushes changes (updates, deletes due to orphanRemoval, inserts due to cascade)
//          // to the database upon commit.
//          transaction.commit();
//
//          // If we reached here, the operation was successful
//          return true;
//
//      } catch (PersistenceException e) {
//          // Catch JPA specific exceptions
//          if (transaction != null && transaction.isActive()) {
//              transaction.rollback(); // Rollback transaction on error
//          }
//          throw new DBException(e); // Wrap and rethrow as your custom exception
//      } catch (Exception e) {
//          // Catch any other unexpected exceptions
//          if (transaction != null && transaction.isActive()) {
//              transaction.rollback(); // Rollback transaction on error
//          }
//          throw new DBException(e); // Wrap and rethrow
//      } finally {
//          if (em != null && em.isOpen()) {
//              em.close(); // Always close the EntityManager
//          }
//      }
//      }
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
        return null;
    }
}












