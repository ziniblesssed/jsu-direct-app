package jsudirect;

import java.io.IOException;
 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/requestEdit")
public class Admin extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name").trim();
        String email = request.getParameter("email").trim();
        String message = request.getParameter("message").trim();

        System.out.println(name);
        System.out.println(email);
        System.out.println(message);

        response.sendRedirect(request.getContextPath() + "success.html");
    }
    
}
