package com.example.dao;

import com.example.common.exception.DBException;
import com.example.model.RoomImages;

import java.util.List;

public interface IRoomImagesDAO {

    void saveRoomImage(RoomImages roomImage) throws DBException;

    List<RoomImages> getRoomImagesByRoomId(int roomId) throws DBException;
}
