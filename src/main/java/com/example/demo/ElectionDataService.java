package com.example.demo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ElectionDataService {

    // Beispielhafte Datenbank/Map für Wahldaten, die nach RegionID sortiert sind
    private Map<Integer, ElectionData> electionDataMap = new HashMap<>();

    public ElectionDataService() {
        // Generiere initial einige Wahldaten für verschiedene Regionen
        electionDataMap.put(33123, generateElectionData(33123, "Linz Bahnhof"));
        electionDataMap.put(33124, generateElectionData(33124, "Wien Mitte"));
        electionDataMap.put(33125, generateElectionData(33125, "Salzburg Zentrum"));
        electionDataMap.put(33126, generateElectionData(33126, "Graz Hauptplatz"));
        // Weitere Daten können hier hinzugefügt werden
    }

    /**
     * Diese Methode sucht nach Wahldaten basierend auf der regionID.
     * @param regionId die ID der Region, für die die Wahldaten abgerufen werden sollen.
     * @return das Wahldatenobjekt oder Optional.empty() wenn die Region nicht existiert.
     */
    public ElectionData getElectionData(int regionId) {
        return electionDataMap.get(regionId);
    }

    /**
     * Diese Methode generiert Wahldaten für eine bestimmte Region.
     * @param regionId die ID der Region.
     * @param regionName der Name der Region.
     * @return ein ElectionData-Objekt mit generierten Daten.
     */
    public ElectionData generateElectionData(int regionId, String regionName) {
        List<PartyResult> countingData = Arrays.asList(
                new PartyResult("OEVP", randomVotes(), generatePreferenceVotes()),
                new PartyResult("SPOE", randomVotes(), generatePreferenceVotes()),
                new PartyResult("FPOE", randomVotes(), generatePreferenceVotes()),
                new PartyResult("GRUENE", randomVotes(), generatePreferenceVotes()),
                new PartyResult("NEOS", randomVotes(), generatePreferenceVotes())
        );

        return new ElectionData(
                regionId,
                regionName,
                "Bahnhofsstrasse 27/9",
                "Linz",
                "Austria",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                countingData
        );
    }

    // Generiere Vorzugsstimmen für eine Partei
    private List<PreferenceVote> generatePreferenceVotes() {
        return Arrays.asList(
                new PreferenceVote("1", randomCandidateName(), randomVotes()),
                new PreferenceVote("2", randomCandidateName(), randomVotes())
        );
    }

    // Generiere zufällige Stimmenanzahl (zwischen 100 und 500)
    private int randomVotes() {
        return new Random().nextInt(400) + 100;
    }

    private String randomCandidateName() {
        String[] names = {"Max Mustermann", "Erika Musterfrau", "Hans Huber", "Anna Berger", "Peter Maier"};
        return names[new Random().nextInt(names.length)];
    }
}
