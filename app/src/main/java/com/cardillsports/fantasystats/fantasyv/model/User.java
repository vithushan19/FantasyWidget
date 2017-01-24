package com.cardillsports.fantasystats.fantasyv.model;

import java.util.List;

/**
 * Created by vithushan on 1/23/17.
 */

public class User {
    public final List<UserTeam> userTeamList;

    public User(List<UserTeam> userTeamList) {
        this.userTeamList = userTeamList;
    }
}