package by.bsu.fpmi.second.dudkoAA.servlet;

import by.bsu.fpmi.second.dudkoAA.model.File;
import by.bsu.fpmi.second.dudkoAA.service.FileBC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@MultipartConfig
public class UploadFileServlet extends HttpServlet {
    private final static int BUFFER_SIZE = 2048;

    private FileBC fileBC = new FileBC();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> messages = getRequestErrors(request);
        if (messages.size() != 0) {
            request.setAttribute("messages",messages);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/addFile.jsp");
            dispatcher.forward(request, response);
        }
        String fileName = createFile(request.getPart("file"));
        fileBC.addFile(makeFileObject(request, fileName));
        response.getOutputStream().println("<html><body>The file has been successfully uploaded</body></html>");
    }

    private String createFile(Part filePart) throws IOException, ServletException {
        String fileName = getFileName(filePart);
        InputStream inputStream = filePart.getInputStream();
        OutputStream outputStream = new FileOutputStream(fileName);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        loadFileContent(inputStream, bufferedOutputStream);
        bufferedOutputStream.close();
        return fileName;
    }

    private void loadFileContent(InputStream inputStream, BufferedOutputStream bufferedOutputStream) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = inputStream.read(buffer);
        while (bytesRead > 0) {
            bufferedOutputStream.write(buffer, 0, bytesRead);
            bytesRead = inputStream.read(buffer);
        }
        bufferedOutputStream.flush();
    }

    private String getFileName(Part part) {
        String[] contentAttributes = part.getHeader("content-disposition").split(";");
        for (String attribute : contentAttributes) {
            if (attribute.trim().startsWith("filename")) {
                String value = attribute.substring(attribute.indexOf('=') + 1).trim();
                return value.replace("\"", "");
            }
        }

        return null;
    }

    private File makeFileObject(HttpServletRequest request, String fileName) {
        File file = new File();
        file.setDescription(request.getParameter("fileDescription"));
        file.setAuthor(request.getParameter("fileAuthor"));
        file.setNotes(request.getParameter("fileNotes"));
        file.setFileName(fileName);
        file.setRemoveCode(Integer.toString(fileName.hashCode()));
        return file;
    }

    private List<String> getRequestErrors(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        if ("".equals(request.getParameter("fileDescription"))) {
            errors.add("Description field should not be empty");
        }
        if ("".equals(request.getParameter("fileAuthor"))) {
            errors.add("Author field should not be empty");
        }
        if ("".equals(request.getParameter("fileNotes"))) {
            errors.add("Notes field should not be empty");
        }
        if ("".equals(request.getParameter("file"))) {
            errors.add("File should be chosen");
        }
        return errors;
    }
}
