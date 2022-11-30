package jsudirect;

import java.io.IOException;
 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.api.client.json.gson.GsonFactory;


@WebServlet("/directions")
public class Directions extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String origin = request.getParameter("origin").trim();
        String destination = request.getParameter("destination").trim();

        System.out.println(origin);
        System.out.println(destination);

        response.sendRedirect(request.getContextPath() + "destination.html");
    }
    
}
