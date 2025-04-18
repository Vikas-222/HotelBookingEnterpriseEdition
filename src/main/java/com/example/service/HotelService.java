package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.mapper.HotelMapper;
import com.example.controller.validation.HotelValidator;
import com.example.dao.IHotelDAO;
import com.example.dao.impl.HotelDAOImpl;
import com.example.dto.HotelDTO;
import com.example.model.Hotel;

public class HotelService {

    IHotelDAO iHotelDAO = new HotelDAOImpl();

    public Hotel saveHotel(HotelDTO hotel) throws ApplicationException {
        HotelValidator.isValidDataToSave(hotel);
        return iHotelDAO.saveHotel(HotelMapper.convertHotelDTOToHotel(hotel));
    }

    public void updateHotel(HotelDTO hotelDTO,String id) throws ApplicationException {
        if(id.isBlank()){
            throw new ApplicationException(Messages.HotelError.HOTEL_ID_NOT_FOUND);
        }
        int ID = Integer.parseInt(id);
        if(ID <= 0){
            throw new ApplicationException(Messages.HotelError.INVALID_HOTEL_ID);
        }
        HotelValidator.isValidDataToUpdate(hotelDTO);
        Hotel hotel = HotelMapper.convertHotelDTOToHotelForUpdate(hotelDTO,ID);
        iHotelDAO.updateHotel(hotel);
    }

    public HotelDTO findHotelById(String id) throws ApplicationException {
        if(id.isBlank()){
            throw new ApplicationException(Messages.HotelError.HOTEL_ID_NOT_FOUND);
        }
        int ID = Integer.parseInt(id);
        if(ID <= 0){
            throw new ApplicationException(Messages.HotelError.INVALID_HOTEL_ID);
        }
        return HotelMapper.convertHotelToHotelDTO(iHotelDAO.findHotelById(ID));
    }

}
