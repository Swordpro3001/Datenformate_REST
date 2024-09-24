package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectionData {
    private String regionID;
    private String regionName;
    private String regionAddress;
    private String regionPostalCode;
    private String federalState;
    private String timestamp;
    private List<PartyResult> countingData;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class PartyResult {
    private String partyID;
    private int amountVotes;
    private List<PreferenceVote> preferenceVotes;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class PreferenceVote {
    private String listNumber;
    private String candidateName;
    private int votes;
}
