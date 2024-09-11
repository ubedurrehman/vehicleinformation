package com.example.gis.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {

 static {
     try {
         Class.forName("com.mysql.cj.jdbc.Driver");
     } catch (Exception ee) {
         ee.printStackTrace();
     }
 }

     public static Connection getConnection() {
         try {
           return   DriverManager.getConnection("jdbc:mysql://localhost:3307/vehicle","root","");
         }catch (Exception ee){
             ee.printStackTrace();
             return null;
         }
     }


 }

