package by.bsu.fpmi.second.dudkoAA.servlet;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("register")) {
            registerAdministrator(request, response);
        } else {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/index.jsp"));
        }
    }

    private void registerAdministrator(HttpServletRequest request,
                                       HttpServletResponse response) throws ServletException, IOException {
        List<String> messages = getRequestErrors(request);
        String jspToForward;
        if (messages.size() != 0) {
            request.setAttribute("messages", messages);
            jspToForward = "/registration.jsp";
        }
        else {
            SiteAdministrator administrator = makeSiteAdmiistrator(request);
            SiteAdministratorBC.getInstance().addAdministrator(administrator);
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
        String salt = "hg38iu37";
        messageDigest.update((password + salt).getBytes());
        byte digest[] = messageDigest.digest();
        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }
        return hexString.toString();
    }


    private List<String> getRequestErrors(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        checkParameter(request, errors, "userLogin");

        String password = request.getParameter("userPassword");
        if (isLengthCorrect(errors, "userPassword", password)) {
            String repeatPassword = request.getParameter("userRepPassword");
            if (!password.equals(repeatPassword)) {
                errors.add("Passwords should be equal");
            }
        }

        checkParameter(request, errors, "adminCode");

        if (isLoginExists(request.getParameter("userLogin"))) {
            errors.add("Sorry, such login already exists");
        }
        return errors;
    }

    private void checkParameter(HttpServletRequest request, List<String> errors, String parameter) {
        String parameterValue = request.getParameter(parameter);
        isLengthCorrect(errors, parameter, parameterValue);
        isConsistsOfLettersAndNumbers(errors, parameter, parameterValue);
    }

    private boolean isConsistsOfLettersAndNumbers(List<String> errors, String parameter, String parameterValue) {
        if (!parameterValue.matches("[0-9A-Za-z]+")) {
            errors.add(parameter + " should consist of numbers and letters only");
            return false;
        }
        return true;
    }

    private boolean isLengthCorrect(List<String> errors, String parameter, String parameterValue) {
        int parameterValueLength = parameterValue.length();
        if (parameterValueLength > MAX_CHARACTERS || parameterValueLength < MIN_CHARACTERS) {
            errors.add(parameter + " length should be 6 - 300 symbols");
            return false;
        }
        return true;
    }

    private boolean isLoginExists(String login) {
        List<SiteAdministrator> administrators = SiteAdministratorBC.getInstance().getProfiles();
        for (SiteAdministrator admin : administrators) {
            if (admin.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}
