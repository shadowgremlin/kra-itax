package abc.kraitax.controller;

import abc.kraitax.KRARepository;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class KRAEndpoint {

    private static final String KRA_URI = "https://196.61.52.40/PaymentGateway/KRAPaymentGateway?wsdl";

    private KRARepository KRARepository;

    @Autowired
    public KRAEndpoint(KRARepository KRARepository) {
        this.KRARepository = KRARepository;
    }

    @PayloadRoot(namespace = KRA_URI, LOCALPART = "KRARequest")
    @ResponsePayload
    public KRAResponse getKRA(@RequestPayload KRARequest request) {
        KRAResponse response = new KRAResponse();
        response.setKRA(KRARepository.findEslip(request.geteslipNumber()));

        return response;
    }
}
