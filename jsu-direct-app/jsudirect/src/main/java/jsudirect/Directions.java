package jsudirect;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.gson.Gson;

@WebServlet("/directions")
public class Directions extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String origin = request.getParameter("origin").trim();
        String destination = request.getParameter("destination").trim();

        String result = getDirections(origin, destination);
        
        System.out.println("Formated Directions: " + result);

        response.setContentType("text/html");
        response.getWriter().println(result);
    }
    public String getDirections(String origin, String destination){
        String directions = "";
        
        try {
            // create Gson instance
            Gson gson = new Gson();
        
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("directions.json"));
        
            // convert a JSON string to a DestinationData object
            DestinationData data = gson.fromJson(reader,DestinationData.class);

            // print data object
            if(data.originNode.equals(origin) && data.destinationNode.equals(destination)){
                directions = "The distance of your total route is: " + data.distance + " . And below is your directions";
                directions += data.directions;
            }

            // close reader
            reader.close();
        
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return directions;
    }
}
