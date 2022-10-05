package com.example.gcOne.controllers;

import com.example.gcOne.clients.ClientGcTwo;
import com.example.gcOne.dto.UserRequestDTO;
import com.example.gcOne.dto.UserResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-send")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final ClientGcTwo clientGcTwo;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Retry(name = "retryInstance", fallbackMethod = "retryFallBack")
//    @CircuitBreaker(name = "circuitbreakerInstance")
    public ResponseEntity<UserResponseDTO> insert(@RequestBody final UserRequestDTO userRequestDTO) throws Exception {

        log.debug("POST send userDto {} ", userRequestDTO.toString());

        var userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(userRequestDTO, userResponseDTO);

        log.debug("Call client");
        System.out.println("-----start request other microservice--------");
        try {

            clientGcTwo.sendUser(userRequestDTO);
            log.debug("**************clientGCOne*******************");

        } catch (Exception e) {
            log.error("Error request {}", e);
            throw new Exception(e.getMessage());
        }

        log.info("Ending request {} ", userRequestDTO.getId());
        return ResponseEntity.ok().body(userResponseDTO);
    }

    public ResponseEntity<UserResponseDTO> retryFallBack(UserRequestDTO userRequestDTO, Throwable t) {

        var userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(userRequestDTO,userResponseDTO);

        userResponseDTO.setId(2L);
        userResponseDTO.setName("RETRY");

        System.out.println("************************");
        System.out.println(userResponseDTO.getId());
        System.out.println(userResponseDTO.getName());
        System.out.println("************************");

        return ResponseEntity.ok().body(userResponseDTO);
    }
}
