package com.hashedin.huSpark.controller;

import com.hashedin.huSpark.dto.BookingRequest;
import com.hashedin.huSpark.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookTicket")
    public ResponseEntity<?> bookTicket(@RequestBody BookingRequest bookingRequest) {
        return bookingService.bookTicket(bookingRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/viewTickets")
    public ResponseEntity<?> viewBookings() {
        return bookingService.viewBookings();
    }

    @GetMapping("/viewTicket/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Long bookingId) {
        return bookingService.getBookingById(bookingId);
    }
}