package by.bsu.fpmi.second.dudkoAA.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 55
 * Date: 22.5.13
 * Time: 1.02
 */
public class FileAddingServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/addFile.jsp");
        reqDispatcher.forward(request, response);
    }
}
