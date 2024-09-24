package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/election")
public class ElectionDataController {

    @Autowired
    private ElectionDataService electionDataService;

    @GetMapping(value = "/data", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ElectionData getElectionData() {
        return electionDataService.generateElectionData();
    }
}
