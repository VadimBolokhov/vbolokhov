package ru.job4j.cars.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.cars.models.*;
import ru.job4j.cars.service.CarsService;
import ru.job4j.cars.service.ServiceFactory;
import ru.job4j.cars.service.Validate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Tests for NavigationServlet.
 * @author Vadim Bolokhov
 */
@Ignore
@RunWith(PowerMockRunner.class)
public class NavigationServletTest {

    private CarsService carsService;

    private Validate validate;

    private ServiceFactory factory;

    private HttpServletRequest req;

    private HttpServletResponse resp;

    private RequestDispatcher dispatcher;

    @Before
    public void setUp() {
        this.carsService = CarsServiceStub.INSTANCE;
        this.validate = ValidateServiceStub.INSTANCE;
        this.factory = new ServiceFactoryStub()
                .setValidateService(this.validate)
                .setCarsService(this.carsService);
        this.req = mock(HttpServletRequest.class);
        this.resp = mock(HttpServletResponse.class);
        this.dispatcher = mock(RequestDispatcher.class);
        when(this.req.getRequestDispatcher(anyString())).thenReturn(this.dispatcher);
    }

    @Test
    public void whenRequestedPageDoesNotExistThenShouldForwardToTheErrorPage() throws ServletException, IOException {
        when(this.req.getServletPath()).thenReturn("doesnotexist");

        this.assertDoGetSuccess("errorpage.jsp");
    }

    @Test
    public void whenIncorrectCarIdThenShouldForwardToTheErrorPage() throws ServletException, IOException {
        this.setRequestParameters("/cars/cardetails.jsp", "carId", 123);

        this.assertDoGetSuccess("errorpage.jsp");
    }

    private void setRequestParameters(String page, String idString, int entityId) {
        when(this.req.getServletPath()).thenReturn(page);
        when(this.req.getParameter(idString)).thenReturn(String.valueOf(entityId));
    }

    @Test
    public void whenRequestForIndexPageThenShouldInvokeDispatcherWithIndex() throws ServletException, IOException {
        when(this.req.getServletPath()).thenReturn("/cars/");

        this.assertDoGetSuccess("index.jsp");
    }

    private void assertDoGetSuccess(String page) throws ServletException, IOException {
        NavigationServlet servlet = new NavigationServlet(this.factory);
        servlet.init();
        servlet.doGet(this.req, this.resp);

        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
        verify(req).getRequestDispatcher(arg.capture());
        verify(dispatcher).forward(this.req, this.resp);

        assertTrue(arg.getValue().contains(page));
    }

    @Test
    public void whenRequestForCarDetailsPageThenShouldInvokeDispatcherWithCarDetails() throws ServletException, IOException {
        Car car = new DummyCarFactory().getCar();
        int id = this.carsService.saveCar(car);
        String page = "/cars/cardetails.jsp";
        this.setRequestParameters(page, "carId", id);

        this.assertDoGetSuccess(page);
    }

    @Test
    public void whenRequestForUserCarsThenShouldInvokeDispatcherWithUserCars() throws ServletException, IOException {
        User user = new DummyUserFactory().getUser();
        this.validate.add(user);
        String page = "/cars/usercars.jsp";
        this.setRequestParameters(page, "userId", user.getId());

        this.assertDoGetSuccess(page);
    }

    @Test
    public void whenRequestForEditCarPageThenShouldInvokeRequestDispatcherWithEditCar()
            throws ServletException, IOException {
        Car car = new DummyCarFactory().getCar();
        int id = this.carsService.saveCar(car);
        String page = "/cars/editcar.jsp";
        this.setRequestParameters(page, "carId", id);

        this.assertDoGetSuccess(page);
    }

    @Test
    public void whenRequestForAddCarPageThenShouldInvokeDispatcherWithAddCar() throws ServletException, IOException {
        Car car = new DummyCarFactory().getCar();
        int id = this.carsService.saveCar(car);
        String page = "/cars/addcar.jsp";
        this.setRequestParameters(page, "carId", id);

        this.assertDoGetSuccess(page);
    }

    @Test
    public void whenRequestForEditUserPageThenShouldInvokeDispatcherWithEditUser() throws ServletException, IOException {
        User user = new DummyUserFactory().getUser();
        this.validate.add(user);
        String page = "/cars/edituser.jsp";
        this.setRequestParameters(page, "userId", user.getId());

        this.assertDoGetSuccess(page);
    }
}