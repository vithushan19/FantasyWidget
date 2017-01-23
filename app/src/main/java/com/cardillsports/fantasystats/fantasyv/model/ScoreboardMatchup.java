package com.cardillsports.fantasystats.fantasyv.model;

import com.squareup.moshi.Json;

/**
 * Created by vithushan on 1/22/17.
 */
public class ScoreboardMatchup {
    //TODO what if there are less than 6 matchups?

    @Json(name = "0")public final MatchupHolder matchupHolder0;
    @Json(name = "1")public final MatchupHolder matchupHolder1;
    @Json(name = "2")public final MatchupHolder matchupHolder2;
    @Json(name = "3")public final MatchupHolder matchupHolder3;
    @Json(name = "4")public final MatchupHolder matchupHolder4;
    @Json(name = "5")public final MatchupHolder matchupHolder5;
    @Json(name = "6")public final MatchupHolder matchupHolder6;
    public final int count;

    public ScoreboardMatchup(MatchupHolder matchupHolder0, MatchupHolder matchupHolder1,
                             MatchupHolder matchupHolder2, MatchupHolder matchupHolder3,
                             MatchupHolder matchupHolder4, MatchupHolder matchupHolder5,
                             MatchupHolder matchupHolder6, int count) {
        this.matchupHolder0 = matchupHolder0;
        this.matchupHolder1 = matchupHolder1;
        this.matchupHolder2 = matchupHolder2;
        this.matchupHolder3 = matchupHolder3;
        this.matchupHolder4 = matchupHolder4;
        this.matchupHolder5 = matchupHolder5;
        this.matchupHolder6 = matchupHolder6;
        this.count = count;
    }
}
