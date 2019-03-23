package kz.epam.servlet;

import kz.epam.config.ConfigManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ImageUploadController extends HttpServlet {

    private static final String UPLOAD_DIR = "picture";
    private static final String CONTEXT_TYPE = "text/plain;charset=UTF-8";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_TEXT = "There was an error: ";
    private static final String PATH_TO_CONFIRMATION = ConfigManager.getInstance().getProperty("path.page.product.added.confirmation");

    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page;

        response.setContentType(CONTEXT_TYPE);

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            // configures upload settings
            FileItemFactory factory = new DiskFileItemFactory();
            // sets memory threshold - beyond which files are stored in disk
            ((DiskFileItemFactory) factory).setSizeThreshold(MEMORY_THRESHOLD);

            ServletFileUpload upload = new ServletFileUpload(factory);

            // sets maximum size of upload file
            upload.setFileSizeMax(MAX_FILE_SIZE);

            // sets maximum size of request (include file + form data)
            upload.setSizeMax(MAX_REQUEST_SIZE);

            try {
                // parses the request's content to extract file data
                List items = upload.parseRequest(request);

                // iterates over form's fields
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                        String fileName = item.getName();

                        String root = getServletContext().getRealPath(File.separator);
                        File path = new File(root + UPLOAD_DIR);

                        // creates the directory if it does not exist
                        if (!path.exists()) {
                            path.mkdirs();
                        }

                        // constructs the directory path to store upload file
                        // this path is relative to application's directory
                        File uploadedFile = new File(path + File.separator + fileName);

                        // saves the file on disk
                        item.write(uploadedFile);
                    }
                }
                page = PATH_TO_CONFIRMATION;

                RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                dispatcher.forward(request, response);

            } catch (Exception ex) {
                request.setAttribute(MESSAGE,
                        MESSAGE_TEXT + ex.getMessage());
            }

        }

    }
}

