package com.gabrielperalta.desafiocalidad.repositories;

import com.gabrielperalta.desafiocalidad.dto.HotelDTO;

import java.util.List;

public interface HotelRepository {
    public List<HotelDTO> getAllHotels();
    public void updateBoookedHotels(List<HotelDTO> hotels);
}
