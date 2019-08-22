package ru.job4j.cars.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Resource management servlet. Provides operations on cars images.
 * @author Vadim Bolokhov
 */
public class ResourcesManagement extends HttpServlet {

    /** Logger */
    private static final Logger LOG = LogManager.getLogger(ResourcesManagement.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String carId = req.getParameter("carId");
        String action = req.getParameter("action");
        if (carId != null) {
            if ("getImages".equals(action)) {
                this.writeFileNames(resp, carId);
            } else if ("getImage".equals(action)) {
                String imageId = req.getParameter("image");
                String extension = imageId.substring(imageId.lastIndexOf('.'));
                resp.setContentType(extension.equals(".jpg") ? "image/jpeg" : "image/png");
                FileInputStream image = new FileInputStream(this.buildPath(carId, imageId));
                try (BufferedInputStream in = new BufferedInputStream(image);
                     BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream())) {
                    for (int ch = in.read(); ch != -1; ch = in.read()) {
                        out.write(ch);
                    }
                }
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/cars");
        }
    }

    private void writeFileNames(HttpServletResponse resp, String carId) throws IOException {
        resp.setContentType("application/json");
        File directory = new File(this.buildPath(carId));
        File[] files = directory.listFiles();
        if (files != null) {
            List<String> fileNames = Arrays.stream(files)
                    .map(File::getName)
                    .collect(Collectors.toList());
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getOutputStream(), fileNames);
        } else {
            resp.getWriter().write("{\"error\":\"Files not found\"}");
        }
    }

    private String buildPath(String... strings) {
        StringJoiner joiner = new StringJoiner(File.separator)
                .add(this.getServletContext().getRealPath(""))
                .add("usersfiles");
        Stream.of(strings).forEach(joiner::add);
        return joiner.toString();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String carId = req.getParameter("carId");
        String image = req.getParameter("image");
        if ("delete".equals(action) && carId != null && image != null) {
            File file = new File(this.buildPath(carId, image));
            file.delete();
            this.writeFileNames(resp, carId);
        }
    }
}
