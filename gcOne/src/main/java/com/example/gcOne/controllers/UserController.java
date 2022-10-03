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
//    @Retry(name = "retryInstance", fallbackMethod = "retryFallBack")
    @CircuitBreaker(name = "circuitbreakerInstance")
    public ResponseEntity<UserResponseDTO> insert(@RequestBody final UserRequestDTO userRequestDTO) throws Exception {

        log.debug("POST send userDto {} ", userRequestDTO.toString());

        var userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(userRequestDTO, userResponseDTO);
        log.debug("Call client");
        System.out.println("-----start request other microservice--------");
        try {
            log.debug("**************clientGCOne*******************");
            clientGcTwo.sendUser(userRequestDTO);

        } catch (Exception e) {
            log.debug("EXCEPTION");
            log.debug(e.getMessage());
            throw new Exception(e.getMessage());
        }

        log.debug("POST user successive {} ", userRequestDTO.getName());
        log.info("User updated successfully userId {} ", userRequestDTO.getId());

        return ResponseEntity.ok().body(userResponseDTO);
    }

    public ResponseEntity<UserResponseDTO> retryFallBack(UserRequestDTO userRequestDTO, Throwable t) {

        var userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(2L);
        userResponseDTO.setName("RETRY");

        System.out.println("************************");
        System.out.println(userRequestDTO.getId());
        System.out.println(userRequestDTO.getName());
        System.out.println("************************");

        return ResponseEntity.ok().body(userResponseDTO);
    }
}
