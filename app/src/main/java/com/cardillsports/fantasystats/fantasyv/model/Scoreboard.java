package com.cardillsports.fantasystats.fantasyv.model;

import com.squareup.moshi.Json;

/**
 * Created by vithushan on 1/22/17.
 */

public class Scoreboard {
    public final String week;

    @Json(name = "0")
    public final ScoreboardWeek scoreboardWeek;

    public Scoreboard(String week, ScoreboardWeek scoreboardWeek) {
        this.week = week;
        this.scoreboardWeek = scoreboardWeek;
    }
}
