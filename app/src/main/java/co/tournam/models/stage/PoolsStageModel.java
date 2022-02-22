package co.tournam.models.stage;

import java.util.List;

import co.tournam.models.TeamModel;

public class PoolsStageModel {
    private List<List<TeamModel>> pools;
    private int poolSize;
    private int numberOfWinners;

    public List<List<TeamModel>> getPools() {
        return pools;
    }

    public void setPools(List<List<TeamModel>> pools) {
        this.pools = pools;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getNumberOfWinners() {
        return numberOfWinners;
    }

    public void setNumberOfWinners(int numberOfWinners) {
        this.numberOfWinners = numberOfWinners;
    }
}
