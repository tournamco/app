package co.tournam.models;

import java.util.List;

public class RoundModel {
    private List<String> matches;
    private String name;

    //Constructor of a Round Model
    public RoundModel(String name, List<String> matches) {
        this.name = name;
        this.matches = matches;
    }

    public List<String> getMatches() {
        return matches;
    }

    public String getName() {
        return name;
    }
}
