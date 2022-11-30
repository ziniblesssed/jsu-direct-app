package jsudirect;

import java.sql.*;

public class SqlConnector {
    
    Connection conn;

    public SqlConnector(){
       try{conn = DriverManager.getConnection("jdbc:mysql://76.237.185.1:3306/jsudirect","JSUDIRECT", "JSUDIRECTPASSWORD");}
       catch (SQLException e) {}
    };
    

    public Boolean testConnection(String email) throws SQLException {
        try{
            String Query = "SELECT * FROM user WHERE username = '"+ email + "';";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Query);
            rs.next();
            System.out.println(rs);
            if(!rs.equals(null)){
                String value = rs.getString("username");
                System.out.println("SQL Output:"+value);
                if (value.equals("J00000000")){
                    return true;
                }
            }
        }
        catch (SQLException e) {System.out.println(e);}

        return false;
    }
    
    public String getDirections(String origin, String destination) throws SQLException {
        String response = "";
        try {
            String Query = "Select total_length, directions FROM route WHERE origin = " + origin +" AND destination = " + destination +";";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Query);
            
            while(rs.next()){
                response = "Length: " + rs.getInt("total_length");
                response += "Directions: " + rs.getString("directions");
            }
        }
        catch (SQLException e) {}
        return response;
    }

    public void updateDirections(String update) throws SQLException{
        try{
            String Query = "INSERT INTO route VALUES (" + update +");";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(Query);
        }
        catch (SQLException e) {}
    }
}


