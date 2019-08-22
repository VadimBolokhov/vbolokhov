package ru.job4j.cars.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cars.models.*;
import ru.job4j.cars.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;

/**
 * Cars store servlet.
 * @author Vadim Bolokhov
 */
public class CarsServlet extends HttpServlet {
    /** Provides operations on Car objects */
    private final CarsService service;
    /** Provides operations on User objects */
    private final Validate usersService;
    /** Functions to be executed depending on a value of 'action' request parameter */
    private final Map<String, Function<HttpServletRequest, Object>> actions = new HashMap<>();
    /** Functions that set values to car fields */
    private final Map<String, BiConsumer<Car, String>> carSetters = new HashMap<>();
    /** Logger */
    private static final Logger LOG = LogManager.getLogger(CarsServlet.class.getName());

    public CarsServlet() {
        this(new ServiceFactoryImpl());
    }

    public CarsServlet(ServiceFactory factory) {
        this.service = factory.getCarsService();
        this.usersService = factory.getValidateService();
        this.initMaps();
    }

    private void initMaps() {
        this.actions.put("getMakes", r -> this.service.getMakes());
        this.actions.put("getModels", this::getModels);
        this.actions.put("getGearboxes", r -> this.service.getGearboxes());
        this.actions.put("getColors", r -> this.service.getColors());
        this.actions.put("getUnsoldCars", r -> this.service.getUnsoldCars());
        this.actions.put("getCarsList", r -> this.service.getAllCars());
        this.actions.put("getCar", this::getCarById);
        this.actions.put("getCarListByUser", this::getCarsByUserId);
        this.carSetters.put("price", (car, price) -> car.setPrice(Double.parseDouble(price)));
        this.carSetters.put("color", (car, color) -> car.setColor(Color.valueOf(color)));
        this.carSetters.put("odometer", (car, kms) -> car.setOdometer(Double.parseDouble(kms)));
        this.carSetters.put("gearbox", this::setGearbox);
        this.carSetters.put("model", this::setModel);
        this.carSetters.put("desc", Car::setDesc);
        this.carSetters.put("userId", this::setOwner);
        this.carSetters.put("carId", (car, id) -> car.setId(Integer.parseInt(id)));
        this.carSetters.put("sold", (car, isSold) -> car.setSold(Boolean.parseBoolean(isSold)));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        Function<HttpServletRequest, Object> function = this.actions.get(action);
        if (function != null) {
            resp.setContentType("application/json");
            Object result = function.apply(req);
            new ObjectMapper().writeValue(resp.getOutputStream(), result);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Object getModels(HttpServletRequest req) {
        String makeId = req.getParameter("makeId");
        List<Model> models = this.getEntity(makeId, LinkedList::new, this.service::getModelsByMake);
        return models.isEmpty() ? this.error("Incorrect make ID") : models;
    }

    private <T> T getEntity(String entityId, Supplier<T> expected, IntFunction<T> function) {
        T result = expected.get();
        if (entityId != null) {
            try {
                int id = Integer.parseInt(entityId);
                result = function.apply(id);
            } catch (NumberFormatException e) {
                LOG.warn(e.getMessage());
            }
        }
        return result;
    }

    private String error(String message) {
        return String.format("\"error\":\"%s\"", message);
    }

    private Object getCarById(HttpServletRequest req) {
        String carId = req.getParameter("id");
        Optional<Car> carOpt = this.getEntity(carId, Optional::empty, this.service::getCarById);
        return carOpt.isPresent() ? carOpt.get() : this.error("Car not found");
    }

    private Object getCarsByUserId(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        Optional<User> userOpt = this.getEntity(userId, Optional::empty, this.usersService::findById);
        return userOpt.isPresent() ? userOpt.get().getCars() : this.error("Incorrect user ID");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirect = req.getContextPath() + "/cars";
        String action = req.getParameter("action");
        if (ServletFileUpload.isMultipartContent(req)) {
            ServletFileUpload upload = this.getUploader();
            try {
                List<FileItem> items = upload.parseRequest(req);
                Car car = new Car();
                Queue<FileItem> images = new LinkedList<>();
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        this.carSetters.get(item.getFieldName()).accept(car, item.getString());
                    } else {
                        String filename = item.getName();
                        if (this.isImageFile(filename)) {
                            LOG.info("Received file: " + filename + ", size: " + item.getSize());
                            images.offer(item);
                        }
                    }
                }
                if (this.persistCar(action, car)) {
                    int carId = car.getId();
                    if (!images.isEmpty()) {
                        this.writeImages(carId, images);
                    }
                    redirect = req.getContextPath() + "/cars/cardetails.jsp?carId=" + carId;
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/cars/errorpage.jsp").forward(req, resp);
            }
        }
        resp.getWriter().write(String.format("{\"url\": \"%s\"}", redirect));
    }

    private ServletFileUpload getUploader() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        File repository = new File(System.getProperty("java.io.tmpdir"));
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024 * 1024);
        upload.setSizeMax(1024 * 1024 * 10);
        return upload;
    }

    private boolean persistCar(String action, Car car) {
        boolean success = false;
        if ("update".equals(action)) {
            this.service.update(car);
            success = true;
        } else {
            car.setPostDate(LocalDateTime.now());
            if (this.service.saveCar(car) > 0) {
                success = true;
            }
        }
        return success;
    }

    private void writeImages(int carId, Queue<FileItem> images) throws Exception {
        File uploadDir = this.createUploadDirectory(carId);
        long currentTimeMillis = System.currentTimeMillis();
        while (!images.isEmpty()) {
            FileItem item = images.poll();
            String fileName = String.valueOf(currentTimeMillis++);
            String storePath = uploadDir.getAbsolutePath()
                    + File.separator
                    + fileName + this.getExtension(item.getName());
            File store = new File(storePath);
            item.write(store);
        }
    }

    private File createUploadDirectory(int carId) {
        String uploadPath = new StringJoiner(File.separator)
                .add(this.getServletContext().getRealPath(""))
                .add("usersfiles")
                .add(String.valueOf(carId)).toString();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return uploadDir;
    }

    private boolean isImageFile(String filename) {
        return filename.endsWith(".jpg") || filename.endsWith(".png");
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }

    private void setGearbox(Car car, String id) {
        Gearbox box = new Gearbox();
        box.setId(Integer.parseInt(id));
        car.setGearbox(box);
    }

    private void setModel(Car car, String id) {
        Model model = new Model();
        model.setId(Integer.parseInt(id));
        car.setModel(model);
    }

    private void setOwner(Car car, String id) {
        User user = new User();
        user.setId(Integer.parseInt(id));
        car.setOwner(user);
    }
}
