package by.bsu.fpmi.second.dudkoAA.servlet;



import by.bsu.fpmi.second.dudkoAA.service.FileBC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class DownloadFileServlet extends HttpServlet {

    private final static int BUFFER_SIZE = 2048;

    private String filePath;

    private FileBC fileBC = new FileBC();

    public void init() {
        filePath = "C:" + java.io.File.separator + "Temp";
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        by.bsu.fpmi.second.dudkoAA.model.File fileFromDB = getFileFromDB(request.getRequestURI());
        if (fileFromDB == null) {
            request.setAttribute("message", "Not valid URI");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/notFound.jsp");
            dispatcher.forward(request, response);
        }
        else {
            File file = new File(filePath + File.separator + fileFromDB.getFileName());
            updateResponseParams(response, file);
            downloadFile(response, file);
        }
    }

    private void downloadFile(HttpServletResponse response, File file) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        ServletOutputStream outStream = response.getOutputStream();
        downloadFileContent(in, outStream);
        in.close();
        outStream.close();
    }

    private by.bsu.fpmi.second.dudkoAA.model.File getFileFromDB(String requestURI) {
        String[] URIParts = requestURI.split("/");
        int i = 0;
        while (!URIParts[i].matches("id[0-9]*") && ++i < URIParts.length) {

        }
        if (i == URIParts.length) {
            return null;
        }
        StringBuilder IDPart = new StringBuilder(URIParts[i]);
        int fileID = Integer.parseInt(IDPart.substring(2, IDPart.length()));
        return fileBC.getFile(fileID);
    }

    private void updateResponseParams(HttpServletResponse response, java.io.File file) {
        response.setContentType(getMimeType());
        response.setContentLength((int)file.length());
        String fileName = file.getName();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    }

    private String getMimeType() {
        String mimeType = getServletConfig().getServletContext().getMimeType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return mimeType;
    }

    private void downloadFileContent(DataInputStream inputStream, ServletOutputStream outputStream) throws IOException {
        byte[] byteBuffer = new byte[BUFFER_SIZE];
        int symbolsRead;
        while ((inputStream != null) && ((symbolsRead = inputStream.read(byteBuffer)) != -1))
        {
            outputStream.write(byteBuffer, 0, symbolsRead);
        }
        outputStream.flush();
    }
}
