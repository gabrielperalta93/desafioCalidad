package com.gabrielperalta.desafiocalidad.repositories;

import com.gabrielperalta.desafiocalidad.dto.FlightDTO;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class FlightRepositoryImpl implements FlightRepository{
    @Override
    public List<FlightDTO> getAllFlights() {
        List<FlightDTO> flightDTOS = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try
        {
            String filePath = new File("").getAbsolutePath();
            BufferedReader br = new BufferedReader(new FileReader(filePath + "/src/main/resources/dbFlights.csv"));
            Boolean first = true;
            while ((line = br.readLine()) != null)
            {
                if (!first){
                    String[] flights = line.split(splitBy);
                    FlightDTO flightDTO = new FlightDTO();
                    flightDTO.setFlightId(Integer.parseInt(flights[0]));
                    flightDTO.setFlightNumber(flights[1]);
                    flightDTO.setOrigin((flights[2]));
                    flightDTO.setDestination((flights[3]));
                    flightDTO.setSeatType((flights[4]));
                    flightDTO.setAmount(Integer.parseInt(flights[5].replace("$", "").replace(".", "")));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(flights[6].replace("/", "-"), formatter);
                    flightDTO.setDateFrom((date));
                    date = LocalDate.parse(flights[7].replace("/", "-"), formatter);
                    flightDTO.setDateTo((date));
                    flightDTOS.add(flightDTO);
                }else
                    first = false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return flightDTOS;
    }
}
