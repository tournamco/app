package co.tournam.models;

import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import co.tournam.models.stage.PoolsStageModel;

public class FakeData {
    public TournamentModel tournament;
    public TeamModel team1;
    public TeamModel team2;
    public UserModel user1;
    public UserModel user2;
    public UserModel user3;
    public UserModel user4;

    public FakeData() {
        user1 = new UserModel();
        user1.setEmail("user1@tournam.co");
        user1.setGamerTag("user1");
        user1.setUsername("user1");

        user2 = new UserModel();
        user2.setEmail("user2@tournam.co");
        user2.setGamerTag("user2");
        user2.setUsername("user2");

        user3 = new UserModel();
        user3.setEmail("user1@tournam.co");
        user3.setGamerTag("user3");
        user3.setUsername("user3");

        user4 = new UserModel();
        user4.setEmail("user4@tournam.co");
        user4.setGamerTag("user4");
        user4.setUsername("user4");

        team1 = new TeamModel();
        team1.setLeader(user1);
        team1.setMembers(Arrays.asList(user1, user2));
        team1.setName("Team A");

        team2 = new TeamModel();
        team2.setLeader(user3);
        team2.setMembers(Arrays.asList(user3, user4));
        team2.setName("Team B");

        ProofModel proof1 = new ProofModel();
        proof1.setScores(Arrays.asList(0,1));

        ProofModel proof2 = new ProofModel();
        proof1.setScores(Arrays.asList(1,0));

        GameModel game1 = new GameModel();
        game1.setScores(new HashMap<TeamModel, Integer>() {{
            put(team1, 1);
            put(team2, 0);
        }});
        game1.setProofs(new HashMap<TeamModel, ProofModel>() {{
            put(team1, proof1);
            put(team2, proof2);
        }});

        GameModel game2 = new GameModel();
        game2.setScores(new HashMap<TeamModel, Integer>() {{
            put(team1, 0);
            put(team2, 1);
        }});

        GameModel game3 = new GameModel();
        GameModel game4 = new GameModel();

        MatchModel match1 = new MatchModel();
        match1.setGames(Arrays.asList(game1, game2));
        match1.setStartDate(LocalDateTime.parse("2022-03-07 23:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        match1.setTeams(Arrays.asList(team1, team2));

        MatchModel match2 = new MatchModel();
        match2.setGames(Arrays.asList(game3, game4));
        match2.setStartDate(LocalDateTime.parse("2022-07-03 23:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        match2.setTeams(Arrays.asList(team1, team2));

        RoundModel round1 = new RoundModel();
        round1.setName("Round 1");
        round1.setMatches(Arrays.asList(match1, match2));
        match1.setRound(round1);
        match2.setRound(round1);

        PoolsStageModel stage1 = new PoolsStageModel();
        stage1.setName("Pools");
        stage1.setPools(Arrays.asList(Arrays.asList(team1, team2)));
        stage1.setNumberOfWinners(1);
        stage1.setPoolSize(2);
        stage1.setRounds(Arrays.asList(round1));
        round1.setStage(stage1);

        DisputeModel dispute = new DisputeModel();
        dispute.setProofs(Arrays.asList(proof1, proof2));
        dispute.setGame(game1);
        dispute.setMatch(match1);

        tournament = new TournamentModel();
        tournament.setColor(Color.BLUE);
        tournament.setTeamSize(2);
        tournament.setGame("Rocket League");
        tournament.setName("Rocketly");
        tournament.setTeams(Arrays.asList(team1, team2));
        tournament.setOrganizer(user1);
        tournament.setStages(Arrays.asList(stage1));
        tournament.setDisputes(Arrays.asList(dispute));
        match1.setTournament(tournament);
        match2.setTournament(tournament);
        stage1.setTournament(tournament);
    }
}
