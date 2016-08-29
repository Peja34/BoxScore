
package boxscore.game;

import java.awt.Color;

/**
 * Implements baketball team
 * @author Peja
 */
public class Team {
    /**Primary team color*/                             public Color primaryColor;
    /**Secondary team collor*/                          public Color secondaryColor;
    /**Number of team players*/                         public final int team_members;
    /**Field of all team players*/                      public Player[] roster;
    /**Name of the team*/                               public String name;
    /**Shortcut of team name*/                          public String shortcut;
    /**Number of total points, scored in the game*/     public int total_points = 0;
    /**Field of points scored in quarters*/             private int[] points;
    /**Number of remaining timeouts*/                   public int timeouts;
    /**Field of team fouls per quarter*/                private int[] fouls;
    /**Number of total team fouls*/                     private int total_fouls = 0;
    /**Counts players as thez are added to the team*/   private int counter = 0;
    /**Name of the coach*/                              public String coach;
    /**Number of players on the bench*/                 public int benchPlayers;
    
    /**
     * Creates a new team
     * 
     * @param name name of new team
     * @param shortcut shortcut of team name
     * @param members number of players, who are part of the team
     */
    public Team (String name, String shortcut, int members) {
        this.name = name;
        this.shortcut = shortcut;
        this.points = new int[6];
        this.fouls = new int[6];
        this.team_members = members;
        this.roster = new Player[members];
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.BLUE;
    }
    
    /**
     * Creates new player for the team
     * 
     * @param number jersey number of new player
     * @param name name of new player
     * @param surname surname of new player
     */
    public void creatPlayer (String number, String name, String surname) {
        this.roster[counter] = new Player(number, name, surname);
        counter++;
    }
    
    /**
     * Creates coach for the team
     * 
     * @param name name of the coach
     */
    public void createCoach (String name) {
        this.coach = name;
    }
    
    /**
     * Gets player based on his jersey number
     * 
     * @param num number of players jersey
     * @return searched Player, null if this jersey number does not exit
     */
    public Player getPlayer (String num) {
        for(int i = 0; i < 12; i++) {
            if(this.roster[i].number.equals(num)) {
                return this.roster[i];
            }
        }
        return null;
    }
    
    /**
     * Gets player based on his field index
     * 
     * @param i index on which player is saved
     * @return Player on wnated index
     */
    public Player getIPlayer (int i) {
        return this.roster[i];
    }
    
    /**
     * Sets team colors
     * 
     * @param c1 primary team color
     * @param c2 secondary team color
     */
    public void setColor (Color c1, Color c2) {
        this.primaryColor = c1;
        this.secondaryColor = c2;
    }
    
    /**
     * Starts a new game
     */
    public void newGame() {
        this.fouls[1] = 0;
        this.points[1] = 0;
        this.timeouts = 2;
    }
    
    /**
     * Starts a new quarter
     * 
     * @param quarter number of new quarter
     */
    public void nextQuarter (int quarter) {
        this.fouls[quarter] = 0;
        this.points[quarter] = 0;
        if (quarter == 3) {
            this.timeouts = 3;
        }
    }
    
    /**
     * Sets and starts first overtime
     */
    public void setOvertime() {
        this.fouls[5] = this.fouls[4];
        this.timeouts = 1;
        this.points[5] = 0;
    }
    
    /**
     * Sets and starts every overtime but the first
     */
    public void nextOvertime() {
        this.timeouts = 1;
    }
    
    /**
     * 
     * @return number of players on the team
     */
    public int getPlayers() {
        return this.team_members;
    }
    
    /**
     * 
     * @return total points scored by a tema, as a String
     */
    public String getScore() {
        return String.valueOf(total_points);
    }
    
    /**
     * commits a foul
     * 
     * @param quarter quarter in which foul has been commited
     * @param player_number number of player who commited foul
     */
    public void foul (int quarter, String player_number) {
        this.total_fouls++;
        if (quarter != 0) {
            this.fouls[quarter]++;
            if (player_number.equals("COACH")) {
                return;
            }
        }
        if (player_number != null) {
            for(int i = 0; i < 12; i++) {
                if (roster[i].number.equals(player_number)) {
                    roster[i].fouls++;
                    break;
                }
            }
        }
    }
    
    /**
     * returns number of fouls in a specific quarter
     * 
     * @param quarter number of quarter, from which fouls are meant to be returned
     * @return String of fouls in a quarter
     */
    public String getFouls (int quarter) {
        if (quarter == 0) {
            return String.valueOf(this.total_fouls);
        }
        else {
            return String.valueOf(this.fouls[quarter]);
        }
    }
    
    /**
     * Scores a basket
     * 
     * @param points value of a basket
     * @param player_number jersey number of a player who scored
     * @param quarter number of quarter in which he scored
     */
    public void score (int points, String player_number, int quarter) {
        this.total_points += points;
        this.points[quarter] += points;
        for (int i = 0; i < 12; i++) {
            if (roster[i].number.equals(player_number)) {
                this.roster[i].score(points);
                break;
            }
        }
    }
    
    /**
     * Misses a shot
     * 
     * @param points value of missed shot
     * @param player_number number of player who missed
     */
    public void miss (int points, String player_number) {
        for (int i = 0; i < 12; i++) {
            if (roster[i].number.equals(player_number)) {
                this.roster[i].miss(points);
                break;
            }
        }
    }
    
    /**
     * Gets a offensive rebound
     * @param num number of player
     */
    public void of_rebound (String num) {
        for(int i = 0; i < 12; i++) {
            if (roster[i].number.equals(num)) {
                roster[i].of_rebounds++;
                roster[i].rebounds++;
                break;
            }
        }
    }
    
    /**
     * Gets a defensive rebound
     * 
     * @param num number of player
     */
    public void def_rebound (String num) {
        for(int i = 0; i < 12; i++) {
            if (roster[i].number.equals(num)) {
                roster[i].def_rebounds++;
                roster[i].rebounds++;
                break;
            }
        }
    }
    
    /**
     * Blocks a shot
     * 
     * @param num number of player
     */
    public void block (String num) {
        for(int i = 0; i < 12; i++) {
            if (roster[i].number.equals(num)) {
                roster[i].blocks++;
                break;
            }
        }
    }
    
    /**
     * Gets an assist
     * 
     * @param num numberof player
     */
    public void assist (String num) {
        for(int i = 0; i < 12; i++) {
            if (roster[i].number.equals(num)) {
                roster[i].assists++;
                break;
            }
        }
    }
    
    /**
     * Gets a steal
     * 
     * @param num number of player
     */
    public void steal (String num) {
        for(int i = 0; i < 12; i++) {
            if (roster[i].number.equals(num)) {
                roster[i].steals++;
                break;
            }
        }
    }
    
    /**
     * Gains a foul
     * 
     * @param num number of player
     */
    public void gainedFoul (String num) {
        for(int i = 0; i < 12; i++) {
            if (roster[i].number.equals(num)) {
                roster[i].foulsGained++;
                break;
            }
        }
    }
    
    /**
     * Turns the ball over
     * 
     * @param num number of player
     */
    public void turnover (String num) {
        for(int i = 0; i < 12; i++) {
            if (roster[i].number.equals(num)) {
                roster[i].turnovers++;
                break;
            }
        }
    }
    
    /**
     * Substitutes player in game
     * 
     * @param num number of player
     * @param time time in which player subs in
     */
    public void subIn (String num, int time) {
        for(int i = 0; i < 12; i++) {
            if (roster[i].number.equals(num)) {
                roster[i].timeIn = time;
                break;
            }
        }
    }
    
    /**
     * Substitutes player out of game
     * 
     * @param num number of player
     * @param time time in which player subs out
     */
    public void subOut (String num, int time) {
        for(int i = 0; i < 12; i++) {
            if (roster[i].number.equals(num)) {
                roster[i].subOut(time);
                break;
            }
        }
    }
    
    /**
     * Calculates players validity
     * 
     * @param num number of player
     * @return integer of players validity
     */
    public int getValidity (String num) {
        for(int i = 0; i < 12; i++) {
            if (roster[i].number.equals(num))
                return roster[i].getValidity();
        }
        return 0;
    }
    
    /**
     * Calls a team timeout
     */
    public void takeTimeout() {
        this.timeouts--;
    }
    
    /**
     * Gets a team timeout
     * 
     * @return integer of team timeouts remaining
     */
    public int getTimeout() {
        return this.timeouts;
    }
}
