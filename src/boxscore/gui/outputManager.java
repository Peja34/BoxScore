package boxscore.gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import boxscore.game.Player;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;



/**
 *
 * @author Peja
 */
public class outputManager {
    gui g;
    
    public outputManager (gui g) {
        this.g = g;
    }
    
    public void loadRoster (ActionEvent event) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C://BoxScore//Data//rosters"));
        int result = fileChooser.showOpenDialog(this.g);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file for load: " + selectedFile.getAbsolutePath());
            this.load(selectedFile.getAbsolutePath());
        }
    }
    
    private void load (String filePath) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            char c = 0;
            boolean broken = false;
            line = br.readLine();
            this.g.nameField.setText(line);
            line = br.readLine();
            this.g.shortcutField.setText(line);
            for(int i = 0; i < 12; i++) {
                c = (char) br.read();
                if (Character.getNumericValue(c) > 9 || Character.getNumericValue(c) < 0) {
                    for(; i < 12; i++) {
                        this.g.numbers[i].setText("");
                        this.g.names[i].setText("");
                        this.g.lnames[i].setText("");
                    }
                    broken = true;
                    break;
                }
                line = br.readLine();
                line = String.join("", String.valueOf(c), line);
                this.g.numbers[i].setText(line);
                line = br.readLine();
                this.g.names[i].setText(line);
                line = br.readLine();
                this.g.lnames[i].setText(line);
            }
            line = br.readLine();
            if (broken) {
                line = String.join("", String.valueOf(c), line);
            }
            this.g.coachName.setText(line);
        }
    }
    
    public void saveRoster (ActionEvent event) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C://BoxScore//Data//rosters"));
        int result = fileChooser.showSaveDialog(this.g);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file for save: " + selectedFile.getAbsolutePath());
            this.save(selectedFile.getAbsolutePath());
        }
    }
    
    private void save (String filePath) throws IOException {
        List<String> lines;
        Path file = Paths.get(filePath);
        lines = Arrays.asList(this.g.nameField.getText() + "\n" + this.g.shortcutField.getText());
        Files.write(file, lines, Charset.forName("UTF-8"));
        for(int i = 0; i < 12; i++) {
            if (this.g.numbers[i].getText().equals("")) {
                break;
            }
            lines = Arrays.asList(this.g.numbers[i].getText() + "\n" + this.g.names[i].getText() + "\n" + this.g.lnames[i].getText());
            Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        }
        lines = Arrays.asList(this.g.coachName.getText());
        Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    }
    
    public void saveBoxScore (ActionEvent event, boolean autosave) throws IOException {
        if (autosave) {
            File selectedFile = new File("C://BoxScore//Data//boxscores//tmp.pdf");
            this.createTable(selectedFile.getAbsolutePath());
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C://BoxScore//Data//boxscores"));
        int result = fileChooser.showSaveDialog(this.g);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected place for new boxscore: " + selectedFile.getAbsolutePath());
            if (selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().length() - 4).equals(".pdf")) {
                this.createTable(selectedFile.getAbsolutePath());
            }
            else {
                this.createTable(selectedFile.getAbsolutePath() + ".pdf");
            }
        }
    }
    
    public void createTable(String path) throws FileNotFoundException, IOException {
        try {
            Document document = new Document();
            OutputStream outputStream = new FileOutputStream(new File(path));
            PdfWriter.getInstance(document, outputStream);
            document.open();
            
            Paragraph p = new Paragraph(this.g.game.teamA.name + " vs. " + this.g.game.teamB.name);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            
            p = new Paragraph(String.valueOf(this.g.game.teamA.total_points) + " : " + String.valueOf(this.g.game.teamB.total_points));
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            
            if (!this.g.game.venue.equals("")) {
                p = new Paragraph(this.g.game.venue + ", " + this.g.game.date + ", " + this.g.game.time);
                p.setAlignment(Element.ALIGN_CENTER);
                document.add(p);
            }
            
            if (!this.g.game.refs[0].equals("")) {
                p = new Paragraph(this.g.game.refs[0] + ", " + this.g.game.refs[1]);
                p.setAlignment(Element.ALIGN_CENTER);
                if (this.g.game.refs[2] != null) {
                    p.add(", " + this.g.game.refs[2]);
                }
                document.add(p);
            }
            
            
            if (this.g.game.refs[3] != null) {
                p = new Paragraph("Commisioner: " + this.g.game.refs[3]);
                p.setAlignment(Element.ALIGN_CENTER);
                document.add(p);
            }
            document.add(Chunk.SPACETABBING);
            
            PdfPTable qTable = new PdfPTable(6);
            qTable.setWidthPercentage(35);
            PdfPCell qCell = new PdfPCell();
            qCell.setColspan(1);
            qTable = addSpecCell(qTable, qCell, "Qtr");
            for (int i = 1; i < 5; i++) {
                qTable = addSpecCell(qTable, qCell, String.valueOf(i));
            }
            qTable = addSpecCell(qTable, qCell, "OT");
            document.add(qTable);
            qTable = new PdfPTable(6);
            qTable.setWidthPercentage(35);
            qTable = addSpecCell(qTable, qCell, this.g.game.teamA.shortcut);
            for (int i = 1; i < 6; i++) {
                qTable = addSpecCell(qTable, qCell, this.g.game.getQPoints(true, i));
            }
            document.add(qTable);
            qTable = new PdfPTable(6);
            qTable.setWidthPercentage(35);
            qTable = addSpecCell(qTable, qCell, this.g.game.teamB.shortcut);
            for (int i = 1; i < 6; i++) {
                qTable = addSpecCell(qTable, qCell, this.g.game.getQPoints(false, i));
            }
            document.add(qTable);
            
            p = new Paragraph(this.g.game.teamA.name);
            document.add(p);
            document.add(Chunk.SPACETABBING);
            
            PdfPTable table = new PdfPTable(30);
            table.setWidthPercentage(100);
            PdfPCell nameCell = new PdfPCell();
            nameCell.setColspan(4);
            PdfPCell shootCell = new PdfPCell();
            shootCell.setColspan(2);
            PdfPCell statCell = new PdfPCell();
            statCell.setColspan(1);
            table = addSpecCell(table, statCell, "no");
            table = addSpecCell(table, nameCell, "Name");
            table = addSpecCell(table, shootCell, "time");
            table = addSpecCell(table, shootCell, "FG");
            table = addSpecCell(table, shootCell, "FG%");
            table = addSpecCell(table, shootCell, "3P");
            table = addSpecCell(table, shootCell, "3P%");
            table = addSpecCell(table, shootCell, "FT");
            table = addSpecCell(table, shootCell, "FT%");
            table = addSpecCell(table, statCell, "or");
            table = addSpecCell(table, statCell, "dr");
            table = addSpecCell(table, statCell, "tr");
            table = addSpecCell(table, statCell, "bl");
            table = addSpecCell(table, statCell, "as");
            table = addSpecCell(table, statCell, "st");
            table = addSpecCell(table, statCell, "to");
            table = addSpecCell(table, statCell, "f+");
            table = addSpecCell(table, statCell, "f-");
            table = addSpecCell(table, statCell, "v");
            table = addSpecCell(table, statCell, "p");
            document.add(table);
            for (int i = 0; i < this.g.game.teamA.team_members; i++) {
                table = addPlayer(true, i, new PdfPTable(30), nameCell, shootCell, statCell);
                document.add(table);
            }
            
            document.add(Chunk.NEWLINE);
            p = new Paragraph(this.g.game.teamB.name);
            document.add(p);
            document.add(Chunk.SPACETABBING);
            
            table = new PdfPTable(30);
            table.setWidthPercentage(100);
            table = addSpecCell(table, statCell, "no");
            table = addSpecCell(table, nameCell, "Name");
            table = addSpecCell(table, shootCell, "time");
            table = addSpecCell(table, shootCell, "FG");
            table = addSpecCell(table, shootCell, "FG%");
            table = addSpecCell(table, shootCell, "3P");
            table = addSpecCell(table, shootCell, "3P%");
            table = addSpecCell(table, shootCell, "FT");
            table = addSpecCell(table, shootCell, "FT%");
            table = addSpecCell(table, statCell, "or");
            table = addSpecCell(table, statCell, "dr");
            table = addSpecCell(table, statCell, "tr");
            table = addSpecCell(table, statCell, "bl");
            table = addSpecCell(table, statCell, "as");
            table = addSpecCell(table, statCell, "st");
            table = addSpecCell(table, statCell, "to");
            table = addSpecCell(table, statCell, "f+");
            table = addSpecCell(table, statCell, "f-");
            table = addSpecCell(table, statCell, "v");
            table = addSpecCell(table, statCell, "p");
            document.add(table);
            for (int i = 0; i < this.g.game.teamA.team_members; i++) {
                table = addPlayer(false, i, new PdfPTable(30), nameCell, shootCell, statCell);
                document.add(table);
            }
            
            document.close();
            outputStream.close();
            System.out.println("Pdf creation complete");
        } catch (DocumentException ex) {
            Logger.getLogger(outputManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private PdfPTable addSpecCell (PdfPTable table, PdfPCell cell, String name) {
        cell.setPhrase(new Phrase(name));
        table.addCell(cell);
        return table;
    }
    
    private PdfPTable addPlayer(boolean isA, int i, PdfPTable table, PdfPCell nameCell, PdfPCell shootCell, PdfPCell statCell) {
        table.setWidthPercentage(100);
        Player player;
        if (isA) {
            player = this.g.game.teamA.getIPlayer(i);
        }
        else {
            player = this.g.game.teamB.getIPlayer(i);
        }
        table = addSpecCell(table, statCell, player.number);
        table = addSpecCell(table, nameCell, player.getName());
        if (player.timeTotal == 0) {
            PdfPCell dnp = new PdfPCell();
            dnp.setColspan(26);
            table = addSpecCell(table, dnp, "DNP");
            return table;
        }
        String tmp = String.valueOf(player.timeTotal/60) + ":" + String.valueOf(player.timeTotal%60);
        table = addSpecCell(table, shootCell, tmp);
        tmp = String.valueOf(player.fgm) + "/" + String.valueOf(player.fga);
        table = addSpecCell(table, shootCell, tmp);
        if (player.fga == 0) {
            tmp = "0.0";
        }
        else {
            tmp = String.valueOf(((float) player.fgm / (float) player.fga) * 100);
        }
        table = addSpecCell(table, shootCell, tmp);
        tmp = String.valueOf(player.tpm) + "/" + String.valueOf(player.tpa);
        table = addSpecCell(table, shootCell, tmp);
        if (player.tpa == 0) {
            tmp = "0.0";
        }
        else {
            tmp = String.valueOf(((float) player.tpm / (float) player.tpa) * 100);
        }
        table = addSpecCell(table, shootCell, tmp);
        tmp = String.valueOf(player.ftm) + "/" + String.valueOf(player.fta);
        table = addSpecCell(table, shootCell, tmp);
        if (player.fta == 0) {
            tmp = "0.0";
        }
        else {
            tmp = String.valueOf(((float) player.ftm / (float) player.fta) * 100);
        }
        table = addSpecCell(table, shootCell, tmp);
        table = addSpecCell(table, statCell, String.valueOf(player.of_rebounds));
        table = addSpecCell(table, statCell, String.valueOf(player.def_rebounds));
        table = addSpecCell(table, statCell, String.valueOf(player.getTotalRebounds()));
        table = addSpecCell(table, statCell, String.valueOf(player.blocks));
        table = addSpecCell(table, statCell, String.valueOf(player.assists));
        table = addSpecCell(table, statCell, String.valueOf(player.steals));
        table = addSpecCell(table, statCell, String.valueOf(player.turnovers));
        table = addSpecCell(table, statCell, String.valueOf(player.foulsGained));
        table = addSpecCell(table, statCell, String.valueOf(player.fouls));
        table = addSpecCell(table, statCell, String.valueOf(player.getValidity()));
        table = addSpecCell(table, statCell, String.valueOf(player.points));
        return table;
    }
}
