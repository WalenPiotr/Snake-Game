import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class ScoreIO {

    private static  ObservableList<Record> records = FXCollections.observableArrayList();
    static ObservableList<Record> getRecords() {
        return records;
    }
    private static File directory = new File("C:\\Users\\walen\\Desktop\\Snake\\highscore.txt");

    static void loadScores() {
        records = FXCollections.observableArrayList();
        try {
            FileInputStream fileInputStream = new FileInputStream(directory);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            while ((line = bufferedReader.readLine()) != null) {
                try {

                    String[] strings = line.split(" ");
                    Date date = format.parse(line);
                    Integer score = Integer.parseInt(strings[2]);
                    Integer time = Integer.parseInt(strings[3]);
                    Boolean obstaclesEnabled = Boolean.parseBoolean(strings[4]);
                    records.add(new Record(format.format(date), score, time, obstaclesEnabled));

                } catch (ParseException e) {
                    System.out.println(e);
                }

            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }



    static void clearScores(){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(directory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert pw != null;
        pw.close();
    }


}
