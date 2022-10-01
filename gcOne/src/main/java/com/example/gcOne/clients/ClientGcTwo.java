package com.example.gcOne.clients;

import com.example.gcOne.dto.UserRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${app.gc-two.name}",
        url = "${app.gc-two.url}", configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface ClientGcTwo {

    @RequestMapping(
            value = "/user-receive",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST
    )
    ResponseEntity<String> sendUser(
            @RequestBody UserRequestDTO userRequestDTO);

}
