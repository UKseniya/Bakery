package kz.epam.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class ImageUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "pictures";
    private static final String FILE_TO_UPLOAD = "file";
    private static final String PATH_TO_CONFIRMATION = "/jsp/admin/product_added.jsp";

    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

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
//        String applicationPath = request.getServletContext().getRealPath("");
//        // constructs path of the directory to save uploaded file
//        String uploadFilePath = applicationPath + UPLOAD_DIR;
//        // creates upload folder if it does not exists
//        File uploadFolder = new File(uploadFilePath);
//        if (!uploadFolder.exists()) {
//            uploadFolder.mkdirs();
//        }

//        Part filePart = request.getPart(FILE_TO_UPLOAD);
//
//        String fileName = filePart.getSubmittedFileName();
//        InputStream is = filePart.getInputStream();
//
//        Files.copy(is, Paths.get(uploadFilePath + fileName),
//                StandardCopyOption.REPLACE_EXISTING);

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
        // another code to try
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (!item.isFormField()) {
                        String fileName = item.getName();

                        String root = getServletContext().getRealPath("/");
                        File path = new File(root + UPLOAD_DIR);
                        if (!path.exists()) {
                            boolean status = path.mkdirs();
                        }

                        File uploadedFile = new File(path + "/" + fileName);
                        item.write(uploadedFile);
                    }
                    page = PATH_TO_CONFIRMATION;

                    RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                    dispatcher.forward(request, response);
                }

            } catch (Exception ex) {
                request.setAttribute("message",
                        "There was an error: " + ex.getMessage());
            }

        }

    }
}

