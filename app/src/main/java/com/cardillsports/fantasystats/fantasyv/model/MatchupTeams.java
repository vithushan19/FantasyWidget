package com.cardillsports.fantasystats.fantasyv.model;

import com.squareup.moshi.Json;

/**
 * Created by vithushan on 1/22/17.
 */
public class MatchupTeams {
    @Json(name = "0")public final MatchupTeam matchupTeam0;
    @Json(name = "1")public final MatchupTeam matchupTeam1;
    public final int count;

    public MatchupTeams(MatchupTeam matchupTeam0, MatchupTeam matchupTeam1, int count) {
        this.matchupTeam0 = matchupTeam0;
        this.matchupTeam1 = matchupTeam1;
        this.count = count;
    }
}
