package ru.job4j.cars.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.cars.models.*;
import ru.job4j.cars.service.CarsService;
import ru.job4j.cars.service.ServiceFactory;
import ru.job4j.cars.service.Validate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Tests for CarsServlet.
 * @author Vadim Bolokhov
 */
@Ignore
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*"})
public class CarsServletTest {

    private CarsService carsService;

    private Validate validate;

    private HttpServletRequest req;

    private HttpServletResponse resp;

    private ServiceFactory factory;

    private ServletOutputStream out;

    @Before
    public void setUp() throws IOException {
        this.carsService = mock(CarsService.class);
        this.validate = mock(Validate.class);
        this.factory = new ServiceFactoryStub()
                .setCarsService(this.carsService)
                .setValidateService(this.validate);
        this.req = mock(HttpServletRequest.class);
        this.resp = mock(HttpServletResponse.class);
        this.out = mock(ServletOutputStream.class);
        when(this.resp.getOutputStream()).thenReturn(this.out);

    }

    @Test
    public void whenDoGetWithGetAllCarsThenShouldInvokeGelAllCarsMethod() throws IOException {
        when(this.req.getParameter("action")).thenReturn("getCarsList");

        new CarsServlet(this.factory).doGet(this.req, this.resp);

        verify(this.carsService, atLeast(1)).getAllCars();
    }

    @Test
    public void whenDoGetWithGetUnsoldCarsThenGetUnsoldCarsMethodShouldBeInvoked() throws IOException {
        when(this.req.getParameter("action")).thenReturn("getUnsoldCars");

        new CarsServlet(this.factory).doGet(this.req, this.resp);

        verify(this.carsService, atLeast(1)).getUnsoldCars();
    }

    @Test
    public void whenDoGetWithGetCarThenGetCarByIdMethodShouldBeInvoked() throws IOException {
        when(this.req.getParameter("action")).thenReturn("getCar");
        when(this.req.getParameter("id")).thenReturn("123");
        when(this.carsService.getCarById(123)).thenReturn(Optional.empty());

        new CarsServlet(this.factory).doGet(this.req, this.resp);

        verify(this.carsService).getCarById(123);
    }

    @Test
    public void whenDoGetWithGetMakesThenGetMakesMethodShouldBeInvoked() throws IOException {
        when(this.req.getParameter("action")).thenReturn("getMakes");

        new CarsServlet(this.factory).doGet(this.req, this.resp);

        verify(this.carsService, atLeast(1)).getMakes();
    }

    @Test
    public void whenDoGetWithGetModelsThenGetModelsByMakeShouldBeInvoked() throws IOException {
        when(this.req.getParameter("action")).thenReturn("getModels");
        when(this.req.getParameter("makeId")).thenReturn("123");

        new CarsServlet(this.factory).doGet(this.req, this.resp);

        verify(this.carsService).getModelsByMake(123);
    }

    @Test
    public void whenDoGetWithGetGearboxesThenGetGearboxesMethodShouldBeInvoked() throws IOException {
        when(this.req.getParameter("action")).thenReturn("getGearboxes");

        new CarsServlet(this.factory).doGet(this.req, this.resp);

        verify(this.carsService, atLeast(1)).getGearboxes();
    }

    @Test
    public void whenDoGetWithGetColorsThenGetColorsMethodShouldBeInvoked() throws IOException {
        when(this.req.getParameter("action")).thenReturn("getColors");

        new CarsServlet(this.factory).doGet(this.req, this.resp);

        verify(this.carsService, atLeast(1)).getColors();
    }

    @Test
    public void whenDoGetWithGetCarListByUserThenFindByIdMethodShouldBeInvoked() throws IOException {
        when(this.req.getParameter("action")).thenReturn("getCarListByUser");
        when(this.req.getParameter("userId")).thenReturn("123");
        User user = mock(User.class);
        when(this.validate.findById(123)).thenReturn(Optional.of(user));

        new CarsServlet(this.factory).doGet(this.req, this.resp);

        verify(this.validate, atLeast(1)).findById(123);
        verify(user, atLeast(1)).getCars();
    }
}