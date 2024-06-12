<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Hotel Room Billing</title>
    <style>
        body {
            font-family: Arial, sans-serif; /* Sets the font for the page */
            background-color: #f4f4f4; /* Light grey background */
            margin: 0;
            padding: 20px;
            color: #333; /* Dark grey text color */
        }
        h2 {
            color: #5D6975; /* Subtle blue-grey text color for headings */
        }
        form {
            background: white; /* White background for the form */
            max-width: 400px; /* Maximum width of the form */
            margin: 20px auto; /* Center the form horizontally */
            padding: 20px; /* Padding inside the form */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Subtle shadow around the form */
        }
        label {
            font-weight: bold; /* Makes labels bold */
            margin-top: 10px; /* Space above labels */
            display: block; /* Ensures the label and input appear on new lines */
        }
        input[type="number"],
        select {
            width: 100%; /* Full-width inputs and select box */
            padding: 8px; /* Padding inside the inputs */
            margin-top: 5px; /* Space above inputs */
            box-sizing: border-box; /* Box sizing to include padding in width */
        }
        input[type="submit"] {
            background-color: #4CAF50; /* Green background color */
            color: white; /* White text color */
            padding: 10px; /* Padding inside the button */
            border: none; /* No border */
            border-radius: 5px; /* Rounded corners */
            cursor: pointer; /* Pointer cursor on hover */
            margin-top: 20px; /* Space above the button */
        }
        input[type="submit"]:hover {
            background-color: #45a049; /* Darker green background on hover */
        }
    </style>
</head>
<body>
    <h2>Hotel Room Billing Form</h2>
    <form action="hotelBilling.jsp" method="post">
        <label for="roomType">Select Room Type:</label>
        <select name="roomType" id="roomType">
            <option value="standard">Standard Room</option>
            <option value="deluxe">Deluxe Room</option>
            <option value="suite">Suite</option>
        </select>
        <br><br>
        <label for="nights">Number of Nights:</label>
        <input type="number" id="nights" name="nights" min="1" max="30">
        <br><br>
        <input type="submit" value="Calculate Total">
    </form>

<% 
    // Server-side script to calculate the total cost after the form is submitted
    String roomType = request.getParameter("roomType");
    String nightsStr = request.getParameter("nights");
    if (roomType != null && nightsStr != null && !nightsStr.isEmpty()) {
        int nights = Integer.parseInt(nightsStr);
        double rate = 0.0;
        if ("standard".equals(roomType)) {
            rate = 100.0; // Standard room rate per night
        } else if ("deluxe".equals(roomType)) {
            rate = 150.0; // Deluxe room rate per night
        } else if ("suite".equals(roomType)) {
            rate = 200.0; // Suite room rate per night
        }
        double totalCost = rate * nights;

        // Display the result
        out.println("<h3>Total Cost for " + nights + " night(s) in a " + roomType + " room: $" + totalCost + "</h3>");
    }
%>
</body>
</html>
