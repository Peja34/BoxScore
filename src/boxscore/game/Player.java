/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxscore.game;

/**
 * Implements basketball players
 * @author Peja
 */
public class Player {
    /**Players number*/                     public String number;
    /**Players name*/                       public String name;
    /**Players surname*/                    public String surname;
    /**Scored points*/                      public int points = 0;
    /**Attempted free throws*/              public int fta = 0;
    /**Made free throws*/                   public int ftm = 0;
    /**Attempted shots*/                    public int fga = 0;
    /**Made shots*/                         public int fgm = 0;
    /**Attempted 3-point shots*/            public int tpa = 0;
    /**Made 3-point shots*/                 public int tpm = 0;
    /**Offensive rebounds*/                 public int of_rebounds = 0;
    /**Defensive rebounds*/                 public int def_rebounds = 0;
    /**Blocked shots*/                      public int blocks = 0;
    /**Total rebounds*/                     public int rebounds = 0;
    /**Assits on baskets*/                  public int assists = 0;
    /**Stolen balls*/                       public int steals = 0;
    /**Gained fouls*/                       public int foulsGained = 0;
    /**Balls turned over*/                  public int turnovers = 0;
    /**Commited fouls*/                     public int fouls = 0;
    /**Time in which player subed in*/      public int timeIn = 0;
    /**Total playing time*/                 public int timeTotal = 0;
    
    /**
     * Creates new Player
     * 
     * @param number number of player
     * @param name player name
     * @param surname player last name
     */
    public Player (String number, String name, String surname) {
        this.number = number;
        this.name = name;
        this.surname = surname;
    }
    
    public String getName() {
        return this.surname;
    }
    
    /**
     * Scores a basket
     * 
     * @param points value of basket
     */
    public void score (int points) {
        this.points += points;
        if (points == 1) {
            this.fta++;
            this.ftm++;
        }
        else {
            this.fga++;
            this.fgm++;
            if (points == 3) {
                this.tpa++;
                this.tpm++;
            }
        }
    }
    
    /**
     * Misse a shot
     * 
     * @param points value of shot
     */
    public void miss (int points) {
        if (points == 1) {
            this.fta++;
        }
        else {
            this.fga++;
            if (points == 3) {
                this.tpa++;
            }
        }
    }
    
    /**
     * Subs player in
     * 
     * @param time time in which player has been subbed in
     */
    public void subIn (int time) {
        this.timeIn = time;
    }
    
    /**
     * Subs player out
     * 
     * @param time time in which player has been subbed out
     */
    public void subOut (int time) {
        this.timeTotal += this.timeIn - time;
        this.timeIn = 0;
    }
    
    /**
     * Gets total number of rebounds
     * 
     * @return integer of addition of defensive and offensive rebounds
     */
    public int getTotalRebounds() {
        return this.of_rebounds + this.def_rebounds;
    }
    
    /**
     * Calculates players validity
     * 
     * @return integer of players validity
     */
    public int getValidity () {
        int misses = (fta + fga + tpa) - (ftm + fgm + tpm);
        return points + rebounds + assists + steals + foulsGained - turnovers - fouls - misses;
    }
}
