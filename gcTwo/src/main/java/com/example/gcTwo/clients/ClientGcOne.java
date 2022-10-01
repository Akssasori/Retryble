package com.example.gcTwo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${app.gc-one.name}",
        url = "${app.gc-one.url}", configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface ClientGcOne {

}
