package com.example.demo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class ElectionDataService {

    public ElectionData generateElectionData() {
        List<PartyResult> countingData = Arrays.asList(
                new PartyResult("OEVP", randomVotes(), generatePreferenceVotes()),
                new PartyResult("SPOE", randomVotes(), generatePreferenceVotes()),
                new PartyResult("FPOE", randomVotes(), generatePreferenceVotes()),
                new PartyResult("GRUENE", randomVotes(), generatePreferenceVotes()),
                new PartyResult("NEOS", randomVotes(), generatePreferenceVotes())
        );

        return new ElectionData(
                "33123",
                "Linz Bahnhof",
                "Bahnhofsstrasse 27/9",
                "Linz",
                "Austria",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                countingData
        );
    }

    private List<PreferenceVote> generatePreferenceVotes() {
        return Arrays.asList(
                new PreferenceVote("1", "Max Mustermann", randomVotes()),
                new PreferenceVote("2", "Erika Musterfrau", randomVotes())
        );
    }

    private int randomVotes() {
        return new Random().nextInt(400) + 100;  // Zufällige Anzahl von Stimmen (zwischen 100 und 500)
    }
}

