package ru.job4j.crud.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.crud.models.Role;
import ru.job4j.crud.models.User;
import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;

/**
 * UsersServlet Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class UsersServletTest {
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();
    private Validate validate = new ValidateStub();
    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Before
    public void initMocks() throws IOException {
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(this.validate);
        this.req = mock(HttpServletRequest.class);
        this.resp = mock(HttpServletResponse.class);
        this.mockRequestDispatcher();
        this.mockPrintWriter();
    }

    private void mockRequestDispatcher() {
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(this.req.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    private void mockPrintWriter() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(this.resp.getWriter()).thenReturn(printWriter);
    }

    @Test
    public void whenAddUserThenStoreIt() throws IOException {
        when(this.req.getParameter("login")).thenReturn("root");
        when(this.req.getParameter("name")).thenReturn("root");
        when(this.req.getParameter("role")).thenReturn("ADMIN");
        new UserCreateServlet().doPost(this.req, this.resp);
        assertThat(this.validate.findAll().get(0).getName(), is("root"));
    }

    @Test
    public void whenUpdateUserThenStoreNewInfo() throws ServletException, IOException {
        this.validate.add(new User.Builder().name("root").role(Role.ADMIN).build());
        when(this.req.getParameter("id")).thenReturn("0");
        when(this.req.getParameter("name")).thenReturn("admin");
        when(this.req.getParameter("role")).thenReturn("ADMIN");
        new UserUpdateServlet().doPost(this.req, this.resp);
        String result = this.validate.findAll().get(0).getName();
        assertThat(result, is("admin"));
    }

    @Test
    public void whenDeleteUserThenStoreHasNoSameUser() throws ServletException, IOException {
        this.validate.add(new User.Builder().name("root").role(Role.ADMIN).build());
        when(this.req.getParameter("id")).thenReturn("0");
        when(this.req.getParameter("action")).thenReturn("delete");
        new UsersServlet().doPost(this.req, this.resp);
        assertThat(validate.findAll().isEmpty(), is(true));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenFindAllThenReturnsUserList() throws ServletException, IOException {
        this.validate.add(new User.Builder().name("root").role(Role.ADMIN).build());
        doAnswer(invocationOnMock -> {
            String key = invocationOnMock.getArgumentAt(0, String.class);
            Object value = invocationOnMock.getArgumentAt(1, Object.class);
            attributes.put(key, value);
            return null;
        }).when(this.req).setAttribute(anyString(), anyObject());
        new UsersServlet().doGet(this.req, this.resp);
        Object attribute = this.attributes.get("users");
        List<User> users = (List<User>) attribute;
        assertThat(users.get(0).getName(), is("root"));
    }
}