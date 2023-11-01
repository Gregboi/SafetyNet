package repository;

import Domain.Index;
import Domain.FireStation;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class FireStationRepository {

    Index source;

    @Autowired
    public FireStationRepository(Index source) {
        this.source = source;
    }

    public List<FireStation> getAllStations() {
        return source.getFireStations();
    }

    /**For all the stations with same number, but different addresses, get them all.@return a list of FireStation*/
    public Set<FireStation> getStationsByNumber(int station) {
        return getAllStations().stream()
                .filter(fireStation -> fireStation.getFireStation() == station)
                .collect(Collectors.toSet());
    }

    public Set<String> findAllAddresses(Set<FireStation> stations) {
        return stations.stream()
                .map(FireStation::getAddress)
                .collect(Collectors.toSet());
    }

    public List<FireStation> getFireStationsByAddress(String address) {
        List<FireStation> list = new ArrayList<>();
        for (FireStation fireStation : this.getAllStations()) {
            if (fireStation.getAddress().equals(address)) {
                list.add(fireStation);
            }
        }
        return list;
    }

    /*** Create a new station if it does not exist yet, by checking both the address and the station number.*/
    public boolean createNewStation(FireStation station) {
        if (station.getAddress() == null || station.getFireStation() == 0) {
            return false; // Invalid input
        }

        List<FireStation> allStations = getAllStations();
        boolean found = allStations.stream()
                .anyMatch(existingStation ->
                        station.getAddress().equals(existingStation.getAddress()) &&
                                station.getFireStation() == existingStation.getFireStation()
                );

        if (!found) {
            allStations.add(station);
        }

        return !found;
    }

    public int getAge(Date birthDay) {
        LocalDate birthday = birthDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthday, currentDate).getYears();
    }

    /*** Update a station number for an address. @return true if updated successfully, or false if failed.*/
    public boolean updateFireStation(FireStation station) {
        if (station.getAddress() == null || station.getFireStation() == 0) {
            return false; // Invalid input
        }

        List<FireStation> allStations = getAllStations();
        for (FireStation fireStation : allStations) {
            if (station.getAddress().equals(fireStation.getAddress()) && station.getFireStation() != fireStation.getFireStation()) {
                fireStation.setFireStation(station.getFireStation());
                return true;
            }
        }

        return false;
    }

    /*** Delete a station by its address.* @return true if deleted successfully, or false if failed.*/
    public boolean deleteFireStation(FireStation station) {
        if (station.getAddress() == null || station.getFireStation() == 0) {
            return false; // Invalid input
        }

        List<FireStation> allStations = getAllStations();
        boolean removed = allStations.removeIf(existingStation ->
                station.getAddress().equals(existingStation.getAddress())
        );
        return removed;
    }
}
