package com.example.backend.controller;

import com.example.backend.entity.Booking;
import com.example.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @GetMapping
    public Iterable<Booking> returnAllBooking() {
        return bookingRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Booking> createMovie(@RequestBody Booking booking) {
        Booking createdBooking = bookingRepository.save(booking);
        return ResponseEntity.ok(createdBooking);
    }
}
