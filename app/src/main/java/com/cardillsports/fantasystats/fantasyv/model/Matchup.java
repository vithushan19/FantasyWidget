package com.cardillsports.fantasystats.fantasyv.model;

import com.squareup.moshi.Json;

/**
 * Created by vithushan on 1/22/17.
 */
public class Matchup {
    public final String week;
    @Json(name = "0")public final MatchupTeamsHolder matchupTeamsHolder;

    public Matchup(String week, MatchupTeamsHolder matchupTeamsHolder) {
        this.week = week;
        this.matchupTeamsHolder = matchupTeamsHolder;
    }
}
