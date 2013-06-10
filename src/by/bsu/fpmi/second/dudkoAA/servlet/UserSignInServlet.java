package by.bsu.fpmi.second.dudkoAA.servlet;

import by.bsu.fpmi.second.dudkoAA.Encryptor;
import by.bsu.fpmi.second.dudkoAA.FormItemsValidator;
import by.bsu.fpmi.second.dudkoAA.model.SiteAdministrator;
import by.bsu.fpmi.second.dudkoAA.service.SiteAdministratorService;

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

/** Servlet used for signing in the administrators. */
public class UserSignInServlet extends HttpServlet {

    /** Interval in seconds.*/
    private static final int MAX_INTERVAL = 604800;

    /** Checker for input fields. */
    private FormItemsValidator validator = new FormItemsValidator();

    /** Service for interaction with files storage. */
    private SiteAdministratorService administratorService = new SiteAdministratorService();

    /**
     * Forwards to index page, if the log in information in request is correct,
     * memorizes the user, else shows error messages to the user.
     * @param request with the login and password typed by the user
     * @param response for the request
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response) throws ServletException, IOException {
        try {
            List<String> messages = validateUserInformation(request);
            if (messages.size() != 0) {
                request.setAttribute("messages", messages);
            } else {
                request.getSession().setAttribute("currentUser", request.getParameter("userLogin"));
                request.getSession().setMaxInactiveInterval(MAX_INTERVAL);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Checks if the information entered by the user
     * is correct.
     * @param request with entered by user login and password
     * @return the list of error messages
     * @throws NoSuchAlgorithmException
     */
    private List<String> validateUserInformation(final HttpServletRequest request) throws NoSuchAlgorithmException {
        List<String> errors = new ArrayList<>();

        String login = request.getParameter("userLogin");
        validator.validateUserStringField(errors, login, "user.login");

        String password = request.getParameter("userPassword");
        validator.isLengthCorrect(errors, password, "user.password");

        if (!isSignInInformationCorrect(login, password)) {
            String errorMessage = format(getMessage("error.user.notexists"));
            errors.add(errorMessage);
        }
        return errors;
    }

    /**
     * Checks if such login exists in DB and the password is correct.
     * @param login typed by user
     * @param password typed by user
     * @return if such login exists in DB and the password is correct.
     * @throws NoSuchAlgorithmException
     */
    private boolean isSignInInformationCorrect(final String login, final String password) throws NoSuchAlgorithmException {
        List<SiteAdministrator> administrators = administratorService.getProfiles();
        for (SiteAdministrator admin : administrators) {
            if (admin.getLogin().equals(login)) {
                if (admin.getPassword().equals(Encryptor.getStringMD5(password))) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Get message from property file.
     * @param key the key in property file
     * @return the message from property file
     */
    private String getMessage(final String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        return bundle.getString(key);
    }
}
