package com.travelagency.destinations.controller;

import com.travelagency.destinations.dto.RatingRequest;
import com.travelagency.destinations.entity.Destination;
import com.travelagency.destinations.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @PostMapping
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {
        return new ResponseEntity<>(destinationService.createDestination(destination), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Destination>> getAllDestinations() {
        return new ResponseEntity<>(destinationService.getAllDestinations(), HttpStatus.OK);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Destination>> getDestinationByName(@RequestParam String name) {
        return new ResponseEntity<>(destinationService.getDestinationByName(name), HttpStatus.OK);
    }

    @GetMapping("/search/location")
    public ResponseEntity<List<Destination>> getDestinationByLocation(@RequestParam String location) {
        return new ResponseEntity<>(destinationService.getDestinationByLocation(location), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        return new ResponseEntity<>(destinationService.getDestinationById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody Destination destination) {
        return new ResponseEntity<>(destinationService.updateDestination(id, destination), HttpStatus.OK);
    }

    @PostMapping("/{id}/ratings")
    public ResponseEntity<Destination> addDestinationRating(@PathVariable Long id, @RequestBody RatingRequest request) {
        return new ResponseEntity<>(destinationService.addDestinationRating(id, request.getRating()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
