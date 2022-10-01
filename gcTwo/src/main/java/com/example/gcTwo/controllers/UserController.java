package com.example.gcTwo.controllers;


import com.example.gcTwo.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-receive")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserResponseDTO> insert(@RequestBody final UserResponseDTO userResponseDTO) {

        log.debug("POST received userDto {} ",userResponseDTO.toString());

        if (userResponseDTO.getName().equalsIgnoreCase("LUCAS")) {
            System.out.println("Cheguei dentro das tentativas");
        }
        if (userResponseDTO.getName().equalsIgnoreCase("RETRY")) {
            System.out.println("Cheguei metodo fallback");
        }

        log.debug("POST user successive {} ",userResponseDTO.getName());
        log.info("User updated successfully userId {} ", userResponseDTO.getId());

        return ResponseEntity.ok().build();
    }
}
