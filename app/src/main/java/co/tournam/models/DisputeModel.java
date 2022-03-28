package co.tournam.models;

import java.util.List;
import java.util.Map;

public class DisputeModel {
    private String matchName;
    private double nextStartTime;
    private int gameIndex;
    private Map<String, ProofModel> proofs;
    private Map<String, DisputeTeamModel> teams;
    private List<String> keys;

    public Map<String, ProofModel> getProofs() {
        return proofs;
    }

    public void setProofs(Map<String, ProofModel> proofs) {
        this.proofs = proofs;
    }

    public List<String> getKeys() {
        return keys;
    }

    public Map<String, DisputeTeamModel> getTeams() {
        return teams;
    }

    public void setTeams(Map<String, DisputeTeamModel> teams) {
        this.teams = teams;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public double getNextStartTime() {
        return nextStartTime;
    }

    public void setNextStartTime(double nextStartTime) {
        this.nextStartTime = nextStartTime;
    }

    public int getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }

    public class DisputeTeamModel {
        private String name;
        private String icon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
