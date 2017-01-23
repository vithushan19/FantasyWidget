package com.cardillsports.fantasystats.fantasyv.model;

import java.util.List;

/**
 * Created by vithushan on 1/22/17.
 */

public class ScoreboardResponseFantasyContent {
    public final List<ScoreboardResponseLeague> league;

    public ScoreboardResponseFantasyContent(List<ScoreboardResponseLeague> league) {
        this.league = league;
    }
}