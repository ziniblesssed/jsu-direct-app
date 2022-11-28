package jsudirect;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
 
@WebServlet("/signIn")
public class SignIn extends HttpServlet {
 
 private static final long serialVersionUID = 0L;
 private static final String CLIENT_ID = "";
 
 
 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
   try {
     HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
     GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, GsonFactory.getDefaultInstance())
         // Specify the CLIENT_ID of the app that accesses the backend:
         .setAudience(Collections.singletonList(CLIENT_ID)).build();
 
     // (Receive idTokenString by HTTPS POST)
     String idTokenString = request.getParameter("credential");
 
     GoogleIdToken idToken = verifier.verify(idTokenString);
     if (idToken != null) {
       Payload payload = idToken.getPayload();
 
       // Print user identifier
       String userId = payload.getSubject();
       System.out.println("User ID: " + userId);
 
       HttpSession session = request.getSession();
       session.setAttribute("UserID", userId);
 
       // Get profile information from payload
       String email = payload.getEmail();
       String familyName = (String) payload.get("family_name");
       String givenName = (String) payload.get("given_name");
       System.out.println(email);
       System.out.println(givenName);
       System.out.println(familyName);
 
       // Use or store profile information
       // ...
       System.out.println("User ID");
 
     } else {
       System.out.println("Invalid ID token.");
     }
     response.sendRedirect(request.getContextPath() + "index.html");
   } catch (GeneralSecurityException e) {
     e.printStackTrace();
   }
 }
 
 @Override
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
   doGet(request, response);
 }
}