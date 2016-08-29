/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxscore.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import boxscore.game.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements graphical user interface
 * @author Peja
 */
public class gui extends JFrame {
    protected boolean oneTeam;
    public boolean isA = true;
    public boolean selectedTeam = false;
    private boolean back;
    private String selectedPlayer;
    
    Game game;
    TimerThread t;
    JLabel timeLabel;
    JButton startBtn;
    outputManager output ;
    
    public JTextField nameField;
    public JTextField shortcutField;
    public JTextField[] numbers;
    public JTextField[] names;
    public JTextField[] lnames;
    public JTextField coachName;
    
    private JPanel Menu;
    private JPanel GameSettings;
    JPanel TeamSettings;
    JPanel BoxScore;
    private JButton buttonOne;
    private JButton exit;
    private JLabel title;
    private Font font;
    private Font fontGui;
    private Font fontBig;
    
    private static final int boxSize = 50;
    private final int statsBar = 200;
    
    public gui() {
        super("Basketball boxscore (Beta)");
        output = new outputManager(this);
        this.getContentPane().setLayout(new BorderLayout());
        font = new Font("Seans", Font.PLAIN, 12);
        fontGui = new Font("Seans", Font.PLAIN, 16);
        fontBig = new Font("Seans", Font.PLAIN, 36);
        back = false;
        
        Menu = new JPanel();
        Menu.setPreferredSize(new Dimension(16 * boxSize + 40 + statsBar, 16 * boxSize + 40));
        Menu.setLayout(null);
        Menu.setBackground(Color.WHITE);
        add(Menu);
        
        buttonOne = new JButton("One team");
        buttonOne.setSize(200, 50);
        buttonOne.setLocation((16 * boxSize + 40 + statsBar)/2 - 100, (16 * boxSize + 40)/3 + 50);
        Menu.add(buttonOne);
        
        exit = new JButton("Exit");
        exit.setSize(200, 50);
        exit.setLocation((16 * boxSize + 40 + statsBar)/2 - 100, (16 * boxSize + 40)/3 + 150);
        Menu.add(exit);
        
        title = new JLabel("Basketball boxscore", SwingConstants.CENTER);
        title.setSize(200, 100);
        title.setLocation((16 * boxSize + 40 + statsBar)/2 - 100, (16 * boxSize + 40)/3 - 150);
        title.setFont(new Font("Seans", Font.BOLD, 20));
        Menu.add(title);
        
        buttonOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStart(e);
            }
        });
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    private void gameStart (ActionEvent event) {
        this.getContentPane().removeAll();
        this.GameSettingsInit();
        this.getContentPane().add(GameSettings, BorderLayout.PAGE_START);
        this.update();
    }
    
    private void teamSettings (ActionEvent event) {
        this.getContentPane().removeAll();
        this.TeamSettingsInit();
        this.getContentPane().add(TeamSettings, BorderLayout.PAGE_START);
        this.update();
    }
    
    private void gameGUI (ActionEvent event) {
        this.getContentPane().removeAll();
        this.boxScore();
        this.getContentPane().add(BoxScore, BorderLayout.PAGE_START);
        this.update();
    }
    
    private void GameSettingsInit() {
        GameSettings = new JPanel();
        GameSettings.setPreferredSize(new Dimension(16 * boxSize + 40 + statsBar, 16 * boxSize + 40));
        GameSettings.setLayout(null);
        GameSettings.setBackground(Color.WHITE);
        add(GameSettings);
        
        JLabel gameTime = new JLabel("Game time:", SwingConstants.CENTER);
        gameTime.setSize(200, 100);
        gameTime.setLocation((16 * boxSize + 40 + statsBar)/2 - 140, (16 * boxSize + 40)/3 - 120);
        gameTime.setFont(font);
        GameSettings.add(gameTime);
        
        JTextField gameTimeField = new JTextField("10", 2);
        gameTimeField.setSize(30, 20);
        gameTimeField.setLocation((16 * boxSize + 40 + statsBar)/2, (16 * boxSize + 40)/3 - 80);
        gameTimeField.setFont(font);
        GameSettings.add(gameTimeField);
        
        JLabel OT = new JLabel("OT:", SwingConstants.CENTER);
        OT.setSize(200, 100);
        OT.setLocation((16 * boxSize + 40 + statsBar)/2 - 120, (16 * boxSize + 40)/3 - 90);
        OT.setFont(font);
        GameSettings.add(OT);
        
        JTextField otField = new JTextField("5", 2);
        otField.setSize(30, 20);
        otField.setLocation((16 * boxSize + 40 + statsBar)/2, (16 * boxSize + 40)/3 - 50);
        otField.setFont(font);
        GameSettings.add(otField);
        
        JLabel venue = new JLabel("Venue:", SwingConstants.CENTER);
        venue.setSize(200, 100);
        venue.setLocation((16 * boxSize + 40 + statsBar)/2 - 130, (16 * boxSize + 40)/3 - 60);
        venue.setFont(font);
        GameSettings.add(venue);
        
        JTextField venueField = new JTextField("", 20);
        venueField.setSize(140, 20);
        venueField.setLocation((16 * boxSize + 40 + statsBar)/2, (16 * boxSize + 40)/3 - 20);
        venueField.setFont(font);
        GameSettings.add(venueField);
        
        JLabel date = new JLabel("Date:", SwingConstants.CENTER);
        date.setSize(200, 100);
        date.setLocation((16 * boxSize + 40 + statsBar)/2 - 126, (16 * boxSize + 40)/3 - 30);
        date.setFont(font);
        GameSettings.add(date);
        
        JTextField dateField = new JTextField("", 10);
        dateField.setSize(70, 20);
        dateField.setLocation((16 * boxSize + 40 + statsBar)/2, (16 * boxSize + 40)/3 + 10);
        dateField.setFont(font);
        GameSettings.add(dateField);
        
        JLabel time = new JLabel("Time:", SwingConstants.CENTER);
        time.setSize(200, 100);
        time.setLocation((16 * boxSize + 40 + statsBar)/2 - 126, (16 * boxSize + 40)/3);
        time.setFont(font);
        GameSettings.add(time);
        
        JTextField timeField = new JTextField("", 5);
        timeField.setSize(40, 20);
        timeField.setLocation((16 * boxSize + 40 + statsBar)/2, (16 * boxSize + 40)/3 + 40);
        timeField.setFont(font);
        GameSettings.add(timeField);
        
        JLabel Ref1 = new JLabel("Referee 1:", SwingConstants.CENTER);
        Ref1.setSize(200, 100);
        Ref1.setLocation((16 * boxSize + 40 + statsBar)/2 - 140, (16 * boxSize + 40)/3 + 30);
        Ref1.setFont(font);
        GameSettings.add(Ref1);
        
        JTextField Ref1Field = new JTextField("", 20);
        Ref1Field.setSize(140, 20);
        Ref1Field.setLocation((16 * boxSize + 40 + statsBar)/2, (16 * boxSize + 40)/3 + 70);
        Ref1Field.setFont(font);
        GameSettings.add(Ref1Field);
        
        JLabel Ref2 = new JLabel("Referee 2:", SwingConstants.CENTER);
        Ref2.setSize(200, 100);
        Ref2.setLocation((16 * boxSize + 40 + statsBar)/2 - 140, (16 * boxSize + 40)/3 + 60);
        Ref2.setFont(font);
        GameSettings.add(Ref2);
        
        JTextField Ref2Field = new JTextField("", 20);
        Ref2Field.setSize(140, 20);
        Ref2Field.setLocation((16 * boxSize + 40 + statsBar)/2, (16 * boxSize + 40)/3 + 100);
        Ref2Field.setFont(font);
        GameSettings.add(Ref2Field);
        
        JLabel Ref3 = new JLabel("Referee 3:", SwingConstants.CENTER);
        Ref3.setSize(200, 100);
        Ref3.setLocation((16 * boxSize + 40 + statsBar)/2 - 140, (16 * boxSize + 40)/3 + 90);
        Ref3.setFont(font);
        GameSettings.add(Ref3);
        
        JTextField Ref3Field = new JTextField("", 20);
        Ref3Field.setSize(140, 20);
        Ref3Field.setLocation((16 * boxSize + 40 + statsBar)/2, (16 * boxSize + 40)/3 + 130);
        Ref3Field.setFont(font);
        GameSettings.add(Ref3Field);
        
        JLabel comm = new JLabel("Commmissioner:", SwingConstants.CENTER);
        comm.setSize(200, 100);
        comm.setLocation((16 * boxSize + 40 + statsBar)/2 - 160, (16 * boxSize + 40)/3 + 120);
        comm.setFont(font);
        GameSettings.add(comm);
        
        JTextField commField = new JTextField("", 20);
        commField.setSize(140, 20);
        commField.setLocation((16 * boxSize + 40 + statsBar)/2, (16 * boxSize + 40)/3 + 160);
        commField.setFont(font);
        GameSettings.add(commField);
        
        JButton next = new JButton("Next >");
        next.setSize(80, 20);
        next.setLocation((16 * boxSize + 40 + statsBar)/2 + 100, (16 * boxSize + 40)/3 + 310);
        GameSettings.add(next);
        
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 if (back == false) {
                    game = new Game(Integer.parseInt(gameTimeField.getText()), Integer.parseInt(otField.getText()));
                }
                else {
                     game.minutes = Integer.parseInt(gameTimeField.getText());
                     game.overtime = Integer.parseInt(otField.getText());
                }
                game.setInfo(venueField.getText(), dateField.getText(), timeField.getText());
                game.setReferees(Ref1Field.getText(), Ref2Field.getText(), Ref3Field.getText(), commField.getText());
                
                teamSettings(e);
            }
        });
        
        JButton exit = new JButton("Exit");
        exit.setSize(80, 20);
        exit.setLocation((16 * boxSize + 40 + statsBar)/2 + 230, (16 * boxSize + 40)/3 + 310);
        GameSettings.add(exit);
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    private void TeamSettingsInit() {
        TeamSettings = new JPanel();
        TeamSettings.setPreferredSize(new Dimension(16 * boxSize + 40 + statsBar, 16 * boxSize + 40));
        TeamSettings.setLayout(null);
        TeamSettings.setBackground(Color.WHITE);
        add(TeamSettings);
        
        JLabel name = new JLabel("Team name:", SwingConstants.CENTER);
        name.setSize(200, 100);
        name.setLocation((16 * boxSize + 40 + statsBar)/2 - 380, (16 * boxSize + 40)/3 - 210);
        name.setFont(font);
        TeamSettings.add(name);
        
        JLabel shortcut = new JLabel("Shortcut:", SwingConstants.CENTER);
        shortcut.setSize(200,100);
        shortcut.setLocation((16 * boxSize + 40 + statsBar)/2 - 110, (16 * boxSize + 40)/3 - 210);
        shortcut.setFont(font);
        TeamSettings.add(shortcut);
        
        nameField = new JTextField("", 40);
        nameField.setSize(200, 20);
        nameField.setLocation((16 * boxSize + 40 + statsBar)/2 - 240, (16 * boxSize + 40)/3 - 170);
        name.setFont(font);
        TeamSettings.add(nameField);
        
        shortcutField = new JTextField("", 3);
        shortcutField.setSize(50, 20);
        shortcutField.setLocation((16 * boxSize + 40 + statsBar)/2 + 20, (16 * boxSize + 40)/3 - 170);
        shortcutField.setFont(font);
        TeamSettings.add(shortcutField);
        
        JLabel players = new JLabel("Players", SwingConstants.CENTER);
        players.setSize(200, 100);
        players.setLocation((16 * boxSize + 40 + statsBar)/2 - 360, (16 * boxSize + 40)/3 - 170);
        players.setFont(font);
        TeamSettings.add(players);
        
        JLabel num = new JLabel("no.", SwingConstants.CENTER);
        num.setSize(200, 100);
        num.setLocation((16 * boxSize + 40 + statsBar)/2 - 390, (16 * boxSize + 40)/3 - 150);
        num.setFont(font);
        TeamSettings.add(num);
        
        numbers = new JTextField[12];
        
        for(int i = 0, c = 80; i < 12; i++, c -= 30){
            numbers[i] = new JTextField("", 2);
            numbers[i].setSize(50, 20);
            numbers[i].setLocation((16 * boxSize + 40 + statsBar)/2 - 310, (16 * boxSize + 40)/3 - c);
            numbers[i].setFont(font);
            TeamSettings.add(numbers[i]);
        }
        
        JLabel firstName = new JLabel("First name", SwingConstants.CENTER);
        firstName.setSize(200, 100);
        firstName.setLocation((16 * boxSize + 40 + statsBar)/2 - 210, (16 * boxSize + 40)/3 - 150);
        firstName.setFont(font);
        TeamSettings.add(firstName);
        
        names = new JTextField[12];
        
        for(int i = 0, c = 80;  i < 12; i++, c-=30){
            names[i] = new JTextField("", 20);
            names[i].setSize(200, 20);
            names[i].setLocation((16 * boxSize + 40 + statsBar)/2 - 200, (16 * boxSize + 40)/3 - c);
            names[i].setFont(font);
            TeamSettings.add(names[i]);
        }
        
        JLabel lastName = new JLabel("Last name", SwingConstants.CENTER);
        lastName.setSize(200, 100);
        lastName.setLocation((16 * boxSize + 40 + statsBar)/2 + 30, (16 * boxSize + 40)/3 - 150);
        lastName.setFont(font);
        TeamSettings.add(lastName);
        
        lnames = new JTextField[12];
        
        for(int i = 0, c = 80;  i < 12; i++, c-=30){
            lnames[i] = new JTextField("", 20);
            lnames[i].setSize(200, 20);
            lnames[i].setLocation((16 * boxSize + 40 + statsBar)/2 + 30, (16 * boxSize + 40)/3 - c);
            lnames[i].setFont(font);
            TeamSettings.add(lnames[i]);
        }
        
        JLabel coach = new JLabel("Coach:", SwingConstants.CENTER);
        coach.setSize(200, 100);
        coach.setLocation((16 * boxSize + 40 + statsBar)/2 - 400, (16 * boxSize + 40)/3 + 240);
        coach.setFont(font);
        TeamSettings.add(coach);
        
        coachName = new JTextField("", 20);
        coachName.setSize(200, 20);
        coachName.setLocation((16 * boxSize + 40 + statsBar)/2 - 260, (16 * boxSize + 40)/3 + 280);
        coachName.setFont(font);
        TeamSettings.add(coachName);
        
        JButton load = new JButton("Load");
        load.setSize(80, 20);
        load.setLocation((16 * boxSize + 40 + statsBar)/2 - 220, (16 * boxSize + 40)/3 + 310);
        TeamSettings.add(load);
        
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    output.loadRoster(e);
                } catch (IOException ex) {
                    Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        JButton save = new JButton("Save");
        save.setSize(80, 20);
        save.setLocation((16 * boxSize + 40 + statsBar)/2 - 130, (16 * boxSize + 40)/3 + 310);
        TeamSettings.add(save);
        
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    output.saveRoster(e);
                } catch (IOException ex) {
                    Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        JButton prev = new JButton("< Back");
        prev.setSize(80, 20);
        prev.setLocation((16 * boxSize + 40 + statsBar)/2 + 10, (16 * boxSize + 40)/3 + 310);
        TeamSettings.add(prev);
        
        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                back = true;
                gameStart(e);
            }
        });
        
        JButton next = new JButton("Next >");
        next.setSize(80, 20);
        next.setLocation((16 * boxSize + 40 + statsBar)/2 + 100, (16 * boxSize + 40)/3 + 310);
        TeamSettings.add(next);
        
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int count = countPlayers(numbers);
                Team team = game.createTeam(nameField.getText(), shortcutField.getText(), count);
                int i = findDoubleZero(numbers, count);
                if (i != -1) {
                    count--;
                    team.creatPlayer(numbers[i].getText(), names[i].getText(), lnames[i].getText());
                }
                i = findZero(numbers, count);
                if (i != -1) {
                    count--;
                    team.creatPlayer(numbers[i].getText(), names[i].getText(), lnames[i].getText());
                }
                int small = 0;
                for(int j = 0; j < count; j++) {
                    i = findNextSmallest(numbers, count, small);
                    small = Integer.parseInt(numbers[i].getText());
                    team.creatPlayer(numbers[i].getText(), names[i].getText(), lnames[i].getText());
                }
                if (isA == false) {
                    team.setColor(Color.white, Color.red);
                    gameGUI(e);
                }
                else {
                    team.setColor(Color.black, Color.blue);
                    isA = false;
                    teamSettings(e);
                }
            }
    });
        
        JButton exit = new JButton("Exit");
        exit.setSize(80, 20);
        exit.setLocation((16 * boxSize + 40 + statsBar)/2 + 230, (16 * boxSize + 40)/3 + 310);
        TeamSettings.add(exit);
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    private void boxScore() {
        game.newGame();
        t = new TimerThread(game, this);
        Player[] onCourtPlayersA = new Player[5];
        game.teamA.benchPlayers = this.game.teamA.getPlayers() - 5;
        Player[] benchPlayersA;
        Player[] onCourtPlayersB = new Player[5];
        game.teamB.benchPlayers = this.game.teamB.getPlayers() - 5;
        Player[] benchPlayersB;
        selectedPlayer = null;
        
        for(int i = 0; i < 5; i++) {
            onCourtPlayersA[i] = this.game.teamA.roster[i];
            onCourtPlayersA[i].subIn(game.actualTime);
        }
        if (game.teamA.benchPlayers > 0) {
            benchPlayersA = new Player[game.teamA.benchPlayers];
            for (int i = 0; i < game.teamA.benchPlayers; i++) {
                benchPlayersA[i] = this.game.teamA.roster[5 + i];
            }
        }
        else {
            benchPlayersA = null;
        }
        
        for(int i = 0; i < 5; i++) {
            onCourtPlayersB[i] = this.game.teamB.roster[i];
            onCourtPlayersB[i].subIn(game.actualTime);
        }
        if (game.teamB.benchPlayers > 0) {
            benchPlayersB = new Player[game.teamB.benchPlayers];
            for (int i = 0; i < game.teamB.benchPlayers; i++) {
                benchPlayersB[i] = this.game.teamB.roster[5 + i];
            }
        }
        else {
            benchPlayersB = null;
        }
        
        BoxScore = new JPanel();
        BoxScore.setPreferredSize(new Dimension(16 * boxSize + 40 + statsBar, 16 * boxSize + 40));
        BoxScore.setLayout(null);
        BoxScore.setBackground(Color.WHITE);
        add(BoxScore);
        RoundedPanel scorePanelA;
        if (game.teamA.primaryColor == Color.white) {
            scorePanelA = new RoundedPanel(game.teamA.secondaryColor);
        }
        else {
            scorePanelA = new RoundedPanel(game.teamA.primaryColor);
        }
        scorePanelA.setSize(150, 75);
        scorePanelA.setLayout(null);
        scorePanelA.setLocation((16 * boxSize + 40 + statsBar)/2 - 350, (16 * boxSize + 40)/3 - 250);
        scorePanelA.setBackground(game.teamA.primaryColor);
        BoxScore.add(scorePanelA);
        
        JLabel scoreLabelA = new JLabel(game.teamA.getScore(), SwingConstants.CENTER);
        scoreLabelA.setSize(150, 75);
        scoreLabelA.setLocation(0, 0);
        scoreLabelA.setFont(fontBig);
        if (game.teamA.primaryColor == Color.white) {
            scoreLabelA.setForeground(game.teamA.primaryColor);
        }
        else {
            scoreLabelA.setForeground(game.teamA.secondaryColor);
        }
        scorePanelA.add(scoreLabelA);
        
        RoundedPanel namePanelA;
        if (game.teamA.primaryColor == Color.white) {
            namePanelA = new RoundedPanel(game.teamA.secondaryColor);
        }
        else {
            namePanelA = new RoundedPanel(game.teamA.primaryColor);
        }
        namePanelA.setSize(150, 75);
        namePanelA.setLayout(null);
        namePanelA.setLocation((16 * boxSize + 40 + statsBar)/2 - 500, (16 * boxSize + 40)/3 - 250);
        namePanelA.setBackground(game.teamA.primaryColor);
        BoxScore.add(namePanelA);
        
        JLabel nameLabelA = new JLabel(game.teamA.shortcut, SwingConstants.CENTER);
        nameLabelA.setSize(150, 75);
        nameLabelA.setLocation(0, 0);
        nameLabelA.setFont(fontBig);
        if (game.teamA.primaryColor == Color.white) {
            nameLabelA.setForeground(game.teamA.primaryColor);
        }
        else {
            nameLabelA.setForeground(game.teamA.secondaryColor);
        }
        namePanelA.add(nameLabelA);
        
        RoundedPanel foulPanelA = new RoundedPanel(Color.BLACK);
        foulPanelA.setSize(75, 75);
        foulPanelA.setLayout(null);
        foulPanelA.setLocation((16 * boxSize + 40 + statsBar)/2 - 390, (16 * boxSize + 40)/3 - 170);
        BoxScore.add(foulPanelA);
        
        JLabel foulLabelA = new JLabel(String.valueOf(game.getFoul(true)), SwingConstants.CENTER);
        foulLabelA.setSize(75, 75);
        foulLabelA.setLocation(0, 0);
        foulLabelA.setFont(fontBig);
        foulLabelA.setForeground(Color.YELLOW);
        foulPanelA.add(foulLabelA);
        
        RoundedPanel timeoutPanelA = new RoundedPanel(Color.BLACK);
        timeoutPanelA.setSize(50, 50);
        timeoutPanelA.setLayout(null);
        timeoutPanelA.setLocation((16 * boxSize + 40 + statsBar)/2 - 310, (16 * boxSize + 40)/3 - 170);
        BoxScore.add(timeoutPanelA);
        
        JLabel timeoutLabelA = new JLabel(String.valueOf(game.teamA.getTimeout()), SwingConstants.CENTER);
        timeoutLabelA.setSize(50, 50);
        timeoutLabelA.setLocation(0, 0);
        timeoutLabelA.setFont(fontGui);
        timeoutLabelA.setForeground(Color.yellow);
        timeoutPanelA.add(timeoutLabelA);
        
        RoundedPanel timePanel = new RoundedPanel(Color.black);
        timePanel.setSize(150, 75);
        timePanel.setLayout(null);
        timePanel.setLocation((16 * boxSize + 40 + statsBar)/2 - 100, (16 * boxSize + 40)/3 - 250);
        timePanel.setBackground(Color.BLACK);
        BoxScore.add(timePanel);
        
        timeLabel = new JLabel(game.actualTime/60 + ":0" + game.actualTime%60, SwingConstants.CENTER);
        timeLabel.setSize(150, 75);
        timeLabel.setLocation(0, 0);
        timeLabel.setFont(fontBig);
        timeLabel.setForeground(Color.RED);
        timePanel.add(timeLabel);
        
        RoundedPanel quarterPanel = new RoundedPanel(Color.black);
        quarterPanel.setSize(75, 75);
        quarterPanel.setLayout(null);
        quarterPanel.setLocation((16 * boxSize + 40 + statsBar)/2 - 60, (16 * boxSize + 40)/3 - 175);
        BoxScore.add(quarterPanel);
        
        JLabel quarterLabel = new JLabel("1", SwingConstants.CENTER);
        quarterLabel.setSize(75, 75);
        quarterLabel.setLocation(0, 0);
        quarterLabel.setFont(fontBig);
        quarterLabel.setForeground(Color.yellow);
        quarterPanel.add(quarterLabel);
        
        JButton teamA = new JButton("Home");
        teamA.setSize(150, 75);
        teamA.setBackground(game.teamA.secondaryColor);
        teamA.setForeground(game.teamA.primaryColor);
        teamA.setFont(fontBig);
        teamA.setLocation((16 * boxSize + 40 + statsBar)/2 - 280, (16 * boxSize + 40)/3 - 80);
        BoxScore.add(teamA);
        
        teamA.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                isA = true;
                selectedTeam = true;
            }
        });
        
        startBtn = new JButton("Start");
        startBtn.setBackground(Color.green);
        startBtn.setSize(150, 75);
        startBtn.setLocation((16 * boxSize + 40 + statsBar)/2 - 100, (16 * boxSize + 40)/3 - 80);
        BoxScore.add(startBtn);
        
        t.start();
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                if (game.actualTime != 0) {
                    if (game.stop) {
                        startBtn.setText("Start");
                        startBtn.setBackground(Color.green);
                        game.stop = false;
                    }
                    else {
                        startBtn.setText("Stop");
                        startBtn.setBackground(Color.red);
                        game.stop = true;
                    }
                }
                else {
                    for(int i = 0; i < 5; i++) {
                        onCourtPlayersA[i].subOut(0);
                    }
                    if (game.nextQuarter()) {
                        quarterLabel.setText(String.valueOf(game.quarter));
                    }
                    else {
                        quarterLabel.setText("E");
                    }
                    for(int i = 0; i < 5; i++) {
                        onCourtPlayersA[i].subIn(game.actualTime);
                    }
                    startBtn.setText("Start");
                    startBtn.setBackground(Color.green);
                    foulLabelA.setText(String.valueOf(game.teamA.getFouls(game.quarter)));
                    if (3 < 4) {
                        foulLabelA.setForeground(Color.yellow);
                    }
                    timeoutLabelA.setText(String.valueOf(game.teamA.getTimeout()));
                    if (game.teamA.getTimeout() > 0) {
                        timeoutLabelA.setForeground(Color.yellow);
                    }
                    timeLabel.setText(game.actualTime/60 + ":0" + game.actualTime%60);
                    try {
                        output.saveBoxScore(e, true);
                    } catch (IOException ex) {
                        Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    game.stop = false;
                    t.start();
                }
            }
        });
        
        JButton teamB = new JButton("Away");
        teamB.setSize(150, 75);
        teamB.setBackground(game.teamB.secondaryColor);
        teamB.setForeground(game.teamB.primaryColor);
        teamB.setFont(fontBig);
        teamB.setLocation((16 * boxSize + 40 + statsBar)/2 + 80, (16 * boxSize + 40)/3 - 80);
        BoxScore.add(teamB);
        
        teamB.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                isA = false;
                selectedTeam = true;
            }
        });
        
        RoundedPanel scorePanelB;
        if (game.teamB.primaryColor == Color.white) {
            scorePanelB = new RoundedPanel(game.teamB.secondaryColor);
        }
        else {
            scorePanelB = new RoundedPanel(game.teamB.primaryColor);
        }
        scorePanelB.setSize(150, 75);
        scorePanelB.setLayout(null);
        scorePanelB.setLocation((16 * boxSize + 40 + statsBar)/2 + 190, (16 * boxSize + 40)/3 - 250);
        scorePanelB.setBackground(game.teamB.primaryColor);
        BoxScore.add(scorePanelB);
        
        JLabel scoreLabelB = new JLabel(game.teamB.getScore(), SwingConstants.CENTER);
        scoreLabelB.setSize(150, 75);
        scoreLabelB.setLocation(0, 0);
        scoreLabelB.setFont(fontBig);
        if (game.teamB.primaryColor == Color.white) {
            scoreLabelB.setForeground(game.teamB.primaryColor);
        }
        else {
            scoreLabelB.setForeground(game.teamB.secondaryColor);
        }
        scorePanelB.add(scoreLabelB);
        
        RoundedPanel namePanelB;
        if (game.teamB.primaryColor == Color.white) {
            namePanelB = new RoundedPanel(game.teamB.secondaryColor);
        }
        else {
            namePanelB = new RoundedPanel(game.teamB.primaryColor);
        }
        namePanelB.setSize(150, 75);
        namePanelB.setLayout(null);
        namePanelB.setLocation((16 * boxSize + 40 + statsBar)/2 + 340, (16 * boxSize + 40)/3 - 250);
        BoxScore.add(namePanelB);
        
        JLabel nameLabelB = new JLabel(game.teamB.shortcut, SwingConstants.CENTER);
        nameLabelB.setSize(150, 75);
        nameLabelB.setLocation(0, 0);
        nameLabelB.setFont(fontBig);
        if (game.teamB.primaryColor == Color.white) {
            nameLabelB.setForeground(game.teamB.primaryColor);
        }
        else {
            nameLabelB.setForeground(game.teamB.secondaryColor);
        }
        namePanelB.add(nameLabelB);
        
        RoundedPanel foulPanelB = new RoundedPanel(Color.BLACK);
        foulPanelB.setSize(75, 75);
        foulPanelB.setLayout(null);
        foulPanelB.setLocation((16 * boxSize + 40 + statsBar)/2 + 300, (16 * boxSize + 40)/3 - 170);
        BoxScore.add(foulPanelB);
        
        JLabel foulLabelB = new JLabel(game.teamB.getFouls(game.quarter), SwingConstants.CENTER);
        foulLabelB.setSize(75, 75);
        foulLabelB.setLocation(0, 0);
        foulLabelB.setFont(fontBig);
        foulLabelB.setForeground(Color.yellow);
        foulPanelB.add(foulLabelB);
        
        RoundedPanel timeoutPanelB = new RoundedPanel(Color.BLACK);
        timeoutPanelB.setSize(50, 50);
        timeoutPanelB.setLayout(null);
        timeoutPanelB.setLocation((16 * boxSize + 40 + statsBar)/2 + 245, (16 * boxSize + 40)/3 - 170);
        BoxScore.add(timeoutPanelB);
        
        JLabel timeoutLabelB = new JLabel(String.valueOf(game.teamB.timeouts), SwingConstants.CENTER);
        timeoutLabelB.setSize(50, 50);
        timeoutLabelB.setLocation(0, 0);
        timeoutLabelB.setFont(fontGui);
        timeoutLabelB.setForeground(Color.yellow);
        timeoutPanelB.add(timeoutLabelB);
        
        JButton[] courtPlayersButtonA = new JButton[5];
        
        for(int i = 0, c = -60; i < 5; i++, c += 70) {
            courtPlayersButtonA[i] = new JButton(onCourtPlayersA[i].number);
            courtPlayersButtonA[i].setBackground(game.teamA.primaryColor);
            courtPlayersButtonA[i].setForeground(game.teamA.secondaryColor);
            courtPlayersButtonA[i].setSize(70, 70);
            courtPlayersButtonA[i].setLocation((16 * boxSize + 40 + statsBar)/2 - 370, (16 * boxSize + 40)/3 + c);
            BoxScore.add(courtPlayersButtonA[i]);
        }
        courtPlayersButtonA[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = onCourtPlayersA[0].number;
                isA = true;
                selectedTeam = false;
            }
        });
        courtPlayersButtonA[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = onCourtPlayersA[1].number;
                isA = true;
                selectedTeam = false;
            }
        });
        
        courtPlayersButtonA[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = onCourtPlayersA[2].number;
                isA = true;
                selectedTeam = false;
            }
        });
        
        courtPlayersButtonA[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = onCourtPlayersA[3].number;
                isA = true;
                selectedTeam = false;
            }
        });
        
        courtPlayersButtonA[4].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = onCourtPlayersA[4].number;
                isA = true;
                selectedTeam = false;
            }
        });
        
        JButton[] benchPlayersButtonA = new JButton[7];
        
        for (int i = 0, c = -60; i < 7; i++, c += 70) {
            if (game.teamA.benchPlayers > i) {
                benchPlayersButtonA[i] = new JButton(benchPlayersA[i].number);
                benchPlayersButtonA[i].setBackground(game.teamA.secondaryColor);
                benchPlayersButtonA[i].setForeground(game.teamA.primaryColor);
            }
            else {
                benchPlayersButtonA[i] = new JButton("-");
                benchPlayersButtonA[i].setBackground(Color.gray);
            }
            benchPlayersButtonA[i].setSize(70, 70);
            benchPlayersButtonA[i].setLocation((16 * boxSize + 40 + statsBar)/2 - 450, (16 * boxSize + 40)/3 + c);
            BoxScore.add(benchPlayersButtonA[i]);
        }
        
        if (game.teamA.benchPlayers > 0) {
            benchPlayersButtonA[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamA.subOut(selectedPlayer, game.actualTime);
                        benchPlayersA[0].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersA);
                        Player player = benchPlayersA[0];
                        benchPlayersA[0] = onCourtPlayersA[index];
                        onCourtPlayersA[index] = player;
                        benchPlayersButtonA[0].setText(benchPlayersA[0].number);
                        courtPlayersButtonA[index].setText(onCourtPlayersA[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamA.benchPlayers > 1) {
            benchPlayersButtonA[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamA.subOut(selectedPlayer, game.actualTime);
                        benchPlayersA[1].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersA);
                        Player player = benchPlayersA[1];
                        benchPlayersA[1] = onCourtPlayersA[index];
                        onCourtPlayersA[index] = player;
                        benchPlayersButtonA[1].setText(benchPlayersA[1].number);
                        courtPlayersButtonA[index].setText(onCourtPlayersA[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamA.benchPlayers > 2) {
            benchPlayersButtonA[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamA.subOut(selectedPlayer, game.actualTime);
                        benchPlayersA[2].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersA);
                        Player player = benchPlayersA[2];
                        benchPlayersA[2] = onCourtPlayersA[index];
                        onCourtPlayersA[index] = player;
                        benchPlayersButtonA[2].setText(benchPlayersA[2].number);
                        courtPlayersButtonA[index].setText(onCourtPlayersA[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamA.benchPlayers > 3) {
            benchPlayersButtonA[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamA.subOut(selectedPlayer, game.actualTime);
                        benchPlayersA[3].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersA);
                        Player player = benchPlayersA[3];
                        benchPlayersA[3] = onCourtPlayersA[index];
                        onCourtPlayersA[index] = player;
                        benchPlayersButtonA[3].setText(benchPlayersA[3].number);
                        courtPlayersButtonA[index].setText(onCourtPlayersA[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamA.benchPlayers > 4) {
            benchPlayersButtonA[4].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamA.subOut(selectedPlayer, game.actualTime);
                        benchPlayersA[4].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersA);
                        Player player = benchPlayersA[4];
                        benchPlayersA[4] = onCourtPlayersA[index];
                        onCourtPlayersA[index] = player;
                        benchPlayersButtonA[4].setText(benchPlayersA[4].number);
                        courtPlayersButtonA[index].setText(onCourtPlayersA[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamA.benchPlayers > 5) {
            benchPlayersButtonA[5].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamA.subOut(selectedPlayer, game.actualTime);
                        benchPlayersA[5].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersA);
                        Player player = benchPlayersA[5];
                        benchPlayersA[5] = onCourtPlayersA[index];
                        onCourtPlayersA[index] = player;
                        benchPlayersButtonA[5].setText(benchPlayersA[5].number);
                        courtPlayersButtonA[index].setText(onCourtPlayersA[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamA.benchPlayers > 6) {
            benchPlayersButtonA[6].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamA.subOut(selectedPlayer, game.actualTime);
                        benchPlayersA[6].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersA);
                        Player player = benchPlayersA[6];
                        benchPlayersA[6] = onCourtPlayersA[index];
                        onCourtPlayersA[index] = player;
                        benchPlayersButtonA[6].setText(benchPlayersA[6].number);
                        courtPlayersButtonA[index].setText(onCourtPlayersA[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        JButton[] courtPlayersButtonB = new JButton[5];
        
        for(int i = 0, c = -60; i < 5; i++, c += 70) {
            courtPlayersButtonB[i] = new JButton(onCourtPlayersB[i].number);
            courtPlayersButtonB[i].setBackground(game.teamB.primaryColor);
            courtPlayersButtonB[i].setForeground(game.teamB.secondaryColor);
            courtPlayersButtonB[i].setSize(70, 70);
            courtPlayersButtonB[i].setLocation((16 * boxSize + 40 + statsBar)/2 + 250, (16 * boxSize + 40)/3 + c);
            BoxScore.add(courtPlayersButtonB[i]);
        }
        courtPlayersButtonB[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = onCourtPlayersB[0].number;
                isA = false;
                selectedTeam = false;
            }
        });
        courtPlayersButtonB[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = onCourtPlayersB[1].number;
                isA = false;
                selectedTeam = false;
            }
        });
        
        courtPlayersButtonB[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = onCourtPlayersB[2].number;
                isA = false;
                selectedTeam = false;
            }
        });
        
        courtPlayersButtonB[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = onCourtPlayersB[3].number;
                isA = false;
                selectedTeam = false;
            }
        });
        
        courtPlayersButtonB[4].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = onCourtPlayersB[4].number;
                isA = false;
                selectedTeam = false;
            }
        });
        
        JButton[] benchPlayersButtonB = new JButton[7];
        
        for (int i = 0, c = -60; i < 7; i++, c += 70) {
            if (game.teamB.benchPlayers > i) {
                benchPlayersButtonB[i] = new JButton(benchPlayersB[i].number);
                benchPlayersButtonB[i].setBackground(game.teamB.secondaryColor);
                benchPlayersButtonB[i].setForeground(game.teamB.primaryColor);
            }
            else {
                benchPlayersButtonB[i] = new JButton("-");
                benchPlayersButtonB[i].setBackground(Color.gray);
            }
            benchPlayersButtonB[i].setSize(70, 70);
            benchPlayersButtonB[i].setLocation((16 * boxSize + 40 + statsBar)/2 + 340, (16 * boxSize + 40)/3 + c);
            BoxScore.add(benchPlayersButtonB[i]);
        }
        
        if (game.teamB.benchPlayers > 0) {
            benchPlayersButtonB[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamB.subOut(selectedPlayer, game.actualTime);
                        benchPlayersB[0].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersB);
                        Player player = benchPlayersB[0];
                        benchPlayersB[0] = onCourtPlayersB[index];
                        onCourtPlayersB[index] = player;
                        benchPlayersButtonB[0].setText(benchPlayersB[0].number);
                        courtPlayersButtonB[index].setText(onCourtPlayersB[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamB.benchPlayers > 1) {
            benchPlayersButtonB[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamB.subOut(selectedPlayer, game.actualTime);
                        benchPlayersB[1].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersB);
                        Player player = benchPlayersB[1];
                        benchPlayersB[1] = onCourtPlayersB[index];
                        onCourtPlayersB[index] = player;
                        benchPlayersButtonB[1].setText(benchPlayersB[1].number);
                        courtPlayersButtonB[index].setText(onCourtPlayersB[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamB.benchPlayers > 2) {
            benchPlayersButtonB[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamB.subOut(selectedPlayer, game.actualTime);
                        benchPlayersB[2].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersB);
                        Player player = benchPlayersB[2];
                        benchPlayersB[2] = onCourtPlayersB[index];
                        onCourtPlayersB[index] = player;
                        benchPlayersButtonB[2].setText(benchPlayersB[2].number);
                        courtPlayersButtonB[index].setText(onCourtPlayersB[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamB.benchPlayers > 3) {
            benchPlayersButtonB[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamB.subOut(selectedPlayer, game.actualTime);
                        benchPlayersB[3].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersB);
                        Player player = benchPlayersB[3];
                        benchPlayersB[3] = onCourtPlayersB[index];
                        onCourtPlayersB[index] = player;
                        benchPlayersButtonB[3].setText(benchPlayersB[3].number);
                        courtPlayersButtonB[index].setText(onCourtPlayersB[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamB.benchPlayers > 4) {
            benchPlayersButtonB[4].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamB.subOut(selectedPlayer, game.actualTime);
                        benchPlayersB[4].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersB);
                        Player player = benchPlayersB[4];
                        benchPlayersB[4] = onCourtPlayersB[index];
                        onCourtPlayersB[index] = player;
                        benchPlayersButtonB[4].setText(benchPlayersB[4].number);
                        courtPlayersButtonB[index].setText(onCourtPlayersB[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamB.benchPlayers > 5) {
            benchPlayersButtonB[5].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamB.subOut(selectedPlayer, game.actualTime);
                        benchPlayersB[5].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersB);
                        Player player = benchPlayersB[5];
                        benchPlayersB[5] = onCourtPlayersB[index];
                        onCourtPlayersB[index] = player;
                        benchPlayersButtonB[5].setText(benchPlayersB[5].number);
                        courtPlayersButtonB[index].setText(onCourtPlayersB[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        if (game.teamB.benchPlayers > 6) {
            benchPlayersButtonB[6].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedPlayer != null) {
                        game.teamB.subOut(selectedPlayer, game.actualTime);
                        benchPlayersB[6].subIn(game.actualTime);
                        int index = findPlayer(selectedPlayer, onCourtPlayersB);
                        Player player = benchPlayersB[6];
                        benchPlayersB[6] = onCourtPlayersB[index];
                        onCourtPlayersB[index] = player;
                        benchPlayersButtonB[6].setText(benchPlayersB[6].number);
                        courtPlayersButtonB[index].setText(onCourtPlayersB[index].number);
                        selectedPlayer = null;
                    }
                }
            });
        }
        
        JButton FTM = new JButton("1+");
        FTM.setBackground(Color.green);
        FTM.setSize(70, 70);
        FTM.setLocation((16 * boxSize + 40 + statsBar)/2 - 370, (16 * boxSize + 40)/3 + 400);
        BoxScore.add(FTM);
        
        FTM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.score(isA, 1, selectedPlayer);
                    if (isA) 
                        scoreLabelA.setText(game.getPoints(isA));
                    else
                        scoreLabelB.setText(game.getPoints(isA));
                    selectedPlayer = null;
                }
            }
        });
        
        JButton FGM = new JButton("2+");
        FGM.setBackground(Color.green);
        FGM.setSize(70, 70);
        FGM.setLocation((16 * boxSize + 40 + statsBar)/2 - 300, (16 * boxSize + 40)/3 + 400);
        BoxScore.add(FGM);
        
        FGM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.score(isA, 2, selectedPlayer);
                    if (isA) 
                        scoreLabelA.setText(game.getPoints(isA));
                    else
                        scoreLabelB.setText(game.getPoints(isA));
                    selectedPlayer = null;
                }
            }
        });
        
        JButton TPM = new JButton("3+");
        TPM.setBackground(Color.green);
        TPM.setSize(70, 70);
        TPM.setLocation((16 * boxSize + 40 + statsBar)/2 - 230, (16 * boxSize + 40)/3 + 400);
        BoxScore.add(TPM);
        
        TPM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.score(isA, 3, selectedPlayer);
                    if (isA) 
                        scoreLabelA.setText(game.getPoints(isA));
                    else
                        scoreLabelB.setText(game.getPoints(isA));
                    selectedPlayer = null;
                }
            }
        });
        
        JButton foulG = new JButton("F+");
        foulG.setBackground(Color.green);
        foulG.setSize(70, 70);
        foulG.setLocation((16 * boxSize + 40 + statsBar)/2 - 160, (16 * boxSize + 40)/3 + 400);
        BoxScore.add(foulG);
        
        foulG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.gainedFoul(isA, selectedPlayer);
                    selectedPlayer = null;
                }
            }
        });
        
        JButton steal = new JButton("M+");
        steal.setBackground(Color.green);
        steal.setSize(70, 70);
        steal.setLocation((16 * boxSize + 40 + statsBar)/2 - 90, (16 * boxSize + 40)/3 + 400);
        BoxScore.add(steal);
        
        steal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.steal(isA, selectedPlayer);
                    selectedPlayer = null;
                }
            }
        });
        
        JButton tip_in = new JButton("Tip-in");
        tip_in.setBackground(Color.green);
        tip_in.setSize(70, 70);
        tip_in.setLocation((16 * boxSize + 40 + statsBar)/2 - 20, (16 * boxSize + 40)/3 + 400);
        BoxScore.add(tip_in);
        
        tip_in.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.of_rebound(isA, selectedPlayer);
                    game.score(isA, 2, selectedPlayer);
                    if (isA) 
                        scoreLabelA.setText(game.getPoints(isA));
                    else
                        scoreLabelB.setText(game.getPoints(isA));
                    selectedPlayer = null;
                }
            }
        });
        
        JButton assist = new JButton("As");
        assist.setBackground(Color.green);
        assist.setSize(70, 70);
        assist.setLocation((16 * boxSize + 40 + statsBar)/2 + 50, (16 * boxSize + 40)/3 + 400);
        BoxScore.add(assist);
        
        assist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.assist(isA, selectedPlayer);
                    selectedPlayer = null;
                }
            }
        });
        
        JButton blok = new JButton("Bl");
        blok.setBackground(Color.green);
        blok.setSize(70, 70);
        blok.setLocation((16 * boxSize + 40 + statsBar)/2 + 120, (16 * boxSize + 40)/3 + 400);
        BoxScore.add(blok);
        
        blok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.block(isA, selectedPlayer);
                    selectedPlayer = null;
                }
            }
        });
        
        JButton defReb = new JButton("DR");
        defReb.setBackground(Color.green);
        defReb.setSize(70, 70);
        defReb.setLocation((16 * boxSize + 40 + statsBar)/2 + 190, (16 * boxSize + 40)/3 + 400);
        BoxScore.add(defReb);
        
        defReb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.def_rebound(isA, selectedPlayer);
                    selectedPlayer = null;
                }
            }
        });
        
        JButton backBtn = new JButton("<<");
        backBtn.setBackground(Color.yellow);
        backBtn.setSize(70, 70);
        backBtn.setLocation((16 * boxSize + 40 + statsBar)/2 + 260, (16 * boxSize + 40)/3 + 400);
        BoxScore.add(backBtn);
        
        JButton FTN = new JButton("1-");
        FTN.setBackground(Color.red);
        FTN.setSize(70, 70);
        FTN.setLocation((16 * boxSize + 40 + statsBar)/2 - 370, (16 * boxSize + 40)/3 + 470);
        BoxScore.add(FTN);
        
        FTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.miss(isA, 1, selectedPlayer);
                    selectedPlayer = null;
                }
            }
        });
        
        JButton FGN = new JButton("2-");
        FGN.setBackground(Color.red);
        FGN.setSize(70, 70);
        FGN.setLocation((16 * boxSize + 40 + statsBar)/2 - 300, (16 * boxSize + 40)/3 + 470);
        BoxScore.add(FGN);
        
        FGN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.miss(isA, 2, selectedPlayer);
                    selectedPlayer = null;
                }
            }
        });
        
        JButton TPN = new JButton("3-");
        TPN.setBackground(Color.red);
        TPN.setSize(70, 70);
        TPN.setLocation((16 * boxSize + 40 + statsBar)/2 - 230, (16 * boxSize + 40)/3 + 470);
        BoxScore.add(TPN);
        
        TPN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.miss(isA, 3, selectedPlayer);
                    selectedPlayer = null;
                }
            }
        });
        
        JButton foulC = new JButton("F-");
        foulC.setBackground(Color.red);
        foulC.setSize(70, 70);
        foulC.setLocation((16 * boxSize + 40 + statsBar)/2 - 160, (16 * boxSize + 40)/3 + 470);
        BoxScore.add(foulC);
        
        foulC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.foul(isA, selectedPlayer);
                    if (isA) {
                        if (!(Integer.valueOf(game.getFoul(isA)) > 5)) {
                            foulLabelA.setText(game.getFoul(isA));
                            if ((game.getFoul(isA)).equals("4")) {
                                foulLabelA.setForeground(Color.red);
                            }
                        }
                    }
                    else {
                        if (!(Integer.valueOf(game.getFoul(isA)) > 5)) {
                            foulLabelB.setText(game.getFoul(isA));
                            if ((game.getFoul(isA)).equals("4")) {
                                foulLabelB.setForeground(Color.red);
                            }
                        }
                    }
                    selectedPlayer = null;
                }
            }
        });
        
        JButton turnover = new JButton("M-");
        turnover.setBackground(Color.red);
        turnover.setSize(70, 70);
        turnover.setLocation((16 * boxSize + 40 + statsBar)/2 - 90, (16 * boxSize + 40)/3 + 470);
        BoxScore.add(turnover);
        
        turnover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.turnover(isA, selectedPlayer);
                    selectedPlayer = null;
                }
            }
        });
        
        JButton foulOut = new JButton("F.Out");
        foulOut.setBackground(Color.red);
        foulOut.setSize(70, 70);
        foulOut.setLocation((16 * boxSize + 40 + statsBar)/2 - 20, (16 * boxSize + 40)/3 + 470);
        BoxScore.add(foulOut);
        
        foulOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.subIn(isA, selectedPlayer, game.actualTime);
                    if (isA) {
                        for(int i = game.teamA.benchPlayers - 1; i >= 0; i--) {
                            if (benchPlayersA[i] != null) {
                                game.teamA.benchPlayers--;
                                int index = findPlayer(selectedPlayer, onCourtPlayersA);
                                Player player = benchPlayersA[i];
                                benchPlayersA[i] = null;
                                onCourtPlayersA[index] = player;
                                courtPlayersButtonA[index].setText(onCourtPlayersA[index].number);
                                benchPlayersButtonA[i].setText("-");
                                benchPlayersButtonA[i].setBackground(Color.gray);
                                break;
                            }
                        }
                    }
                    selectedPlayer = null;
                }
            }
        });
        
        JButton technicalFoul = new JButton("TF-");
        technicalFoul.setBackground(Color.red);
        technicalFoul.setSize(70, 70);
        technicalFoul.setLocation((16 * boxSize + 40 + statsBar)/2 + 50, (16 * boxSize + 40)/3 + 470);
        BoxScore.add(technicalFoul);
        
        technicalFoul.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.foul(isA, selectedPlayer);
                    if (isA)
                        foulLabelA.setText(game.getFoul(isA));
                    else
                        foulLabelB.setText(game.getFoul(isA));
                    selectedPlayer = null;
                }
                else if (selectedTeam) {
                    if (isA)
                        game.teamA.foul(0, "COACH");
                    else
                        game.teamB.foul(0, "COACH");
                    selectedTeam = false;
                }
            }
        });
        
        JButton uFoul = new JButton("UF-");
        uFoul.setBackground(Color.red);
        uFoul.setSize(70, 70);
        uFoul.setLocation((16 * boxSize + 40 + statsBar)/2 + 120, (16 * boxSize + 40)/3 + 470);
        BoxScore.add(uFoul);
        
        uFoul.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.foul(isA, selectedPlayer);
                    if (isA)
                        foulLabelA.setText(game.getFoul(isA));
                    else
                        foulLabelB.setText(game.getFoul(isA));
                    selectedPlayer = null;
                }
            }
        });
        
        JButton offReb = new JButton("OR");
        offReb.setBackground(Color.green);
        offReb.setSize(70, 70);
        offReb.setLocation((16 * boxSize + 40 + statsBar)/2 + 190, (16 * boxSize + 40)/3 + 470);
        BoxScore.add(offReb);
        
        offReb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer != null) {
                    game.of_rebound(isA, selectedPlayer);
                    selectedPlayer = null;
                }
            }
        });
        
        JButton timeout = new JButton("TO");
        timeout.setBackground(Color.yellow);
        timeout.setSize(70, 70);
        timeout.setLocation((16 * boxSize + 40 + statsBar)/2 + 260, (16 * boxSize + 40)/3 + 470);
        BoxScore.add(timeout);
        
        timeout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isA && selectedTeam) {
                    if (game.teamA.getTimeout() != 0) {
                        game.teamA.takeTimeout();
                        timeoutLabelA.setText(String.valueOf(game.teamA.getTimeout()));
                        if (game.teamA.getTimeout() == 0) {
                            timeoutLabelA.setForeground(Color.red);
                        }
                    }
                }
                else if (selectedTeam) {
                    if (game.teamB.getTimeout() != 0) {
                        game.teamB.takeTimeout();
                        timeoutLabelB.setText(String.valueOf(game.teamB.getTimeout()));
                        if (game.teamB.getTimeout() == 0) {
                            timeoutLabelB.setForeground(Color.red);
                        }
                    }
                }
                selectedTeam = false;
            }
        });
        
        JButton out = new JButton("Generate boxscore");
        out.setSize(130, 20);
        out.setLocation((16 * boxSize + 40 + statsBar)/2 - 500, (16 * boxSize + 40)/3 - 280);
        BoxScore.add(out);
        
        out.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < 5; i++) {
                    onCourtPlayersA[i].subOut(game.actualTime);
                    onCourtPlayersA[i].subIn(game.actualTime);
                    onCourtPlayersB[i].subOut(game.actualTime);
                    onCourtPlayersB[i].subIn(game.actualTime);
                }
                try {
                    output.saveBoxScore(e, false);
                } catch (IOException ex) {
                    Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        JButton exit = new JButton("Exit");
        exit.setSize(80, 20);
        exit.setLocation((16 * boxSize + 40 + statsBar)/2 + 350, (16 * boxSize + 40)/3 + 530);
        BoxScore.add(exit);
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    /**
     * Counts how many players are on the team
     * 
     * @param players field of players, which are meant to be counted
     * @return number of players on the team
     */
    private int countPlayers (JTextField[] players) {
        int counter = 12;
        for(int i = 0; i < 12; i++) {
            if("".equals(players[i].getText())) {
                counter--;
            }
        }
        return counter;
    }
    
    /**
     * finds player with jersey number 0
     * 
     * @param players field of players, where might figure player with number 0
     * @param count total number of players on the team
     * @return index of player with number 0, or -1 if there is no such player
     */
    private int findZero (JTextField[] players, int count) {
        for(int i = 0; i < count; i++) {
            if("0".equals(players[i])) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Finds player in a field of players
     * 
     * @param courtNumber number of a player we are looking for
     * @param onCourt field of players
     * @return index of field of players
     */
    private int findPlayer (String number, Player[] players) {
        int i = 0;
        for(; i < 5; i++) {
            if (number.equals(players[i].number)) {
                break;
            }
        }
        return i;
    }
    
    /**
     * finds player with jersey number 00
     * 
     * @param players field of players, where might figure player with number 00
     * @param count total number of players on the team
     * @return index of player with number 00, or -1 if there is no such player
     */
    private int findDoubleZero (JTextField[] players, int count) {
        for(int i = 0; i < count; i++) {
            if("00".equals(players[i])) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * finds the smallest jersey number amongst players, in a specified range
     * 
     * @param players field of players, where we are searching for player numbers
     * @param count total count of players on team
     * @param range biggest player number, which is not meant to be found
     * @return 
     */
    private int findNextSmallest (JTextField[] players, int count, int range) {
        int smallest = 100;
        int index = -1;
        int next;
        for(int i = 0; i < count; i++) {
            next = Integer.parseInt(players[i].getText());
            if(next <= range) {
                continue;
            }
            if(next < smallest) {
                smallest = next;
                index = i;
            }
        }
        return index;
    }
    
    /**
     * updates board
     */
    public void update() {
        this.revalidate();
        this.repaint();
    }
}
