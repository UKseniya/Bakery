package kz.epam.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "pictures";
    private static final String FILE_TO_UPLOAD = "image";
    private static final String PATH_TO_CONFIRMATION = "/jsp/admin/product_added.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;

        response.setContentType("text/plain;charset=UTF-8");

        // gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        // creates upload folder if it does not exists
        File uploadFolder = new File(uploadFilePath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        Part filePart = request.getPart(FILE_TO_UPLOAD);

        String fileName = filePart.getSubmittedFileName();
        InputStream is = filePart.getInputStream();

        Files.copy(is, Paths.get(uploadFilePath + fileName),
                StandardCopyOption.REPLACE_EXISTING);

        // write all files in upload folder
//            if (filePart != null) {
//                String fileName = filePart.getSubmittedFileName();
//                String contentType = filePart.getContentType();
//
//                // allows only JPG files to be uploaded
//                if (!contentType.equalsIgnoreCase("image/jpg")) {
//                    return;
//                }
//
//                filePart.write(uploadFilePath + File.separator + fileName);
//        }
        page = PATH_TO_CONFIRMATION;

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}

