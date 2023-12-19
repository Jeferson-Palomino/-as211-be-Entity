package com.example.t07_entidadescoperantes;

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
public class EntidadControllerTest2 {


    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void testCrearConNombreNulo() {
        EntidadRequestDTO requestDto = new EntidadRequestDTO();
        requestDto.setNombre(null);
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

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println("Salida Esperada: HTTP 400 Bad Request");
    }

    @Test
    public void testCrearConRucInvalido() {
        EntidadRequestDTO requestDto = new EntidadRequestDTO();
        requestDto.setNombre("Ejemplo Corp.");
        requestDto.setContacto(975947746);
        requestDto.setRuc(new BigInteger("12345")); // RUC inválido
        requestDto.setDireccion("Urb. San Isidro");
        requestDto.setEstado("A");

        ResponseEntity<EntidadResponseDTO> response = restTemplate.exchange(
                "http://localhost:8080/v1/entidad",
                HttpMethod.POST,
                new HttpEntity<>(requestDto, new HttpHeaders()),
                EntidadResponseDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println("Salida Esperada: HTTP 400 Bad Request");
    }

    @Test
    public void testBuscarPorIdConIdInexistente() {
        int entityId = 9999; // ID que no existe en la base de datos

        ResponseEntity<EntidadResponseDTO> response = restTemplate.exchange(
                "http://localhost:8080/v1/entidad/" + entityId,
                HttpMethod.GET,
                null,
                EntidadResponseDTO.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Salida Esperada: HTTP 404 Not Found");
    }

    @Test
    public void testActualizarConEstadoInvalido() {
        int entityId = 4;
        EntidadRequestDTO requestDto = new EntidadRequestDTO();
        requestDto.setNombre("Ejemplo Corp.");
        requestDto.setContacto(975947746);
        requestDto.setRuc(new BigInteger("1234567890"));
        requestDto.setDireccion("Urb. San Isidro");
        requestDto.setEstado("X"); // Estado inválido

        ResponseEntity<EntidadResponseDTO> response = restTemplate.exchange(
                "http://localhost:8080/v1/entidad/" + entityId,
                HttpMethod.PUT,
                new HttpEntity<>(requestDto, new HttpHeaders()),
                EntidadResponseDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println("Salida Esperada: HTTP 400 Bad Request");
    }

    @Test
    public void testEliminarConIdInexistente() {
        int entityId = 9999; // ID que no existe en la base de datos

        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:8080/v1/entidad/" + entityId,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Salida Esperada: HTTP 404 Not Found");
    }


    



}
