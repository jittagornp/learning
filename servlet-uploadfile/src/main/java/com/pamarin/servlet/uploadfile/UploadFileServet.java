/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pamarin.servlet.uploadfile;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author anonymous
 */
@WebServlet(urlPatterns = "/uploadfile")
public class UploadFileServet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(UploadFileServet.class.getName());

    private final int maxFileSize = 50 * 1024;
    private final int maxMemSize = 4 * 1024;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.log(Level.INFO, "uploaded");
        
        try {
            if (!ServletFileUpload.isMultipartContent(request)) {
                return;
            }

            LOG.log(Level.INFO, "is multipart");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(maxMemSize);
            factory.setRepository(new File("c:\\temp"));
            //
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(maxFileSize);

            List<FileItem> fileItems = upload.parseRequest(request);
            Iterator<FileItem> iterator = fileItems.iterator();
            
            LOG.log(Level.INFO, "file size --> {0}", fileItems.size()); 
            while (iterator.hasNext()) {
                FileItem item = iterator.next(); 
                //if (!item.isFormField()) {
                    LOG.log(Level.INFO, "content type --> {0}", item.getContentType());
                    LOG.log(Level.INFO, "name --> {0}", item.getName());
                    LOG.log(Level.INFO, "field name --> {0}", item.getFieldName());
                    LOG.log(Level.INFO, "string --> {0}", item.getString());
                    
                    item.write(new File("c:\\temp", UUID.randomUUID().toString() + ".png"));
                //}
            }
        } catch (FileUploadException ex) {
            LOG.log(Level.WARNING, ex.getMessage());
        } catch (Exception ex) {
            LOG.log(Level.WARNING, ex.getMessage());
        }
    }

}
