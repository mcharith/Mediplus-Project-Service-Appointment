package lk.ijse.eca.appointmentservice.client;

import lk.ijse.eca.appointmentservice.exception.DoctorServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class DoctorServiceClient {
    private final RestClient restClient;

    public DoctorServiceClient(@LoadBalanced RestClient.Builder restClientBuilder){
        this.restClient = restClientBuilder
                .baseUrl("http://DOCTOR-SERVICE")
                .build();
    }

    public void validateDoctor(String doctorId) {
        log.debug("Validate program with doctor ID {}", doctorId);
        try {
            restClient.get()
                    .uri("/api/v1/doctor/{id}",doctorId)
                    .retrieve()
                    .toBodilessEntity();
        }catch (RestClientException e){
            log.error("Error while validating doctor: {}",doctorId,e);
            throw new DoctorServiceException(
                    "Unable to validate doctor: " + doctorId, e
            );
        }
    }
}
