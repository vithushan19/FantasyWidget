package com.cardillsports.fantasystats.fantasyv.model;

import java.util.List;

/**
 * Created by vithushan on 1/23/17.
 */

public class Scoreboard {

    public final int week;
    public final List<Matchup> matchupList;

    public Scoreboard(int week, List<Matchup> matchupList) {
        this.week = week;
        this.matchupList = matchupList;
    }
}
