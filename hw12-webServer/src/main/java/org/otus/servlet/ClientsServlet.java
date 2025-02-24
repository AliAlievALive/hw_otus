package org.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.otus.crm.model.Address;
import org.otus.crm.model.Client;
import org.otus.crm.model.Phone;
import org.otus.crm.service.DBClientService;
import org.otus.services.TemplateProcessor;

@SuppressWarnings({"java:S1989"})
public class ClientsServlet extends HttpServlet {

    private static final String PAGE_TEMPLATE = "clients.html";
    private final transient DBClientService service;
    private final transient TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBClientService serviceClient) {
        this.templateProcessor = templateProcessor;
        this.service = serviceClient;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(PAGE_TEMPLATE, getParamsMap()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        service.saveClient(new Client(null, name, new Address(null, address), List.of(new Phone(null, phone))));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(PAGE_TEMPLATE, getParamsMap()));
    }

    private HashMap<String, Object> getParamsMap() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        var clients = service.findAll();
        if (clients != null) {
            paramsMap.put("clients", clients);
        }
        return paramsMap;
    }
}
