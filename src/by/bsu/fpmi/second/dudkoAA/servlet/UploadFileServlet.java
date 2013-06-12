package by.bsu.fpmi.second.dudkoAA.servlet;

import by.bsu.fpmi.second.dudkoAA.FormItemsValidator;
import by.bsu.fpmi.second.dudkoAA.Messager;
import by.bsu.fpmi.second.dudkoAA.model.File;
import by.bsu.fpmi.second.dudkoAA.service.FileService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.text.MessageFormat.format;

/**
 * Servlet using for uploading files.
 */
@MultipartConfig
public class UploadFileServlet extends HttpServlet {

    /** The size in bytes of buffer for uploading files. */
    private static final int BUFFER_SIZE = 2048;

    /** The number of max bytes file size. */
    private static final int MAX_FILE_SIZE = 50000000;

    /** The path to upload files on server. */
    private String filePath;

    /** Service for interaction with files storage. */
    private FileService fileService = new FileService();

    /** Checker for input fields. */
    private FormItemsValidator validator = new FormItemsValidator();

    /** Initializes the path to files on server. */
    public void init() {
        filePath = Messager.getMessage("server.file.path");
    }

    /**
     * Checks the action in the form was chosen:
     * upload or cancel - and follows it.
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
        if (action.equals("upload")) {
            uploadFile(request, response);
        } else {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/index.jsp"));
        }
    }

    /**
     * Checks if the request is correct and uploads file to the server.
     * @param request request with the info about file to upload
     * @param response response to the request
     * @throws IOException
     * @throws ServletException
     */
    private void uploadFile(final HttpServletRequest request,
                            final HttpServletResponse response) throws IOException, ServletException {
        List<String> messages = getRequestErrors(request);
        String jspToForward;
        if (!messages.isEmpty()) {
            request.setAttribute("messages", messages);
            jspToForward = "/addFile.jsp";
        } else {
            File file = makeFileObject(request, createFileOnServer(request.getPart("file")));
            fileService.addFile(file);
            updateRequestWithInfoForLinks(request, file);
            jspToForward = "/successfulUpload.jsp";
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspToForward);
        dispatcher.forward(request, response);
    }

    /**
     * Creates and loads file to the server.
     * @param filePart a part file of request
     * @return the name of uploaded file
     * @throws IOException
     * @throws ServletException
     */
    private String createFileOnServer(final Part filePart) throws IOException, ServletException {
        InputStream inputStream = filePart.getInputStream();
        String fileName = getFileName(filePart);
        java.io.File file = new java.io.File(filePath + java.io.File.separator + fileName);
        file.getParentFile().mkdirs();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        loadFileContent(inputStream, bufferedOutputStream);
        bufferedOutputStream.close();
        return fileName;
    }

    /**
     * Loads the content of file from input to output stream.
     * @param inputStream input stream from the source of file
     * @param bufferedOutputStream output stream for uploading file
     * @throws IOException
     */
    private void loadFileContent(final InputStream inputStream,
                                 final BufferedOutputStream bufferedOutputStream) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = inputStream.read(buffer);
        while (bytesRead > 0) {
            bufferedOutputStream.write(buffer, 0, bytesRead);
            bytesRead = inputStream.read(buffer);
        }
        bufferedOutputStream.flush();
    }

    /**
     * Gets the name of file form its part in request.
     * @param part file part of request
     * @return the name of uploading file
     */
    private String getFileName(final Part part) {
        String[] contentAttributes = part.getHeader("content-disposition").split(";");
        for (String attribute : contentAttributes) {
            if (attribute.trim().startsWith("filename")) {
                String value = attribute.substring(attribute.indexOf('=') + 1).trim();
                return value.replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Adds attributes to the request
     * @param request request to be updated
     * @param file file with the info for the links
     */
    private void updateRequestWithInfoForLinks(HttpServletRequest request, File file) {
        request.setAttribute("fileID", file.getId());
        request.setAttribute("removeCode", file.getRemoveCode());
    }

    /**
     * Creates File object according to the information in request.
     * @param request request with uploading file information
     * @return file object
     */
    private File makeFileObject(final HttpServletRequest request, final String fileName) {
        File file = new File();
        file.setDescription(request.getParameter("fileDescription"));
        file.setAuthor(request.getParameter("fileAuthor"));
        file.setNotes(request.getParameter("fileNotes"));
        file.setFileName(fileName);
        file.setRemoveCode(Integer.toString(fileName.hashCode()));
        return file;
    }

    /**
     * Checks the information about the file in request and adds
     * error messages to errors.
     * @param request request with entered by user file information
     * @return the list of error messages
     */
    private List<String> getRequestErrors(final HttpServletRequest request) throws IOException, ServletException {
        List<String> errors = new ArrayList<>();
        validator.validateFileStringField(request.getParameter("fileDescription"), errors, "file.description");
        validator.validateFileStringField(request.getParameter("fileAuthor"), errors, "file.author");
        validator.validateFileStringField(request.getParameter("fileNotes"), errors, "file.notes");
        Part filePart = request.getPart("file");
        if (filePart == null) {
            errors.add(format(Messager.getMessage("error.file.notchosen")));
        } else if (filePart.getSize() > MAX_FILE_SIZE) {
            errors.add(format(Messager.getMessage("error.file.size")));
        }
        return errors;
    }
}
