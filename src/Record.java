public class Record {
    private int score;
    private String date;
    private int time;
    private boolean obstaclesEnabled;

    public Record(String date, int score, int time, boolean obstaclesEnabled) {
        this.score = score;
        this.date = date;
        this.time = time;
        this.obstaclesEnabled = obstaclesEnabled;
    }

    @Override
    public String toString() {
        return "Record{" +
                "score=" + score +
                ", date=" + date +
                ", time=" + time +
                ", OBSTACLES_ENABLED=" + obstaclesEnabled +
                '}';
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isObstaclesEnabled() {
        return obstaclesEnabled;
    }

    public void setObstaclesEnabled(boolean obstaclesEnabled) {
        this.obstaclesEnabled = obstaclesEnabled;
    }
}
