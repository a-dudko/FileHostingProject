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
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.text.MessageFormat.format;

public class UserSignInServlet extends HttpServlet{
    private final static int MAX_CHARACTERS = 300;

    private final static int MIN_CHARACTERS = 6;

    private final static int MAX_INTERVAL = 604800;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<String> messages = validateUserInformation(request);
            if (messages.size() != 0) {
                request.setAttribute("messages", messages);
            }
            else {
                request.getSession().setAttribute("currentUser", request.getParameter("userLogin"));
                request.getSession().setMaxInactiveInterval(MAX_INTERVAL);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    private List<String> validateUserInformation(HttpServletRequest request) throws NoSuchAlgorithmException {
        List<String> errors = new ArrayList<>();

        String login = request.getParameter("userLogin");
        validateUserStringField(errors, login,"user.login");

        String password = request.getParameter("userPassword");
        validateUserStringFieldLength(errors, password, "user.password");

        if (!isSignInInformationCorrect(login, password)) {
            String errorMessage = format(getMessage("error.user.notexists"));
            errors.add(errorMessage);
        }
        return errors;
    }

    private void validateUserStringField(List<String> errors, String fieldValue, String fieldName) {
        validateUserStringFieldLength(errors, fieldValue, fieldName);
        validateConsistenseOfLettersAndNumbers(errors, fieldValue, fieldName);
    }

    private void validateConsistenseOfLettersAndNumbers(List<String> errors, String fieldValue, String fieldName) {
        if (!fieldValue.matches("[0-9A-Za-z]+")) {
            String errorMessage = format(getMessage("error.field.letandnum"), getMessage(fieldName));
            errors.add(errorMessage);
        }
    }

    private void validateUserStringFieldLength(List<String> errors, String fieldName, String fieldValue) {
        int fieldValueLength = fieldValue.length();
        if (fieldValueLength > MAX_CHARACTERS || fieldValueLength < MIN_CHARACTERS) {
            String errorMessage = format(getMessage("error.field.length"),
                    getMessage(fieldName), MIN_CHARACTERS, MAX_CHARACTERS);
            errors.add(errorMessage);
        }
    }

    private boolean isSignInInformationCorrect(String login, String password) throws NoSuchAlgorithmException {
        List<SiteAdministrator> administrators = SiteAdministratorBC.getInstance().getProfiles();
        for (SiteAdministrator admin : administrators) {
            if (admin.getLogin().equals(login)) {
                if (admin.getPassword().equals(Scrambler.getInstance().getPasswordMD5(password))) {
                    return true;
                }
                return false;
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
