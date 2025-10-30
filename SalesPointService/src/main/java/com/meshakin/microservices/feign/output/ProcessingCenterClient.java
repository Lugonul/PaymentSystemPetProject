package com.meshakin.microservices.feign.output;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "processing-center-service")
public interface ProcessingCenterClient {

    @PostMapping("/api/requestToProcessingCenter")
    void startTransaction(@RequestBody RequestDTO data);;
}
