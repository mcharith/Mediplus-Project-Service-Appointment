package lk.ijse.eca.appointmentservice.client;

import lk.ijse.eca.appointmentservice.dto.PatientDTO;
import lk.ijse.eca.appointmentservice.exception.PatientServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class PatientServiceClient {

    private final RestClient restClient;

    public PatientServiceClient(@LoadBalanced RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://PATIENT-SERVICE")
                .build();
    }

    public PatientDTO getPatient (String patientId) {
        log.debug("Getting patient with ID {}", patientId);
        try {
            return restClient.get()
                    .uri("/api/v1/patient/{id}",patientId)
                    .retrieve()
                    .body(PatientDTO.class);
        }catch (RestClientException e){
            log.error("Failed to fetch patient details for ID:{}",patientId,e);
            throw new PatientServiceException(
                    "Unable to retrive patient details for: " + patientId, e
            );
        }
    }
}
