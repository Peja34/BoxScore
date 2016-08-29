/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxscore.game;

/**
 * Implements game of basketball
 * @author Peja
 */
public class Game {
    /**Number of minutes in a single quarter*/      public int minutes;
    /**Number of minutes in a single overtime*/     public int overtime;
    /**Time remaing to the end of period*/          public int actualTime;
    /**Field of ref names*/                         public String[] refs;
    /**Place where game takes place*/               public String venue;
    /**Game date*/                                  public String date;
    /**Game time*/                                  public String time;
    /**Number of ongoing quarter*/                  public int quarter;
    /**Team A of the basketball game*/              public Team teamA;
    /**Team B of the basketball game*/              public Team teamB;
    
    /**Indicator of state of basketball clock*/     public boolean stop = false;
    
    /**
     * Creates a new game
     * 
     * @param minutes lenght of a single quarter
     * @param overtime lenght of overtime
     */
    public Game (int minutes, int overtime) {
        this.minutes = minutes;
        this.overtime = overtime;
        this.actualTime = this.minutes * 60;
        this.refs = new String[4];
    }
    
    /**
     * Sets basic info about game
     * 
     * @param venue place of the gane
     * @param date date of the game
     * @param time time of the game
     */
    public void setInfo (String venue, String date, String time) {
        this.date = date;
        this.venue = venue;
        this.time = time;
    }
    
    /**
     * Sets names of refferees
     * 
     * @param ref1 name of ref 1
     * @param ref2 name of ref 2
     * @param ref3 name of ref 3
     * @param com name of commisioner
     */
    public void setReferees (String ref1, String ref2, String ref3, String com) {
        refs[0] = ref1;
        refs[1] = ref2;
        if ("".equals(ref3)) {
            refs[2] = null;
        }
        else {
            refs[2] = ref3;
        }
        if ("".equals(com)) {
            refs[3] = null;
        }
        else {
            refs[3] = com;
        }
    }
    
    /**
     * Creates new team
     * 
     * @param name name of the team
     * @param shortcut shortcut of team name
     * @param members number of players on the team
     * @return newly created Team
     */
    public Team createTeam (String name, String shortcut, int members) {
        if (this.teamA == null) {
            this.teamA = new Team(name, shortcut, members);
            return this.teamA;
        }
        else {
            this.teamB = new Team(name, shortcut, members);
            return this.teamB;
        }
    }
    
    /**
     * Creates new player
     * 
     * @param isA team indicator
     * @param num number of new player
     * @param name name of new player
     * @param surname last name of new player
     */
    public void creatPlayer (boolean isA, String num, String name, String surname) {
        if (isA) {
            this.teamA.creatPlayer(num, name, surname);
        }
        else {
            this.teamB.creatPlayer(num, name, surname);
        }
    }
    
    /**
     * Creates new game
     */
    public void newGame() {
        this.quarter = 1;
        this.teamA.newGame();
        if (this.teamB != null) {
            this.teamB.newGame();
        }
    }
    
    /**
     * Creates new quarter
     * 
     * @return false if next quarter is overtime, true if not
     */
    public boolean nextQuarter() {
        if (quarter < 4) {
            this.actualTime = this.minutes * 60;
            this.quarter++;
            this.teamA.nextQuarter(this.quarter);
            if (teamB != null) {
                this.teamB.nextQuarter(this.quarter);
            }
            return true;
        }
        else {
            this.actualTime = this.overtime * 60;
            this.quarter++;
            if (quarter == 5) {
                this.teamA.setOvertime();
                if (teamB != null) {
                    this.teamB.setOvertime();
                }
            }
            else {
                this.teamA.nextOvertime();
                if (teamB != null) {
                    this.teamB.nextOvertime();
                }
            }
            return false;
        }
    }
    
    /**
     * Scores a basket
     * 
     * @param isA team indicator
     * @param points value of basket
     * @param num number of player
     */
    public void score (boolean isA, int points, String num) {
        if (isA) {
            this.teamA.score(points, num, quarter);
        }
        else {
            this.teamB.score(points, num, quarter);
        }
    }
    
    /**
     * Misses a shot
     * 
     * @param isA team indicator
     * @param points value of shot
     * @param num number of player
     */
    public void miss (boolean isA, int points, String num) {
        if (isA) {
            this.teamA.miss(points, num);
        }
        else {
            this.teamB.miss(points, num);
        }
    }
    
    /**
     * Gets total points of selected team
     * 
     * @param isA team indicator
     * @return String of total points of selected team
     */
    public String getPoints (boolean isA) {
        if (isA) {
            return String.valueOf(this.teamA.total_points);
        }
        else {
            return String.valueOf(this.teamB.total_points);
        }
    }
    
    public String getQPoints(boolean isA, int quarter) {
        if (isA) {
            return this.teamA.getQScore(quarter);
        }
        else {
            return this.teamB.getQScore(quarter);
        }
    }
    
    /**
     * Gets an offensive rebound
     * 
     * @param isA team indicator
     * @param num number of player
     */
    public void of_rebound (boolean isA, String num) {
        if (isA) {
            this.teamA.of_rebound(num);
        }
        else {
            this.teamB.of_rebound(num);
        }
    }
    
    /**
     * Gets defensive rebound
     * 
     * @param isA team indicator
     * @param num number of player
     */
    public void def_rebound (boolean isA, String num) {
        if (isA) {
            this.teamA.def_rebound(num);
        }
        else {
            this.teamB.def_rebound(num);
        }
    }
    
    /**
     * Blocks a shot
     * 
     * @param isA team indicator
     * @param num number of player
     */
    public void block (boolean isA, String num) {
        if (isA) {
            this.teamA.block(num);
        }
        else {
            this.teamB.block(num);
        }
    }
    
    /**
     * Gets an assist
     * 
     * @param isA team indicator
     * @param num number of player
     */
    public void assist (boolean isA, String num) {
        if (isA) {
            this.teamA.assist(num);
        }
        else {
            this.teamB.assist(num);
        }
    }
    
    /**
     * Steals the ball
     * 
     * @param isA team indicator
     * @param num number of player
     */
    public void steal (boolean isA, String num) {
        if (isA) {
            this.teamA.steal(num);
        }
        else {
            this.teamB.steal(num);
        }
    }
    
    /**
     * Gains a foul
     * 
     * @param isA team indicator
     * @param num number of player
     */
    public void gainedFoul (boolean isA, String num) {
        if (isA) {
            this.teamA.gainedFoul(num);
        }
        else {
            this.teamB.gainedFoul(num);
        }
    }
    
    /**
     * Commits a foul
     * 
     * @param isA team indicator
     * @param num number of player
     */
    public void foul (boolean isA, String num) {
        if (isA) {
            this.teamA.foul(quarter, num);
        }
        else {
            this.teamB.foul(quarter, num);
        }
    }
    
    /**
     * Gets team fouls in this quarter
     * 
     * @param isA team indicator
     * @return String of fouls commitied in ongoing quarter
     */
    public String getFoul (boolean isA) {
        if (isA) {
            return teamA.getFouls(quarter);
        }
        else {
            return teamB.getFouls(quarter);
        }
    }
    
    /**
     * Gets team fouls in game
     * 
     * @param isA team indicator
     * @return String of fouls commited in whole game
     */
    public String getTotalFouls (boolean isA) {
        if (isA) {
            return teamA.getFouls(0);
        }
        else {
            return teamB.getFouls(0);
        }
    }
    
    /**
     * Commits a turnover
     * 
     * @param isA team indicator
     * @param num number of player
     */
    public void turnover (boolean isA, String num) {
        if (isA) {
            this.teamA.turnover(num);
        }
        else {
            this.teamB.turnover(num);
        }
    }
    
    /**
     * Subs player in
     * 
     * @param isA team indicator
     * @param num number of player
     * @param time time in which player subs in
     */
    public void subIn (boolean isA, String num, int time) {
        if (isA) {
            this.teamA.subIn(num, time);
        }
        else {
            this.teamB.subIn(num, time);
        }
    }
    
    /**
     * Subs player out
     * 
     * @param isA team indicator
     * @param num number of player
     * @param time time in which player subs out
     */
    public void subOut (boolean isA, String num, int time) {
        if (isA) {
            this.teamB.subOut(num, time);
        }
        else {
            this.teamB.subOut(num, time);
        }
    }
    
    /**
     * Gets player validity
     * 
     * @param isA team indicator
     * @param num number of player
     * @return integer of players validity
     */
    public int getValidity (boolean isA, String num) {
        if (isA) {
            return this.teamA.getValidity(num);
        }
        else {
            return this.teamB.getValidity(num);
        }
    }
    
    /**
     * Takes team timeout
     * 
     * @param isA team indicator
     */
    public void takeTimeout (boolean isA) {
        if (isA) {
            this.teamA.takeTimeout();
        }
        else {
            this.teamB.takeTimeout();
        }
    }
    
    /**
     * Gets number of remaining timeouts
     * 
     * @param isA team indicator
     * @return integer of remaining timeouts
     */
    public int getTimeout (boolean isA) {
        if (isA) {
            return this.teamA.getTimeout();
        }
        else {
            return this.teamB.getTimeout();
        }
    }
}
