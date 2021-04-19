package com.gabrielperalta.desafiocalidad.repositories;

import com.gabrielperalta.desafiocalidad.dto.HotelDTO;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class HotelRepositoryImpl implements HotelRepository{
    @Override
    public List<HotelDTO> getAllHotels() {
        List<HotelDTO> hotels = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try
        {
            String filePath = new File("").getAbsolutePath();
            BufferedReader br = new BufferedReader(new FileReader(filePath + "/src/main/resources/dbHotel.csv"));
            Boolean first = true;
            while ((line = br.readLine()) != null)
            {
                if (!first){
                    String[] hoteles = line.split(splitBy);
                    HotelDTO hotelDTO = new HotelDTO();
                    hotelDTO.setHotelId(Integer.parseInt(hoteles[0]));
                    hotelDTO.setHotelCode(hoteles[1]);
                    hotelDTO.setName((hoteles[2]));
                    hotelDTO.setDestination((hoteles[3]));
                    hotelDTO.setRoomType((hoteles[4]));
                    int precioNoche = Integer.parseInt(hoteles[5].replace("$", ""));
                    hotelDTO.setAmount(precioNoche);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(hoteles[6].replace("/", "-"), formatter);
                    hotelDTO.setDateFrom((date));
                    date = LocalDate.parse(hoteles[7].replace("/", "-"), formatter);
                    hotelDTO.setDateTo((date));
                    Boolean reservado = false;
                    if (hoteles[8].toLowerCase().equals("si")){
                        reservado = true;
                    }
                    hotelDTO.setBooked(reservado);
                    hotels.add(hotelDTO);
                }else
                    first = false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return hotels;
    }

    @Override
    public void updateBoookedHotels(List<HotelDTO> hotels) {
        FileWriter writer = null;
        try {
            String filePath = new File("").getAbsolutePath();
            writer = new FileWriter(filePath + "/src/main/resources/dbHotel.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line = "hotelId,codigoHotel,nombre,lugar,tipoHabitacion,precioNoche,disponibleDesde,disponibleHasta,reservado\n";
        String price = "";
        String booked = "";
        String dateFrom = "";
        String dateTo = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (HotelDTO hotel: hotels) {
            price = "$" + String.valueOf(hotel.getAmount()).replace(",",".");
            booked = hotel.getBooked() ? "SI" : "NO";
            dateFrom = dtf.format(hotel.getDateFrom()).replace("-", "/");
            dateTo = dtf.format(hotel.getDateTo()).replace("-", "/");
            line += hotel.getHotelId() + "," + hotel.getHotelCode() + "," + hotel.getName() + "," + hotel.getDestination() + "," + hotel.getRoomType() + "," + price + "," + dateFrom + "," + dateTo  + "," + booked + "\n";
        }
        try {
            writer.write(line);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
