package com.travelagency.destinations.service;

import com.travelagency.destinations.entity.Destination;
import com.travelagency.destinations.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public Destination createDestination(Destination destination) {
        destination.setRating(calculateAverage(destination.getRatings()));
        return destinationRepository.save(destination);
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }

    public List<Destination> getDestinationByName(String name) {
        return destinationRepository.findByName(name);
    }

    public List<Destination> getDestinationByLocation(String location) {
        return destinationRepository.findByLocation(location);
    }

    public Destination updateDestination(Long id, Destination updatedData) {
        Destination destination = destinationRepository.findById(id);
        destination.setName(updatedData.getName());
        destination.setLocation(updatedData.getLocation());
        destination.setDescription(updatedData.getDescription());
        return destination;
    }

    public Destination addDestinationRating(Long id, Integer rating) {
        Destination destination = destinationRepository.findById(id);
        destination.getRatings().add(rating);
        destination.setRating(calculateAverage(destination.getRatings()));
        return destination;
    }

    public void deleteDestination(Long id) {
        destinationRepository.delete(id);
    }

    private double calculateAverage(List<Integer> ratings) {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        return ratings.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }
}
