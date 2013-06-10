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

/** Servlet used for registering the administrators. */
public class UserRegistrationServlet extends HttpServlet {

    /** Code given for users to verify their rights for registration. */
    private static final String ADMIN_CODE = "LIDA1323";

    /** Checker for input fields. */
    private FormItemsValidator validator = new FormItemsValidator();

    /** Service for interaction with files storage. */
    private SiteAdministratorService administratorService = new SiteAdministratorService();

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
            administratorService.addAdministrator(administrator);
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
            administrator.setPassword(Encryptor.getStringMD5(password));
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
        validator.validateUserStringField(errors, request.getParameter("userLogin"), "user.login");

        String password = request.getParameter("userPassword");
        if (validator.isLengthCorrect(errors, password, "user.password")) {
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

        validator.validateUserStringField(errors, request.getParameter("adminCode"), "user.admincode");

        if (!ADMIN_CODE.equals(request.getParameter("adminCode"))) {
            String errorMessage = format(getMessage("error.admincode"));
            errors.add(errorMessage);
        }
        return errors;
    }



    /**
     * Checks the existence of such login in DB.
     * @param login login to check
     * @return if such login is already exists in DB.
     */
    private boolean isLoginExists(final String login) {
        List<SiteAdministrator> administrators = administratorService.getProfiles();
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
