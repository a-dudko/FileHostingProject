package by.bsu.fpmi.second.dudkoAA.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Servlet used for sign out the administrators. */
public class UserSignOutServlet extends HttpServlet {

    /**
     * Ends the session of administrator and signs out.
     * @param request with the attribute currentUser
     * @param response for the request
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(final HttpServletRequest request,
                           final HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("currentUser") != null) {
            request.getSession().invalidate();
        }
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/index.jsp"));
    }
}
