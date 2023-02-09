package ru.otus.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.dto.ClientDto;
import ru.otus.services.ClientService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClientApiServlet extends HttpServlet {
    private static final int ID_PATH_PARAM_POSITION = 1;

    private final ClientService clientService;
    private final ObjectMapper objectMapper;

    public ClientApiServlet(ClientService clientService, ObjectMapper objectMapper) {
        this.clientService = clientService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long requiredClientId = extractIdFromRequest(req);
        String value;
        if (requiredClientId == null) {
            List<ClientDto> clientDtos = clientService.findAll();
            value = objectMapper.writeValueAsString(clientDtos);
        } else {
            ClientDto clientDto = clientService.findById(requiredClientId);
            value = clientDto == null ? "{}" : objectMapper.writeValueAsString(clientDto);
        }
        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outStream = resp.getOutputStream();
        outStream.print(value);
    }

    private Long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: null;
        return id != null ? Long.parseLong(id) : null;
    }
}