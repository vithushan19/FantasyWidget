package com.cardillsports.fantasystats.fantasyv.model;

/**
 * Created by vithushan on 1/23/17.
 */

public class UserTeam {

    public final String teamName;
    public final String teamLogoUrl;
    public final String leagueKey;

    public UserTeam(String teamName, String teamLogoUrl, String leagueKey) {
        this.teamName = teamName;
        this.teamLogoUrl = teamLogoUrl;
        this.leagueKey = leagueKey;
    }
}