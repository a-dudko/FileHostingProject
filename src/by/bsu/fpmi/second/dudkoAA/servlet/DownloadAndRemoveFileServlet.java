package by.bsu.fpmi.second.dudkoAA.servlet;

import by.bsu.fpmi.second.dudkoAA.FormItemsValidator;
import by.bsu.fpmi.second.dudkoAA.model.File;
import by.bsu.fpmi.second.dudkoAA.service.FileService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import static java.text.MessageFormat.format;

/** Servlet used for downloading and removing files. */
public class DownloadAndRemoveFileServlet extends HttpServlet {

    /** The size in bytes of buffer for downloading files. */
    private static final int BUFFER_SIZE = 2048;

    /** The code of successful response. */
    private static final int SUCCESS_RESPONSE_CODE = 200;

    /** The code of unsuccessful response. */
    private static final int FAILURE_RESPONSE_CODE = 500;

    /** The path to files on server. */
    private String filePath;

    /** Service for interaction with files storage. */
    private FileService fileService = new FileService();

    /** Checker for input fields. */
    private FormItemsValidator validator = new FormItemsValidator();

    /** Initializes the path to files on server. */
    public void init() {
        filePath = validator.getMessage("server.file.path");
    }

    /**
     * If file's id in request is correct, downloads
     * or tries to remove file from DB (depends on
     * attribute op).
     * @param request with file id, [optional] attributes op and removecode
     * @param response for the request
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response) throws ServletException, IOException {
        File fileFromDB = getFileFromDB(request.getRequestURI());
        if (fileFromDB == null) {
            wrongURIForward(request, response, format(getMessage("error.file.notinDB")));
        } else {
            if ("remove".equals(request.getParameter("op"))) {
                removeFile(request, response, fileFromDB);
            } else {
                downloadFile(response, fileFromDB);
            }
        }
    }

    /**
     * Removes given file from DB if the remove code is right.
     * @param request with the remove code from user for given file
     * @param response
     * @param fileFromDB to be removed
     * @throws IOException
     * @throws ServletException
     */
    private void removeFile(final HttpServletRequest request, final HttpServletResponse response,
                            final File fileFromDB) throws IOException, ServletException {
        if (isRemoveCodeRight(request, fileFromDB)) {
            fileService.removeFile(fileFromDB);
            response.setStatus(SUCCESS_RESPONSE_CODE);
            if (request.getParameter("ajax") == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/index.jsp"));
            }
        } else {
            response.setStatus(FAILURE_RESPONSE_CODE);
            wrongURIForward(request, response, format(getMessage("error.removecode.wrong")));
        }
    }

    /**
     * Checks if the remove code in request matches
     * the remove code for given file.
     * @param request with the remove code given by user
     * @param fileFromDB to be removed
     * @return is the remove code in request is equal
     * to the remove code for given file
     */
    private boolean isRemoveCodeRight(final HttpServletRequest request, final File fileFromDB) {
        String currentFileRemoveCode = fileFromDB.getRemoveCode().toString();
        String requestRemoveCode = request.getParameter("removecode");
        return currentFileRemoveCode.equals(requestRemoveCode);
    }

    /**
     * Forwards request and response to index page
     * with the message includes what is wrong in URI.
     * @param request
     * @param response
     * @param errorMessage describes the error in URI
     * @throws ServletException
     * @throws IOException
     */
    private void wrongURIForward(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final String errorMessage) throws ServletException, IOException {
        request.setAttribute("message", errorMessage);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Writes file content from file on server to response output
     * and updates response with content info.
     * @param response with the output stream to write file
     * @param fileFromDB on server to be downloaded
     * @throws IOException
     */
    private void downloadFile(final HttpServletResponse response, final File fileFromDB) throws IOException {
        java.io.File file = new java.io.File(filePath + java.io.File.separator + fileFromDB.getFileName());
        updateResponseWithContentInfo(response, file);
        DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
        ServletOutputStream outputStream = response.getOutputStream();
        downloadFileContent(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }

    /**
     * Get file from DB using id from request URI.
     * @param requestURI with ID of file to get.
     * @return file from DB with id from request URI or
     * null if there is no such file
     */
    private File getFileFromDB(final String requestURI) {
        String[] uriParts = requestURI.split("/");
        int i = 0;
        while (i < uriParts.length && !uriParts[i].matches("id[0-9]+")) {
            i++;
        }
        if (i == uriParts.length) {
            return null;
        }
        StringBuilder idPart = new StringBuilder(uriParts[i]);
        int fileID = Integer.parseInt(idPart.substring(2, idPart.length()));
        return fileService.getFile(fileID);
    }

    /**
     * Sets content info and header in the response.
     * @param response to be updated.
     * @param file the info of which will be in responce
     */
    private void updateResponseWithContentInfo(final HttpServletResponse response, final java.io.File file) {
        response.setContentType(getMimeType());
        response.setContentLength((int) file.length());
        String fileName = file.getName();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    }

    /**
     * Gets mime type by file path.
     * @return mime type by file path.
     */
    private String getMimeType() {
        String mimeType = getServletConfig().getServletContext().getMimeType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return mimeType;
    }

    /**
     * Writes all bytes of file from input to output stream.
     * @param inputStream of the source file
     * @param outputStream of the file to be saved
     * @throws IOException
     */
    private void downloadFileContent(final DataInputStream inputStream,
                                     final ServletOutputStream outputStream) throws IOException {
        byte[] byteBuffer = new byte[BUFFER_SIZE];
        int symbolsRead = inputStream.read(byteBuffer);
        while ((inputStream != null) && (symbolsRead  != -1)) {
            outputStream.write(byteBuffer, 0, symbolsRead);
            symbolsRead = inputStream.read(byteBuffer);
        }
        outputStream.flush();
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
