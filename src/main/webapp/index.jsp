<%@ page import="com.example.gis.db.ConnectionProvider" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 8/20/2024
  Time: 11:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <title>Vehicle Information</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <style>
        body {
            background-color: #f0f0f0;
        }
    </style>


</head>
<% Connection connection = ConnectionProvider.getConnection();
        String vehicleNo = "";
    if(request.getParameter("id")!=null) {
            vehicleNo = request.getParameter("id");
        }//QUERY SELECT  * FROM VEHCLE WHERE vehcicle = vehicleNo


    String que = "SELECT * FROM vehicleinfo WHERE vehicleNo = ?";
    PreparedStatement prepared = connection.prepareStatement(que);
    prepared.setString(1, vehicleNo);


    ResultSet ras = prepared.executeQuery();
    String engineCapacity = "";
    String manufacturer="";
    String color = "";
    String engine = "";
    String engineType="";
    String bodyType="";
    String numOfSeats="";


    while (ras.next()) {
             engineCapacity  =  ras.getString("engineCapacity");
             manufacturer =   ras.getString("manufacturer");
             color =      ras.getString("color");
             engine =ras.getString("engine");
             engineType =ras.getString("engineType");
             bodyType =ras.getString("bodyType");
             numOfSeats =ras.getString("numOfSeats");
            vehicleNo =ras.getString("vehicleNo");

    }


%>
<body>
<div class="container">

    <h1>Vehicle Information</h1>

    <hr style="background-color: #ff0000; height: 5px; ">

    <%
    String msg = (String) session.getAttribute("msg");
    if (msg!=null){
        %>
        <h1> <%=msg%> </h1>
    <%
    }
    session.removeAttribute("msg");

%>


    <div class="row">
        <div class="col-md-6">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Vehicle No</th>
                    <th>Body Type</th>
                    <th>Manufacture</th>
                    <th>Engine Capacity</th>
                    <th>Color</th>
                    <th>View</th>
                </tr>
                </thead>
                <tbody>
                <%
                    String query2 = "SELECT * FROM vehicleinfo";


                    PreparedStatement ps = connection.prepareStatement(query2);
                    ResultSet rs = ps.executeQuery();


                    while(rs.next()) {
                %>
                <tr>
                    <td><%= rs.getString("vehicleNo") %></td>
                    <td><%= rs.getString("bodyType") %></td>
                    <td><%= rs.getString("manufacturer") %></td>
                    <td><%= rs.getString("engineCapacity") %></td>
                    <td><%= rs.getString("color") %></td>
                    <td><a href="index.jsp?id=<%=rs.getString("vehicleNo")%>">  View </a> </td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>

        </div>


        <div class="col-md-6">
<form action="/GIS_war_exploded/vehicleinfo" method="post">

            <div class="row">

                <div class="col-md-4">
                    <label>Vehicle No</label>
                    <input type="text" class="form-control" name="vehicleNo" value="<%=vehicleNo%> " id="buttons-hbox">
                </div>

            </div>

            <div class="row">
                <div class="col-md-6">

                    <%
                        String query = "SELECT * FROM bodytype";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        ResultSet resultSet = preparedStatement.executeQuery();
                    %>

                    <label>Body Type</label>
                    <select class="form-control" name="bodyType" >

                        <%while (resultSet.next()){
                            String option =resultSet.getString(1);
                           boolean isSelected = option.equals(bodyType);
                        %>
                        <option value="<%= option %>" <%= isSelected ? "selected" : "" %>><%= option %></option>

                        <%}%>

                    </select>
                </div>
                <div class="col-md-6">
                    <%
                        String query1 ="SELECT * from manufacturer ";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                        ResultSet resultSet1 = preparedStatement1.executeQuery();

                    %>
                    <label>Manfacturer</label>
                    <select class="form-control" name="manufacturer">
                        <% while (resultSet1.next()) {
                            String optionValue = resultSet1.getString(1);
                            boolean isSelected = optionValue.equals(manufacturer); // Check if the current option matches the manufacturer
                        %>
                        <option value="<%= optionValue %>" <%= isSelected ? "selected" : "" %>><%= optionValue %></option>
                        <% } %>
                    </select>

                </div>
            </div>


            <div class="row">

                <div class="col-md-4">
                    <label>Engine Capacity</label>
                    <input type="text" class="form-control" name="engineCapacity" value="<%=engineCapacity%>">
                </div>

                <div class="col-md-4">
                    <label>Num Of Seats</label>
                    <input type="text" class="form-control" name="numOfSeats" value="<%=numOfSeats%>">
                </div>

            </div>
            <div class="row">
                <div class="col-md-4" >
                    <label>Engine Type</label>
                        <input type="text" class="form-control" name="engineType" value="<%=engineType%>" >
                </div>

                <div class="col-md-4">
                    <label>Engine</label>
                    <input type="text" class="form-control" name="engine" value="<%=engine%>">
                </div>

                <div class="col-md-4">
                    <label>Color</label>
                    <input type="text" class="form-control" name="color" value="<%=color%>">
                </div>
            </div>
            <br>

            <div class="container " style="display: flex; gap: 10px;">
                <button class="btn" style="  padding: 10px 10px;  border-radius: 5px; background-color: #007bff; color: white;" value="register" name="btn">Register</button>
                <button class="btn" style="  padding: 10px 10px; border-radius: 5px; background-color: #007bff; color: white;" value="update" name="btn">Update</button>
                <button class="btn" style="  padding: 10px 10px; border-radius: 5px; background-color: #007bff; color: white;" value="delete" name="btn">Delete</button>
                <button class="btn" style="  padding: 10px 10px; border-radius: 5px; background-color: #007bff; color: white;" value="search" name="btn">Search</button>
            </div>
</form>
        </div>

    </div>

</div>
</div>

</body>
</html>

