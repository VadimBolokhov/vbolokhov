package ru.job4j.cars.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cars.models.Role;
import ru.job4j.cars.models.User;
import ru.job4j.cars.service.Validate;
import ru.job4j.cars.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Users management servlet.
 * @author Vadim Bolokhov
 */
public class UsersServlet extends HttpServlet {

    /** Logger */
    private static final Logger LOG = LogManager.getLogger(UsersServlet.class.getName());

    private static final String RESPONSE_SUCCESS = "{\"resonse\":\"success\"}";

    private final Validate validate = ValidateService.INSTANCE;

    private final Map<String, Function<HttpServletRequest, String>> doPostActions = new HashMap<>();


    @Override
    public void init() throws ServletException {
        this.doPostActions.put("update", this::updateUser);
        this.doPostActions.put("changePassword", this::changePassword);
        this.doPostActions.put("signout", this::signout);
        this.doPostActions.put("signup", this::signUp);
        this.doPostActions.put("signin", this::signIn);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getUser".equals(action)) {
            String userId = req.getParameter("userId");
            if (userId != null) {
                int id = Integer.parseInt(userId);
                Optional<User> user = this.validate.findById(id);
                if (user.isPresent()) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(resp.getOutputStream(), user.get());
                }
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "signin";
        }
        String response;
        response = this.doPostActions.get(action).apply(req);
        resp.getWriter().write(response);
    }

    private String updateUser(HttpServletRequest req) {
        boolean success = false;
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(req.getReader(), User.class);
            success = this.validate.update(user);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return success ? RESPONSE_SUCCESS : this.getJsonResponse("error", "User does not exist");
    }

    private String getJsonResponse(String field, String value) {
        return "{\"" + field + "\":\"" + value + "\"}";
    }

    private String changePassword(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        String oldPwd = req.getParameter("oldPassword");
        String newPwd = req.getParameter("newPassword");
        return this.validate.setNewPassword(userId, oldPwd, newPwd)
                ? RESPONSE_SUCCESS : this.getJsonResponse("error", "User does not exist");
    }

    private String signout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return RESPONSE_SUCCESS;
    }

    private String signUp(HttpServletRequest req) {
        String result = this.getJsonResponse("error", "User already exists");
        try {
            User user = new ObjectMapper().readValue(req.getReader(), User.class);
            user.setRegistration(LocalDateTime.now());
            user.setRole(Role.USER);
            if (this.validate.add(user)) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                result = RESPONSE_SUCCESS;
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }

    private String signIn(HttpServletRequest req) {
        String response = this.getJsonResponse("error", "Incorrect login or password");
        String login = req.getParameter("login");
        String pwd = req.getParameter("password");
        for (User user : this.validate.findAll()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(pwd)) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                response = RESPONSE_SUCCESS;
                break;
            }
        }
        return response;
    }
}
