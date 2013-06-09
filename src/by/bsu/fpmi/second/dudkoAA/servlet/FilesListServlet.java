package by.bsu.fpmi.second.dudkoAA.servlet;

import by.bsu.fpmi.second.dudkoAA.dao.FileDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used for displaying list of files for site administrators.
 */
public class FilesListServlet extends HttpServlet {

    /**
     * If current user is administrator, the list of files
     * on server is shown, for the usual user this page
     * doesn't exist.
     * @param request with the attribute currentUser
     *                if he is administrator
     * @param response for the request
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(final HttpServletRequest request,
                           final HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("currentUser") != null) {
            request.setAttribute("filesList", FileDAO.getInstance().readAll());
            RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/filesList.jsp");
            reqDispatcher.forward(request, response);
        } else {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/notFound.jsp"));
        }
    }
}
