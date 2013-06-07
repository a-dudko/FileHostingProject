package by.bsu.fpmi.second.dudkoAA.servlet;

import by.bsu.fpmi.second.dudkoAA.Scrambler;
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
            administrator.setPassword(Scrambler.getInstance().getPasswordMD5(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return administrator;
    }

    private List<String> getRequestErrors(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        validateUserStringField(errors, request.getParameter("userLogin"),"user.login");

        String password = request.getParameter("userPassword");
        if (isLengthCorrect(errors, password, "user.password")) {
            String repeatPassword = request.getParameter("userRepPassword");
            if (!password.equals(repeatPassword)) {
                String errorMessage = MessageFormat.format(getMessage("error.passwords.notequal"));
                errors.add(errorMessage);
            }
        }

        validateUserStringField(errors, request.getParameter("userLogin"), "user.admincode");

        if (isLoginExists(request.getParameter("userLogin"))) {
            String errorMessage = MessageFormat.format(getMessage("error.login.exists"));
            errors.add(errorMessage);
        }
        return errors;
    }

    private void validateUserStringField(List<String> errors, String fieldValue, String fieldName) {
        isLengthCorrect(errors, fieldValue, fieldName);
        isConsistsOfLettersAndNumbers(errors, fieldValue, fieldName);
    }

    private boolean isConsistsOfLettersAndNumbers(List<String> errors, String fieldName, String fieldValue) {
        if (!fieldValue.matches("[0-9A-Za-z]+")) {
            String errorMessage = MessageFormat.format(getMessage("error.field.letandnum"), getMessage(fieldName));
            errors.add(errorMessage);
            return false;
        }
        return true;
    }

    private boolean isLengthCorrect(List<String> errors, String fieldName, String fieldValue) {
        int fieldValueLength = fieldValue.length();
        if (fieldValueLength > MAX_CHARACTERS || fieldValueLength < MIN_CHARACTERS) {
            String errorMessage = MessageFormat.format(getMessage("error.field.length"),
                    getMessage(fieldName), MIN_CHARACTERS, MAX_CHARACTERS);
            errors.add(errorMessage);
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

    /**
     * get message from property file
     * @param key the key in property file
     * @return the message from property file
     */
    private String getMessage(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        return bundle.getString(key);
    }
}
