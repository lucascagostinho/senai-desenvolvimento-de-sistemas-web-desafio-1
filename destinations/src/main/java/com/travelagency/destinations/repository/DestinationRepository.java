package com.travelagency.destinations.repository;

import com.travelagency.destinations.entity.Destination;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DestinationRepository {

    private final List<Destination> destinations = new ArrayList<>();

    public Destination save(Destination destination) {
        destinations.add(destination);
        return destination;
    }

    public List<Destination> findAll() {
        return destinations;
    }

    public List<Destination> findByName(String name) {
        return destinations.stream()
                .filter(d -> d.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public List<Destination> findByLocation(String location) {
        return destinations.stream()
                .filter(d -> d.getLocation().toLowerCase().contains(location.toLowerCase()))
                .toList();
    }

    public Destination findById(Long id) {
        return destinations.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void delete(Long id) {
        destinations.removeIf(d -> d.getId().equals(id));
    }
}
