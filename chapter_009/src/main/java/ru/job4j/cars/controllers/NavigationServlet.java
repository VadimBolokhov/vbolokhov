package ru.job4j.cars.controllers;

import ru.job4j.cars.models.Car;
import ru.job4j.cars.service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

/**
 * Navigation servlet.
 * @author Vadim Bolokhov
 */
public class NavigationServlet extends HttpServlet {

    private final CarsService service;

    private final Validate validate;

    private final Map<String, Supplier<String>> forwardMap = new HashMap<>();

    private final ThreadLocal<String> path = new ThreadLocal<>();

    private final ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();

    public NavigationServlet() {
        this(new ServiceFactoryImpl());
    }

    public NavigationServlet(ServiceFactory factory) {
        this.service = factory.getCarsService();
        this.validate = factory.getValidateService();
    }

    @Override
    public void init() {
        this.forwardMap.put("/cars/", () -> "/WEB-INF/views/cars/index.jsp");
        this.forwardMap.put("/cars/cardetails.jsp", this::getCarsDetailsPage);
        this.forwardMap.put("/cars/usercars.jsp", this::getUserCarsPage);
        this.forwardMap.put("/cars/editcar.jsp", this::getEditCarPage);
        this.forwardMap.put("/cars/addcar.jsp", this::getRequestedPage);
        this.forwardMap.put("/cars/edituser.jsp", this::getRequestedPage);
    }

    private String getCarsDetailsPage() {
        String carId = this.request.get().getParameter("carId");
        return this.getForwardPage(
                carId,
                "Car does not exist.",
                id -> this.service.getCarById(id).isPresent()
        );
    }

    private String getForwardPage(String entityId, String errorMessage, IntPredicate check) {
        boolean entityExists = entityId != null && check.test(Integer.parseInt(entityId));
        return entityExists ? this.getRequestedPage() : this.getErrorPage(errorMessage);
    }

    private String getRequestedPage() {
        return "/WEB-INF/views" + this.path.get();
    }

    private String getErrorPage(String message) {
        this.request.get().setAttribute("error", message);
        return "/WEB-INF/views/cars/errorpage.jsp";
    }

    private String getUserCarsPage() {
        String userId =  this.request.get().getParameter("userId");
        return this.getForwardPage(
                userId,
                "User does not exist.",
                id -> this.validate.findById(id).isPresent()
        );
    }

    private String getEditCarPage() {
        String carId = this.request.get().getParameter("carId");
        return this.getForwardPage(
                carId,
                "Car does not exist.",
                this::setRequestAttribute
        );
    }

    private boolean setRequestAttribute(int id) {
        boolean carExists = false;
        Optional<Car> carOptional = this.service.getCarById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            this.request.get().setAttribute("car", car);
            carExists = true;
        }
        return carExists;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String path = req.getServletPath();
            this.path.set(path);
            this.request.set(req);
            String forwardPage = this.forwardMap.getOrDefault(
                    path,
                    () -> this.getErrorPage("The page does not exits.")
            ).get();
            RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPage);
            dispatcher.forward(req, resp);
        } finally {
            this.request.set(null);
            this.path.set(null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
