package ivents.ivents_ui_support.controller;

import ivents.ivents_ui_support.dto.request.ClientRequest;
import ivents.ivents_ui_support.dto.response.ClientResponse;
import ivents.ivents_ui_support.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboarding-service/v1/")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/self-onboarding")
    public ClientResponse selfOnboard(@RequestBody ClientRequest request) {
        return clientService.createClient(request);
    }
}