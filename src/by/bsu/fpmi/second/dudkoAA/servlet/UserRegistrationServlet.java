package by.bsu.fpmi.second.dudkoAA.servlet;

import by.bsu.fpmi.second.dudkoAA.Encryptor;
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

/** Servlet used for registering the administrators. */
public class UserRegistrationServlet extends HttpServlet {

    /** Maximum number of characters in input field. */
    private static final int MAX_CHARACTERS = 300;

    /** Minimum number of characters in input field. */
    private static final int MIN_CHARACTERS = 6;

    /** Code given for users to verify their rights for registration. */
    private static final String ADMIN_CODE = "LIDA1323";

    /**
     * Checks the action in the form was chosen:
     * register or cancel - and follows it.
     * @param request request with the information about
     *                action was chosen
     * @param response response to the request
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("register")) {
            registerAdministrator(request, response);
        } else {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/index.jsp"));
        }
    }

    /**
     * Checks if the information in request is correct
     * and adds new administrator in DB or shows request
     * errors.
     * @param request request with registration info
     * @param response response for the request
     * @throws ServletException
     * @throws IOException
     */
    private void registerAdministrator(final HttpServletRequest request,
                                       final HttpServletResponse response) throws ServletException, IOException {
        List<String> messages = getRequestErrors(request);
        String jspToForward;
        if (messages.size() != 0) {
            request.setAttribute("messages", messages);
            jspToForward = "/registration.jsp";
        } else {
            SiteAdministrator administrator = makeSiteAdministratorObject(request);
            SiteAdministratorService.getInstance().addAdministrator(administrator);
            jspToForward = "/index.jsp";
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspToForward);
        dispatcher.forward(request, response);
    }

    /**
     * Creates SiteAdministrator object according to the information in request.
     * @param request request with registration information
     * @return administrator object
     */
    private SiteAdministrator makeSiteAdministratorObject(final HttpServletRequest request) {
        SiteAdministrator administrator = new SiteAdministrator();
        administrator.setLogin(request.getParameter("userLogin"));
        String password = request.getParameter("userPassword");
        try {
            administrator.setPassword(Encryptor.getInstance().getPasswordMD5(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return administrator;
    }

    /**
     * Checks the information about the user in request and adds
     * error messages to errors.
     * @param request request with entered by user login and password
     * @return the list of error messages
     */
    private List<String> getRequestErrors(final HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        validateUserStringField(errors, request.getParameter("userLogin"), "user.login");

        String password = request.getParameter("userPassword");
        if (isLengthCorrect(errors, password, "user.password")) {
            String repeatPassword = request.getParameter("userRepPassword");
            if (!password.equals(repeatPassword)) {
                String errorMessage = format(getMessage("error.passwords.notequal"));
                errors.add(errorMessage);
            }
        }

        if (isLoginExists(request.getParameter("userLogin"))) {
            String errorMessage = format(getMessage("error.login.exists"));
            errors.add(errorMessage);
        }

        validateUserStringField(errors, request.getParameter("adminCode"), "user.admincode");

        if (!ADMIN_CODE.equals(request.getParameter("adminCode"))) {
            String errorMessage = format(getMessage("error.admincode"));
            errors.add(errorMessage);
        }
        return errors;
    }

    /**
     * Checks the length of field value and its characters
     * consistence.
     * @param errors errors of user input strings
     * @param fieldName name of validated field
     * @param fieldValue value of validated field
     */
    private void validateUserStringField(final List<String> errors,
                                         final String fieldValue, final String fieldName) {
        isLengthCorrect(errors, fieldValue, fieldName);
        isConsistsOfLettersAndNumbers(errors, fieldValue, fieldName);
    }

     /**
     * Checks if the field value consists of letters
     * and numbers only and adds error messages to errors.
     * @param errors errors of user input strings
     * @param fieldName name of validated field
     * @param fieldValue value of validated field
     * @return if field value is correct
     */
    private boolean isConsistsOfLettersAndNumbers(final List<String> errors,
                                                  final String fieldValue, final String fieldName) {
        if (!fieldValue.matches("[0-9A-Za-z]+")) {
            String errorMessage = format(getMessage("error.field.letandnum"), getMessage(fieldName));
            errors.add(errorMessage);
            return false;
        }
        return true;
    }

    /**
     * Checks the length of field value and adds error messages
     * to errors.
     * @param errors errors of user input strings
     * @param fieldValue value of checked field
     * @param fieldName name of checked field
     * @return if field value is correct
     */
    private boolean isLengthCorrect(final List<String> errors, final String fieldValue, final String fieldName) {
        int fieldValueLength = fieldValue.length();
        if (fieldValueLength > MAX_CHARACTERS || fieldValueLength < MIN_CHARACTERS) {
            String errorMessage = format(getMessage("error.field.length"),
                    getMessage(fieldName), MIN_CHARACTERS, MAX_CHARACTERS);
            errors.add(errorMessage);
            return false;
        }
        return true;
    }

    /**
     * Checks the existence of such login in DB.
     * @param login login to check
     * @return if such login is already exists in DB.
     */
    private boolean isLoginExists(final String login) {
        List<SiteAdministrator> administrators = SiteAdministratorService.getInstance().getProfiles();
        for (SiteAdministrator admin : administrators) {
            if (admin.getLogin().equals(login)) {
                return true;
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
