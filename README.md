# Movie Booking Application

## Overview
This is a Movie Booking Application built using Spring Boot. The application allows users to view movies, book tickets, and manage admin functionalities.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- MySQL (or your preferred database)
- Maven (for dependency management)

## Features
- View movies by ID
- Retrieve all movies
- Retrieve booking history
- Add admin users
- Add new movies with Verified admin credentials
- Delete movies with Verified admin credentials
- Book tickets for a specific movie
- Sends mail on successfull booking to the user mail
- Cancel Seat Booking
- Get Available Seats for a Movie
- Get All Seat Bookings by Movie ID

### Prerequisites
- JDK 17 or higher
- Maven
- MySQL or another relational database
- IDE (like IntelliJ IDEA or Eclipse)

## Contributing
- Contributions are welcome! If you would like to contribute, please fork the repository and create a pull request with a detailed description of your changes.
- We are also open to contributions for the front end, so feel free to improve the user interface or add new features!

## Note: 
Make sure to update application.properties with your database credentials and other configuration details before running the application.



## API Endpoints

### 1. Get Movie by ID
- **URL:** `/getMovie`
- **Method:** `GET`
- **Query Params:**
  - `id`: Integer (Movie ID)
- **Response:** 
  - Movie details or 404 if not found.

---

### 2. Get All Movies
- **URL:** `/getAll`
- **Method:** `GET`
- **Response:** 
  - List of all movies.

---

### 3. Get Booking History
- **URL:** `/gethistory`
- **Method:** `GET`
- **Response:** 
  - List of booking history.

---

### 4. Add Admin
- **URL:** `/addadmin`
- **Method:** `POST`
- **Query Params:**
  - `username`: String
  - `password`: String
- **Response:** 
  - Admin details.

---

### 5. Add Movie
- **URL:** `/addmovie`
- **Method:** `POST`
- **Query Params:**
  - `username`: String
  - `password`: String
  - `title`: String
  - `director`: String
  - `description`: String
  - `genre`: String
  - `date`: LocalDate
  - `location`: String
  - `totalSeats`: Integer
  - `availableSeats`: Integer
  - `price`: Integer
- **Response:** 
  - Movie addition status (Boolean).

---

### 6. Delete Movie
- **URL:** `/deletemovie`
- **Method:** `DELETE`
- **Query Params:**
  - `username` : String
  - `password` : String
  - `id`: Integer (Movie ID)
- **Response:** 
  - Confirmation of deletion.

---

### 7. Book Ticket
- **URL:** `/bookticket`
- **Method:** `POST`
- **Query Params:**
  - `id`: Integer (Movie ID)
  - `contact`: Long
  - `mail`: String
  - `seats`: Integer[]
  - `date`: LocalDate
- **Response:** 
  - Confirmation of ticket booking.
  - Sends mail on successfull booking.

---

### 8. Get All Seat Bookings by Movie ID
- **URL:** `/seatbookings/{movieId}`
- **Method:** `GET`
- **Path Variables:**
  - `movieId`: Integer (Movie ID)
- **Response:** 
  - List of seat bookings for the movie, or 204 No Content if no bookings.

---

### 9. Get Available Seats for a Movie
- **URL:** `/available-seats/{movieId}`
- **Method:** `GET`
- **Path Variables:**
  - `movieId`: Integer (Movie ID)
- **Response:** 
  - List of available seats, or 204 No Content if no available seats.
  
---

### 10. Cancel Seat Booking
- **URL:** `/cancel-seat-booking`
- **Method:** `POST`
- **Path Variables:**
  - `movieId`: Integer (Movie ID)
  - `seatNumber`: Integer
- **Response:** 
  - Success message or error message if the seat was not found or not booked.
