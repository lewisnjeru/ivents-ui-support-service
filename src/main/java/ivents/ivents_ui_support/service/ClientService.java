package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.request.ClientRequest;
import ivents.ivents_ui_support.dto.response.ClientResponse;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    public ClientResponse createClient(ClientRequest request) {
        ClientResponse response = new ClientResponse();
        response.setId(1L); // dummy, integrate with DB later
        response.setName(request.getName());
        response.setEmail(request.getEmail());
        return response;
    }
}