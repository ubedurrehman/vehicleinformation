package com.example.gis.db;

import com.example.gis.db.ConnectionProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/vehicleinfo")
public class VehicleInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String btn = request.getParameter("btn");

        String vehicleNo = request.getParameter("vehicleNo");
        String bodyType = "";
        String manufacturer = "";
        int engineCapacity = 0;
        int numOfSeats = 0;
        String engineType = "";
        String engine ="";
        String color = "";
        if(!btn.equalsIgnoreCase("search")) {
             manufacturer = request.getParameter("manufacturer");
             bodyType =   request.getParameter("bodyType");
             Integer.parseInt(request.getParameter("engineCapacity"));
             numOfSeats = Integer.parseInt(request.getParameter("numOfSeats"));
             engineType =  request.getParameter("engineType");
             engine  =  request.getParameter("engine");
             color=  request.getParameter("color");
        }
        if (btn.equalsIgnoreCase("register")) {


            //harec connecton watha unkha bd ma try carhc m pereped m kadhan
            Connection connection = ConnectionProvider.getConnection();
            String query = "INSERT INTO VehicleInfo (vehicleNo, bodyType, manufacturer, engineCapacity, numOfSeats, engineType, engine, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


            HttpSession session = request.getSession();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, vehicleNo);
                preparedStatement.setString(2, bodyType);
                preparedStatement.setString(3, manufacturer);
                preparedStatement.setInt(4, engineCapacity);
                preparedStatement.setInt(5, numOfSeats);
                preparedStatement.setString(6, engineType);
                preparedStatement.setString(7, engine);
                preparedStatement.setString(8, color);

            int rows = preparedStatement.executeUpdate();

                PrintWriter out = response.getWriter();
                if (rows>0) {
                    session.setAttribute("msg", "Record Inserted Successfully!");
                    out.println("Record inserted successfully.");
                } else {
                    session.setAttribute("msg", "Record Could not be Inserted");
                    out.println("not record.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                requestDispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (btn.equalsIgnoreCase("update")) {

            Connection connection = ConnectionProvider.getConnection();
            String query = "UPDATE VehicleInfo SET bodyType=?, manufacturer=?, engineCapacity=?, numOfSeats=?, engineType=?, engine=?, color=? WHERE vehicleNo=?";

            HttpSession session = request.getSession();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, bodyType);
                preparedStatement.setString(2, manufacturer);
                preparedStatement.setInt(3, engineCapacity);
                preparedStatement.setInt(4, numOfSeats);
                preparedStatement.setString(5, engineType);
                preparedStatement.setString(6, engine);
                preparedStatement.setString(7, color);
                preparedStatement.setString(8, vehicleNo);

                int rows = preparedStatement.executeUpdate();

                PrintWriter out = response.getWriter();
                if (rows > 0) {
                    session.setAttribute("msg", "Recorde Updated Successfuley!");
                    out.println("Record updated successfully.");
                } else {
                    session.setAttribute("msg", "Recorde  not be Updated");
                    out.println("Record not updated.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

           try{
               RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                    requestDispatcher.forward(request,response);
           }catch (Exception e){
               e.printStackTrace();
           }
        }else if (btn.equalsIgnoreCase("delete")) {
            Connection connection = ConnectionProvider.getConnection();
            String query = "DELETE FROM VehicleInfo WHERE vehicleNo=?";

            HttpSession session = request.getSession();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, vehicleNo);

                int rows = preparedStatement.executeUpdate();

                PrintWriter out = response.getWriter();
                if (rows > 0) {
                    session.setAttribute("msg", "Record deleted successfully!");
                    out.println("Record deleted successfully.");
                } else {
                    session.setAttribute("msg", "Record not found.");
                    out.println("Record not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                requestDispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        else if(btn.equalsIgnoreCase("search")){
          response.sendRedirect("index.jsp?id="+vehicleNo);
        }


    }
}

//PELI APP table banany hai us m colums add krrni jo coulumns page m hai sarri
// us k baad aap yaha per insert k query challao gy.
