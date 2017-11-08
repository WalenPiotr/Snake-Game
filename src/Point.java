class Point {
    private int x;
    private int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point(Point point){
        this.x = point.getX();
        this.y = point.getY();
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    boolean equals(Point point) {
        return x == point.x && y == point.y;
    }

    void add(Point point) {
        this.x += point.getX();
        this.y += point.getY();

    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    void limit(int maxX, int maxY) {
        if(x < 0) x = maxX;
        else if(y < 0) y = maxY;
        else if(x > maxX) x = 0;
        else if(y > maxY) y = 0;
    }
}
