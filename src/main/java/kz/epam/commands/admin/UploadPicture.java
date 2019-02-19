package kz.epam.commands.admin;

import kz.epam.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class UploadPicture implements Command {
    private static final String UPLOAD_DIR = "pictures";
    private static final String PATH_TO_CONFIRMATION = "/jsp/admin/product_added.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        // gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        // creates upload folder if it does not exists
        File uploadFolder = new File(uploadFilePath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // write all files in upload folder
        try {
            for (Part part : request.getParts()) {
                if (part != null && part.getSize() > 0) {
                    String fileName = part.getSubmittedFileName();
                    String contentType = part.getContentType();

                    // allows only JPG files to be uploaded
                    if (!contentType.equalsIgnoreCase("image/jpg")) {
                        continue;
                    }

                    part.write(uploadFilePath + File.separator + fileName);

                    page = PATH_TO_CONFIRMATION;

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return page;
    }
    }

