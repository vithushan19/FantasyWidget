package com.cardillsports.fantasystats.fantasyv.model;

import java.util.List;

/**
 * Created by vithushan on 1/23/17.
 */

public class Matchup {
    public final List<MatchupTeam> matchupTeamList;

    public Matchup(List<MatchupTeam> matchupTeamList) {
        this.matchupTeamList = matchupTeamList;
    }
}
