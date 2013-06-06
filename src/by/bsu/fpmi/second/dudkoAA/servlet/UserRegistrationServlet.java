package by.bsu.fpmi.second.dudkoAA.servlet;

import by.bsu.fpmi.second.dudkoAA.model.File;
import by.bsu.fpmi.second.dudkoAA.model.SiteAdministrator;
import by.bsu.fpmi.second.dudkoAA.service.SiteAdministratorBC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserRegistrationServlet extends HttpServlet{

    private final static int MAX_CHARACTERS = 300;

    private final static int MIN_CHARACTERS = 6;

    private SiteAdministratorBC siteAdministratorBC = SiteAdministratorBC.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> messages = getRequestErrors(request);
        String jspToForward;
        if (messages.size() != 0) {
            request.setAttribute("messages",messages);
            jspToForward = "/registration.jsp";
        }
        else {

            SiteAdministrator administrator = makeSiteAdmiistrator(request);
            siteAdministratorBC.addAdministrator(administrator);
            jspToForward = "/index.jsp";
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspToForward);
        dispatcher.forward(request, response);
    }

    private SiteAdministrator makeSiteAdmiistrator(HttpServletRequest request) {
        SiteAdministrator administrator = new SiteAdministrator();
        administrator.setLogin(request.getParameter("userLogin"));
        String password = request.getParameter("userPassword");
        try {
            administrator.setPassword(getPasswordMD5(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return administrator;
    }

    private String getPasswordMD5(String password) throws NoSuchAlgorithmException {
        StringBuffer hexString = new StringBuffer();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte digest[] = messageDigest.digest();
        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }
        return hexString.toString();
    }


    private List<String> getRequestErrors(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        String login = request.getParameter("userLogin");
        if (isAttributeCorrect(login)) {
            errors.add("Login length should be 6 - 300 symbols");
        }
        else if (!login.matches("[0-9A-Za-z]+")) {
            errors.add("Login should consist of numbers and letters only");
        }

        String password = request.getParameter("userPassword");
        if (isAttributeCorrect(password)) {
            errors.add("Password length should be 6 - 300 symbols");
        }
        else {
            String repeatPassword = request.getParameter("userRepPassword");
            if (!password.equals(repeatPassword)) {
                errors.add("Passwords should be equal");
            }
        }

        String adminCode = request.getParameter("adminCode");
        if (isAttributeCorrect(adminCode)) {
            errors.add("Admin code length should be 6 - 300 symbols");
        }
        else if (!adminCode.matches("[0-9A-Za-z]+")) {
            errors.add("Admin Code should consist of numbers and letters only");
        }

        if (isLoginExists(request.getParameter("userLogin"))) {
            errors.add("Sorry, such login already exists");
        }

        return errors;
    }

    private boolean isAttributeCorrect(String attributeValue) {
        int attributeValueLength = attributeValue.length();
        if (attributeValueLength > MAX_CHARACTERS || attributeValueLength < MIN_CHARACTERS) {
            return false;
        }
        return true;
    }

    private boolean isLoginExists(String login) {
        List<SiteAdministrator> administrators = siteAdministratorBC.getProfiles();
        for (SiteAdministrator admin : administrators) {
            if (admin.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}
