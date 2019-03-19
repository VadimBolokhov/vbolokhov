package ru.job4j.cinema.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.cinema.service.CinemaService;
import ru.job4j.cinema.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * HallServlet test.
 * @author Vadim Bolokhov
 */
@Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest(CinemaService.class)
@PowerMockIgnore({"javax.management.*"})
public class HallServletTest {
    private Service service = new ServiceStub();
    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Before
    public void initHall() {
        this.service.initHall(2, 2);
    }

    @Before
    public void initMocks() throws IOException {
        PowerMockito.mockStatic(CinemaService.class);
        when(CinemaService.getInstance()).thenReturn(this.service);
        this.req = mock(HttpServletRequest.class);
        this.resp = mock(HttpServletResponse.class);
        this.mockPrintWriter();
    }

    private void mockPrintWriter() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(this.resp.getWriter()).thenReturn(printWriter);
    }

    @Test
    public void whenReserveSeatThenSeatIsReserved() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(this.req.getReader()).thenReturn(reader);
        when(reader.readLine())
                .thenReturn("{\"id\":101,\"username\":\"a\", \"phone\":\"123\"}")
                .thenReturn(null);

        new HallServlet().doPost(this.req, this.resp);

        assertTrue(this.service.findSeatById(101).get().isReserved());
    }

    @Test
    public void whenRequestPriceThenReturn500() throws IOException {
        this.service.findSeatById(101).get().setPrice(500d);
        when(this.req.getParameter("getPrice")).thenReturn("getPrice");
        when(this.req.getParameter("getPrice")).thenReturn("101");

        new HallServlet().doGet(this.req, this.resp);
        double result = this.service.findSeatById(101).get().getPrice();

        assertThat(result, is(500d));
    }
}