package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/wahlen")
public class ElectionDataController {

    private final ElectionDataService electionDataService;

    public ElectionDataController(ElectionDataService electionDataService) {
        this.electionDataService = electionDataService;
    }

    @GetMapping(value = "/data/{eID}/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ElectionData getElectionDataAsJson(@PathVariable int eID) {
        return electionDataService.getElectionData(eID);
    }

    @GetMapping(value = "/data/{eID}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ElectionData getElectionDataAsXml(@PathVariable int eID) {
        return electionDataService.getElectionData(eID);
    }
}
