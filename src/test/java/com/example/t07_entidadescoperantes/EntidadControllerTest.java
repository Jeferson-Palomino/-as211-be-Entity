package com.example.t07_entidadescoperantes;

import com.example.t07_entidadescoperantes.T07EntidadesCoperantesApplication;
import com.example.t07_entidadescoperantes.domain.dto.EntidadRequestDTO;
import com.example.t07_entidadescoperantes.domain.dto.EntidadResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = T07EntidadesCoperantesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EntidadControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testFindAll() {
        ResponseEntity<EntidadResponseDTO[]> response = restTemplate.exchange(
                "http://localhost:8080/v1/entidad",
                HttpMethod.GET,
                null,
                EntidadResponseDTO[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        EntidadResponseDTO[] entidadResponseDTOs = response.getBody();
        if (entidadResponseDTOs != null) {
            for (EntidadResponseDTO entidadResponseDTO : entidadResponseDTOs) {
                System.out.println(entidadResponseDTO.toString());
            }
        } else {
            System.out.println("El cuerpo de la respuesta es nulo.");
        }
    }


    @Test
    public void testFindById() {
        int entityId = 1;

        ResponseEntity<EntidadResponseDTO> response = restTemplate.exchange(
                "http://localhost:8080/v1/entidad/" + entityId,
                HttpMethod.GET,
                null,
                EntidadResponseDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdate() {
        int entityId = 7;
        EntidadRequestDTO requestDto = new EntidadRequestDTO();
        requestDto.setNombre("Municipio");
        requestDto.setContacto(123456789);
        requestDto.setRuc(new BigInteger("12345678901"));
        requestDto.setDireccion("Parque central de San Vicente");
        requestDto.setEstado("I");

        ResponseEntity<EntidadResponseDTO> response = restTemplate.exchange(
                "http://localhost:8080/v1/entidad/" + entityId,
                HttpMethod.PUT,
                new HttpEntity<>(requestDto, new HttpHeaders()),
                EntidadResponseDTO.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreate() {
        EntidadRequestDTO requestDto = new EntidadRequestDTO();
        requestDto.setNombre("PNP");
        requestDto.setContacto(975947746);
        requestDto.setRuc(new BigInteger("1234567890"));
        requestDto.setDireccion("Urb. Miraflores");
        requestDto.setEstado("A");

        ResponseEntity<EntidadResponseDTO> response = restTemplate.exchange(
                "http://localhost:8080/v1/entidad",
                HttpMethod.POST,
                new HttpEntity<>(requestDto, new HttpHeaders()),
                EntidadResponseDTO.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testDelete() {
        int entityId = 1;

        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:8080/v1/entidad/" + entityId,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}