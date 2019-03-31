package ru.job4j.items.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.items.models.Item;
import ru.job4j.items.models.ItemService;
import ru.job4j.items.models.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Items management servlet.
 * @author Vadim Bolokhov
 */
public class ItemsServlet extends HttpServlet {
    /** Validation service */
    private final Service service = ItemService.getInstance();
    /** Logger */
    private static final Logger LOG = LogManager.getLogger(ItemsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = req.getParameter("action");
        if (query != null) {
            ObjectMapper mapper = new ObjectMapper();
            List<Item> itemList = new LinkedList<>();
            if ("getAllItems".equals(query)) {
                itemList = this.service.getAllItems();
            } else if ("getUndone".equals(query)) {
                itemList = this.service.getUndoneItems();
            }
            mapper.writeValue(resp.getOutputStream(), itemList);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getReader());
        String action = node.get("action").asText();
        JsonNode itemNode = node.get("item");
        Item item = mapper.treeToValue(itemNode, Item.class);
        LOG.info(String.format("Receiving request with action: %s, and item: %s", action, item));
        if ("add".equals(action)) {
            Item newItem = this.service.addItem(item);
            if (newItem.getId() == 0) {
                String error = "{\"error\" : \"Cannot add the task right now. Please try again later.\"}";
                this.sendErrorMessage(error, resp);
            } else {
                LOG.info(String.format("Added item. New item id: %d", newItem.getId()));
                mapper.writeValue(resp.getOutputStream(), newItem);
            }
        } else if ("update".equals(action)) {
            if (!this.service.update(item)) {
                String error = "{\"error\" : \"Cannot update the task right now. Please try again later.\"}";
                this.sendErrorMessage(error, resp);
            }
        }
    }

    private void sendErrorMessage(String error, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.write(error);
        out.flush();
    }
}
