package kz.epam.servlet;

import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.message.MessageManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ImageUploadController extends HttpServlet {

    private static final String UPLOAD_DIR = "picture";
    private static final String CONTEXT_TYPE = "text/plain;charset=UTF-8";
    private static final String MIME_IMAGE_JPEG = "image/jpeg";
    private static final String MIME_IMAGE_PNG = "image/png";
    private static final String INCORRECT_FILE_TYPE = "error.image";
    private static final String INCORRECT_FILE_TYPE_MESSAGE = "imageError";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_TEXT = "There was an error: ";
    private static final String PATH_TO_PICTURE_UPLOAD = ConfigManager.getInstance().getProperty("path.page.upload.picture");
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
        String page = null;

        HttpSession session = request.getSession();
        String language = session.getAttribute(Constant.LOCALE).toString();
        Locale locale = new Locale(language.substring(0,2));
        response.setContentType(CONTEXT_TYPE);

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {

            FileItemFactory factory = new DiskFileItemFactory();
            ((DiskFileItemFactory) factory).setSizeThreshold(MEMORY_THRESHOLD);

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(MAX_REQUEST_SIZE);

            try {
                List<FileItem> items = upload.parseRequest(request);

                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();

                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        String contentType = item.getContentType();

                        if (contentType.equals(MIME_IMAGE_JPEG) || contentType.equals(MIME_IMAGE_PNG)) {
                            String root = getServletContext().getRealPath(File.separator);
                            File path = new File(root + File.separator + UPLOAD_DIR);

                            if (!path.exists()) {
                                path.mkdirs();
                            }
                            File uploadedFile = new File(path + File.separator + fileName);
                        item.write(uploadedFile);
                            page = PATH_TO_CONFIRMATION;
                        } else {

                            request.setAttribute(INCORRECT_FILE_TYPE_MESSAGE,
                                    MessageManager.getInstance(locale).getProperty(INCORRECT_FILE_TYPE));
                            page = PATH_TO_PICTURE_UPLOAD;
                        }

                    }
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                dispatcher.forward(request, response);

            } catch (Exception ex) {
                request.setAttribute(MESSAGE,
                        MESSAGE_TEXT + ex.getMessage());
            }

        }

    }
}

