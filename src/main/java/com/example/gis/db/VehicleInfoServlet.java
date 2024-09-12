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

        String vehicleNo = "";
        String cnic = "";
        if(!btn.equalsIgnoreCase("searchCnic")) {
            vehicleNo = request.getParameter("vehicleNo");
        }

        if(btn.equalsIgnoreCase("searchCnic")){
         cnic  =    request.getParameter("cnic");
            response.sendRedirect("index.jsp?searchByCnic="+cnic);
        }



        String bodyType = "";
        String manufacturer = "";
        int engineCapacity = 0;
        int numOfSeats = 0;
        String engineType = "";
        String engine ="";
        String color = "";

        if(!btn.equalsIgnoreCase("search"))  {
            if(!btn.equalsIgnoreCase("searchCnic")) {
                    manufacturer = request.getParameter("manufacturer");
                    bodyType = request.getParameter("bodyType");
                    engineCapacity = Integer.parseInt(request.getParameter("engineCapacity"));
                    numOfSeats = Integer.parseInt(request.getParameter("numOfSeats"));
                    engineType = request.getParameter("engineType");
                    engine = request.getParameter("engine");
                    color = request.getParameter("color");
            }

        }

        if (btn.equalsIgnoreCase("register")) {
            Connection connection = ConnectionProvider.getConnection();
            HttpSession session = request.getSession();

          //OWNER INSERTION QUERY LAGAO aur erequest value get krrwayo

            try{



               // String ownerId = request.getParameter("ownerId");
//                Yaha agar owner id milllla tu aap ownere ID vehicle m doo go aur sirf vehicle register krro ada
                if(!request.getParameter("ownerId").equals("") && request.getParameter("ownerId") !=null) {
                    int ownerId = Integer.parseInt(request.getParameter("ownerId"));
                    String query = "INSERT INTO VehicleInfo ( vehicleNo, bodyType, manufacturer, engineCapacity, numOfSeats, engineType, engine, color, ownerId) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, vehicleNo);
                        preparedStatement.setString(2, bodyType);
                        preparedStatement.setString(3, manufacturer);
                        preparedStatement.setInt(4, engineCapacity);
                        preparedStatement.setInt(5, numOfSeats);
                        preparedStatement.setString(6, engineType);
                        preparedStatement.setString(7, engine);
                        preparedStatement.setString(8, color);
                        preparedStatement.setInt(9, ownerId);


                        int rows = preparedStatement.executeUpdate();
                        PrintWriter out = response.getWriter();
                        if (rows > 0) {
                            session.setAttribute("msg", "Record inserted successfully.");
                            out.println("Record inserted successfully.");
                        } else {
                            session.setAttribute("msg", "Vehicle record could not be inserted.");
                            out.println("Vehicle record  not be inserted.");
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                        }
//YAHA p agar id Nahii milla tu hum owner ko add krrygy ada
                else{
                    String ownerName =request.getParameter("ownerName");
                    String ownerFname=request.getParameter("ownerFname");
                    cnic=request.getParameter("cnic");
                    String ownerAddress=request.getParameter("ownerAddress");
                    String query1="INSERT INTO owner ( ownerName, ownerFname,cnic,ownerAddress) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query1);
                    preparedStatement2.setString(1,ownerName);
                    preparedStatement2.setString(2,ownerFname);
                    preparedStatement2.setString(3,cnic);
                    preparedStatement2.setString(4,ownerAddress);

                    int r =  preparedStatement2.executeUpdate();

                    PrintWriter out = response.getWriter();
                    if (r>0) {

//                        Ownere j Details krry eendi eeho jeko haani insert thyoo aahy
//                        Solution :
//                        aap ek Query Challalo Select k us m where cond lagao where cnic = 'cnic'
//                        PORE ROW MEL JAIGE PHR  OWNER ID PHR MA VEHICLEINFO KO DNE HOGE
                        String q12="Select * from owner where cnic=?";

                        PreparedStatement ps=connection.prepareStatement(q12);
                        ps.setString(1,cnic);
                        ResultSet rs=ps.executeQuery();

                        if (rs.next()){
                            int ownerId = rs.getInt("ownerId");
                            String insertVehicleQuery = "INSERT INTO VehicleInfo ( vehicleNo, bodyType, manufacturer, engineCapacity, numOfSeats, engineType, engine, color, ownerId) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
                            PreparedStatement vps = connection.prepareStatement(insertVehicleQuery);
                            vps.setString(1, vehicleNo);
                            vps.setString(2, bodyType);
                            vps.setString(3, manufacturer);
                            vps.setInt(4, engineCapacity);
                            vps.setInt(5, numOfSeats);
                            vps.setString(6, engineType);
                            vps.setString(7, engine);
                            vps.setString(8, color);
                            vps.setInt(9, ownerId);

                            int i = vps.executeUpdate();

                            session.setAttribute("msg", "Vehicle Registered Successfullly!");
                            out.println("Vehicle Registered Successfullly.");
                        } else {
                            session.setAttribute("msg", "Vehicle Could not Successfullly!s");
                            out.println("Owner not record.");

                        }
                        }


                }
                ///


            } catch (Exception e) {
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
