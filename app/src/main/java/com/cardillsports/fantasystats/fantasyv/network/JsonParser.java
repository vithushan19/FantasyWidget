package com.cardillsports.fantasystats.fantasyv.network;

import com.cardillsports.fantasystats.fantasyv.model.Matchup;
import com.cardillsports.fantasystats.fantasyv.model.Scoreboard;
import com.cardillsports.fantasystats.fantasyv.model.MatchupTeam;
import com.cardillsports.fantasystats.fantasyv.model.User;
import com.cardillsports.fantasystats.fantasyv.model.UserTeam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vithushan on 1/23/17.
 */

public class JsonParser {
    private JsonParser() {}

    public static Scoreboard createScoreboard(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject fantasyContent = jsonObject.getJSONObject("fantasy_content");
        JSONArray league = fantasyContent.getJSONArray("league");
        JSONObject scoreboard = league.getJSONObject(1).getJSONObject("scoreboard");
        int week = scoreboard.getInt("week");
        JSONObject scoreboard0 = scoreboard.getJSONObject("0");
        JSONObject matchups = scoreboard0.getJSONObject("matchups");
        int matchupCount = matchups.getInt("count");

        List<Matchup> matchupList = new ArrayList<>();

        for (int i=0; i<matchupCount; i++) {
            JSONObject matchupHolder = matchups.getJSONObject(String.valueOf(i));
            JSONObject matchup = matchupHolder.getJSONObject("matchup");
            JSONObject matchupTeamsHolder = matchup.getJSONObject("0");
            JSONObject matchupTeams = matchupTeamsHolder.getJSONObject("teams");
            int matchupTeamsCount = matchupTeams.getInt("count");
            List<MatchupTeam> matchupTeamList = new ArrayList<>();
            for (int j=0; j<matchupTeamsCount; j++) {
                JSONObject matchupTeamHolder = matchupTeams.getJSONObject(String.valueOf(j));
                JSONArray matchupTeam = matchupTeamHolder.getJSONArray("team");
                JSONArray teamNameHolder = matchupTeam.getJSONArray(0);
                String teamName = teamNameHolder.getJSONObject(2).getString("name");
                JSONObject teamPointsHolder = matchupTeam.getJSONObject(1);
                JSONObject teamPoints = teamPointsHolder.getJSONObject("team_points");
                int teamPointTotal = teamPoints.getInt("total");
                MatchupTeam team = new MatchupTeam(teamPointTotal, teamName);
                matchupTeamList.add(team);
            }
            Matchup matchupModel = new Matchup(matchupTeamList);
            matchupList.add(matchupModel);
        }

        Scoreboard scoreboardModel = new Scoreboard(week, matchupList);
        return scoreboardModel;
    }

    public static User createUser(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject games = jsonObject.getJSONObject("fantasy_content").getJSONObject("users")
                .getJSONObject("0").getJSONArray("user").getJSONObject(1).getJSONObject("games");

        List<UserTeam> userTeamList = new ArrayList<>();

        int gamesCount = games.getInt("count");
        for (int i=0; i<gamesCount; i++) {

            JSONArray gamesArray = games.getJSONObject(String.valueOf(i)).getJSONArray("game");
            JSONObject field1 = gamesArray.getJSONObject(0);
            String gameName = field1.getString("name");
            if (!"Basketball".equals(gameName)) continue;

            JSONObject teams = gamesArray.getJSONObject(1).getJSONObject("teams");
            int teamCount = teams.getInt("count");
            for (int j=0; j<teamCount; j++) {
                JSONObject team = teams.getJSONObject(String.valueOf(j));
                JSONArray teamArray = team.getJSONArray("team").getJSONArray(0);
                String teamName = teamArray.getJSONObject(2).getString("name");

                String logoUrl = teamArray.getJSONObject(5).getJSONArray("team_logos")
                        .getJSONObject(0).getJSONObject("team_logo").getString("url");
                String teamKey = teamArray.getJSONObject(0).getString("team_key");
                String leagueKey = teamKey.substring(0, teamKey.length() - 4);
                userTeamList.add(new UserTeam(teamName, logoUrl, leagueKey));
            }

        }

        return new User(userTeamList);
    }
}
