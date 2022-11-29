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
 
 private static final String CLIENT_ID = "758470417555-7j0uner8orai70vt2s65s6igj2uj9shr.apps.googleusercontent.com";
 
 
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
       //Send Username to endpoint, verify it exist and return boolean
       if(getVerificationStatus(userId)){
           System.out.println("This user can edit nodes and edges. Redirect to Admin Page");
           response.sendRedirect(request.getContextPath() + "admin.html");
           return;
       }else{
        System.out.println("This user cannot edit nodes and edges. Redirect to Jsu Direct Home Page");
       }
 
     } else {
       System.out.println("Invalid ID token.");
     }
     response.sendRedirect(request.getContextPath() + "index.html");
   } catch (GeneralSecurityException e) {
     e.printStackTrace();
   }
 }
 
 private boolean getVerificationStatus(String userId) {
    return true;
}

@Override
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
   doGet(request, response);
 }
}