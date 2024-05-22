package com.hashedin.huSpark.service;

import com.hashedin.huSpark.dto.BookingRequest;
import com.hashedin.huSpark.dto.PassengerRequest;
import com.hashedin.huSpark.model.Booking;
import com.hashedin.huSpark.model.Passenger;
import com.hashedin.huSpark.model.Train;
import com.hashedin.huSpark.model.User;
import com.hashedin.huSpark.repository.BookingRepository;
import com.hashedin.huSpark.repository.PassengerRepository;
import com.hashedin.huSpark.repository.TrainRepository;
import com.hashedin.huSpark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PassengerRepository passengerRepository;

    public ResponseEntity<?> bookTicket(BookingRequest bookingRequest) {
        // Get the train
        Optional<User> user = userRepository.findById(bookingRequest.getUserId());
        Train train = trainRepository.findById(bookingRequest.getTrainId()).orElse(null);
        if (train == null || user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please check if the user or train exist");
        }
        // Check seat availability based on seat type
        Integer availableSeats = train.getAvailableSeats(bookingRequest.getSeatType());

        // Check if there are enough available seats
        if (availableSeats >= bookingRequest.getPassengers().size()) {
            // If enough seats available, create booking
            Booking booking = createBookingFromRequest(bookingRequest, user.get(), train, true);
            // Set booking date to current date
            // Save the booking to the database
            bookingRepository.save(booking);
            return ResponseEntity.status(HttpStatus.CREATED).body("Booking created successfully");
        } else {
                // Create booking with waitlisting status
                Booking booking = createBookingFromRequest(bookingRequest,user.get(), train,false);
                // Mark booking as waitlisted
                // Save the booking to the database
                bookingRepository.save(booking);
                return ResponseEntity.status(HttpStatus.CREATED).body("Booking created with waitlisting status");
            }
        }

    public ResponseEntity<?> viewBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No bookings found");
        }
        return ResponseEntity.ok(bookings);
    }

    public ResponseEntity<?> getBookingById(Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking with id " + bookingId + " not found");
        }
        return ResponseEntity.ok(bookingOptional.get());
    }

    private Booking createBookingFromRequest(BookingRequest bookingRequest, User user, Train train, boolean bookingStatus) {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTrain(train);
        booking.setBoardingStation(bookingRequest.getBoardingStation());
        booking.setDropOffStation(bookingRequest.getDropOffStation());
        booking.setTravelDate(bookingRequest.getTravelDate());
        booking.setSeatType(bookingRequest.getSeatType());

        // Create a list to store passengers
        List<Passenger> passengers = new ArrayList<>();

        // Iterate over PassengerRequest objects and create Passenger entities
        for (PassengerRequest passengerRequest : bookingRequest.getPassengers()) {
            Passenger passenger = createPassengerFromRequest(passengerRequest, user, bookingStatus);
            passengerRepository.save(passenger);
            passengers.add(passenger);
        }

        booking.setBookingStatus(bookingStatus);
        // Set booking date to current date
        booking.setBookingDate(LocalDate.now());
        return booking;
    }

    private Passenger createPassengerFromRequest(PassengerRequest passengerRequest, User user, boolean bookingStatus) {
        Passenger passenger = new Passenger();
        passenger.setName(passengerRequest.getName());
        passenger.setAge(passengerRequest.getAge());
        passenger.setUser(user);
        // By default, set isWaitingList to false for passengers
        passenger.setBookingStatus(bookingStatus);
        return passenger;
    }
}

