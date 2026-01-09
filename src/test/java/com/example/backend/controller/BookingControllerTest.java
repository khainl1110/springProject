// package com.example.backend.controller;

// import com.example.backend.entity.Booking;
// import com.example.backend.entity.Customer;
// import com.example.backend.entity.Movie;
// import com.example.backend.repository.BookingRepository;
// import com.example.backend.repository.CustomerRepository;
// import com.example.backend.repository.MovieRepository;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest      // Full Spring context, uses H2 from test config
// @AutoConfigureMockMvc // Enables MockMvc for testing REST endpoints
// class BookingControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Autowired
//     private BookingRepository bookingRepository;

//     @Autowired
//     private CustomerRepository customerRepository;

//     @Autowired
//     private MovieRepository movieRepository;

//     private Customer testCustomer;
//     private Movie testMovie;

//     @BeforeEach
//     void setUp() {
//         bookingRepository.deleteAll();
//         movieRepository.deleteAll();
//         customerRepository.deleteAll();

//         testCustomer = new Customer("Jane", "Smith");
//         testCustomer = customerRepository.save(testCustomer);

//         testMovie = new Movie();
//         testMovie.setName("The Matrix");
//         testMovie.setDescription("A sci-fi action film");
//         testMovie = movieRepository.save(testMovie);
//     }

//     @Test
//     void testGetAllBookings() throws Exception {
//         Booking booking = new Booking();
//         booking.setCustomer(testCustomer);
//         booking.setMovie(testMovie);
//         bookingRepository.save(booking);

//         mockMvc.perform(get("/booking"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.length()").value(1));
//     }

//     @Test
//     void testCreateBooking() throws Exception {
//         Booking booking = new Booking();
//         booking.setCustomer(testCustomer);
//         booking.setMovie(testMovie);

//         mockMvc.perform(post("/booking")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(booking)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").isNumber());

//         assert bookingRepository.count() == 1;
//     }
// }