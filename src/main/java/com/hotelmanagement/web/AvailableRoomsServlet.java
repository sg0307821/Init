package com.hotelmanagement.web;

import com.hotelmanagement.dao.RoomsDAO;
import com.hotelmanagement.model.Rooms;

import com.hotelmanagement.dao.CategoryDAO;
import com.hotelmanagement.model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



@WebServlet("/available")
public class AvailableRoomsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	//added for testing need to remove after integration
    	
//    	HttpSession session = request.getSession();
		
		/*
		 * session.setAttribute("customerID",1); session.setAttribute("email",
		 * "johndoe@example.com");
		 */
    	
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categories = categoryDAO.getAllCategories();

        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/room-search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }
    

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomType = request.getParameter("roomType");
        String roomView = request.getParameter("roomView");
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");

		/*
		 * Date checkInDate = Date.valueOf(checkIn); Date checkOutDate =
		 * Date.valueOf(checkOut);
		 */
        
        Date checkInDate = null;
        Date checkOutDate = null;

        try {
            if (checkIn != null && !checkIn.isEmpty()) {
                checkInDate = Date.valueOf(checkIn);
            }
            if (checkOut != null && !checkOut.isEmpty()) {
                checkOutDate = Date.valueOf(checkOut);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid date format. Please use YYYY-MM-DD.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        if (checkInDate == null || checkOutDate == null) {
            request.setAttribute("error", "Check-in and Check-out dates are required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        RoomsDAO roomDAO = new RoomsDAO();
        List<Rooms> availableRooms = roomDAO.getAvailableRooms(roomType, roomView, checkInDate, checkOutDate);
        
        for (Rooms room : availableRooms) {
            System.out.println(room + "availableRooms");
        }
        
        Map<Integer, Rooms> roomMap = new HashMap<>();

        for (Rooms room : availableRooms) {
            Date roomCheckIn = room.getCheckIn();
            Date roomCheckOut = room.getCheckOut();

            // Check if roomCheckIn and roomCheckOut are not null before comparing
            if (roomCheckIn != null && roomCheckOut != null) {
                boolean isOverlapping = !(checkOutDate.before(roomCheckIn) || checkInDate.after(roomCheckOut));
                if (isOverlapping) {
                    // dates overlap
                    if ("Booked".equals(room.getStatus())) {
                        roomMap.put(room.getRoomNumber(), room);
                    }
                } else {
                    // do not overlap
                    roomMap.putIfAbsent(room.getRoomNumber(), room);
                }
            } else {
                // dates are null
                roomMap.putIfAbsent(room.getRoomNumber(), room);
            }
        }

        List<Rooms> filteredRooms = new ArrayList<>(roomMap.values());

        // Print the filteredRooms list to the console
        for (Rooms room : filteredRooms) {
            System.out.println(room);
        }
        request.setAttribute("availableRooms", filteredRooms);
        request.setAttribute("checkInDate", checkInDate);
        request.setAttribute("checkOutDate", checkOutDate);
        request.getRequestDispatcher("available-rooms.jsp").forward(request, response);
    }
}

