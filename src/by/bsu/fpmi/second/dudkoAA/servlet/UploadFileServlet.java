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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@MultipartConfig
public class UploadFileServlet extends HttpServlet {
    private final static int BUFFER_SIZE = 2048;

    private final static int MAX_CHARACTERS = 300;

    private final static int MIN_CHARACTERS = 1;

    private String filePath;

    public void init() {
        filePath = "C:" + java.io.File.separator + "Temp";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("upload")) {
            uploadFile(request, response);
        } else {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/index.jsp"));
        }
    }

    private void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> messages = getRequestErrors(request);
        String jspToForward;
        if (!messages.isEmpty()){
            request.setAttribute("messages", messages);
            jspToForward = "/addFile.jsp";
        }
        else {
            File file = makeFileObject(request, createFileOnServer(request.getPart("file")));
            FileBC.getInstance().addFile(file);
            updateRequestWithInfoForLinks(request, file);
            jspToForward = "/successfulUpload.jsp";
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspToForward);
        dispatcher.forward(request, response);
    }

    private String createFileOnServer(Part filePart) throws IOException, ServletException {
        InputStream inputStream = filePart.getInputStream();
        String fileName = getFileName(filePart);
        java.io.File file = new java.io.File(filePath + java.io.File.separator + fileName);
        file.getParentFile().mkdirs();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
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

    private void updateRequestWithInfoForLinks(HttpServletRequest request, File file) {
        request.setAttribute("fileID", file.getId());
        request.setAttribute("removeCode", file.getRemoveCode());
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
        validateFileStringField(request.getParameter("fileDescription"), errors, "file.description");
        validateFileStringField(request.getParameter("fileAuthor"), errors, "file.author");
        validateFileStringField(request.getParameter("fileNotes"), errors, "file.notes");
        if (request.getParameter("file") == null) {
            errors.add("File should be chosen");
        }
        return errors;
    }

    /**
     * validate value and add error messages to messages list
     * @param value the value to validate
     * @param errors the messages list
     * @param fieldName the key of field name in properties file
     */
    private void validateFileStringField(String value, List<String> errors, String fieldName) {
        if (value == null || value.trim().equals("")) {
            String errorMessage = MessageFormat.format(getMessage("error.empty"), getMessage(fieldName));
            errors.add(errorMessage);
        } else if (value.length() > MAX_CHARACTERS) {
            String errorMessage = MessageFormat.format(getMessage("error.field.length"),
                    getMessage(fieldName), MIN_CHARACTERS, MAX_CHARACTERS);
            errors.add(errorMessage);
        }
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
