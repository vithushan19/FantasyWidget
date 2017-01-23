package com.cardillsports.fantasystats.fantasyv.model;

import com.squareup.moshi.Json;

/**
 * Created by vithushan on 1/22/17.
 */
public class MatchupTeamHolder {
    @Json(name = "1") public final TeamPointsHolder teamPointsHolderList;

    public MatchupTeamHolder(TeamPointsHolder teamPointsHolderList) {
        this.teamPointsHolderList = teamPointsHolderList;
    }
}
