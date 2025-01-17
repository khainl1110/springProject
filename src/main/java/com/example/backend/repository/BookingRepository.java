package com.example.backend.repository;

import com.example.backend.entity.Booking;
import com.example.backend.entity.Customer;
import com.example.backend.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer(Customer customer);
}
