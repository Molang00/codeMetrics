package com.SoftwareMatrix;

public class StringProducer {
    String BASE_URL;
    String TEAM;
    String PROJECT;

    public StringProducer() {
        BASE_URL = "https://csed332.postech.ac.kr";
        TEAM = "team3";
        PROJECT = "team_project";
    }

    public String GetHome() {
        return BASE_URL + "/" + TEAM + "/" + PROJECT;
    }

    private String GetBase() {
        return BASE_URL;
    }

    public String GetWiki() {
        return GetHome() + "/" + "wikis";
    }

    public String GetTutorial() {
        return GetWiki() + "/" + "Tutorial";
    }

};
