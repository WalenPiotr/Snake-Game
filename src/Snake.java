import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.Map.entry;

class Snake {


    private boolean crashed;
    private int score = 0;
    private long timeStamp = System.currentTimeMillis();

    void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    long getTimeStamp() {
        return timeStamp;
    }

    int getScore() {
        return score;
    }

    boolean isCrashed() {
        return crashed;
    }

    void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    private Direction newDirection = Direction.EAST;
    private Direction direction = Direction.EAST;
    private List<Point> snakePoints = new ArrayList<>();
    private List<Point> applePoints = new ArrayList<>();
    private List<Point> obstaclesPoints = new ArrayList<>();
    private Map<Direction, Point> directionPointMap = Map.ofEntries(
            entry(Direction.WEST, new Point(-1, 0)),
            entry(Direction.EAST, new Point(1, 0)),
            entry(Direction.NORTH, new Point(0, -1)),
            entry(Direction.SOUTH, new Point(0, 1))
    );
    private final Random random = new Random();

    Snake() {
        initialize();
    }

    void setScore(int score) {
        this.score = score;
    }

    private void initializeFood() {
        int apples = Config.NUMBER_OF_APPLES;
        Random random = new Random();
        while (apples > 0) {
            Point point = new Point(random.nextInt(Config.MAX_X), random.nextInt(Config.MAX_Y));
            if (!(isOccupied(point))) {
                applePoints.add(point);
                apples--;
            }
        }
    }

    void initialize() {
        snakePoints = new ArrayList<>();
        applePoints = new ArrayList<>();
        obstaclesPoints = new ArrayList<>();
        if (Config.OBSTACLES_ENABLED) initializeObstacles();
        initializeSnake();
        initializeFood();
        setCrashed(false);
        setNewDirection(Direction.EAST);
        setScore(0);
        setTimeStamp(System.currentTimeMillis());
    }


    private void initializeSnake() {
        IntStream.range(0, Config.INITIAL_SNAKE_LENGTH)
                .mapToObj(i -> new Point(Config.INITIAL_X_POSITION - i, Config.INITIAL_Y_POSITION))
                .forEach(point -> snakePoints.add(point));
    }

    private void initializeObstacles() {
        IntStream.rangeClosed(0, Config.MAX_X).forEach(x -> {
            obstaclesPoints.add(new Point(x, 0));
            obstaclesPoints.add(new Point(x, Config.MAX_Y));
        });
        IntStream.range(1, Config.MAX_Y).forEach(y -> {
            obstaclesPoints.add(new Point(0, y));
            obstaclesPoints.add(new Point(Config.MAX_X, y));
        });
    }


    void setNewDirection(Direction newDirection) {
        this.newDirection = newDirection;
    }

    Direction getDirection() {
        return direction;
    }

    List<Point> getSnakePoints() {
        return snakePoints;
    }

    List<Point> getApplePoints() {
        return applePoints;
    }

    List<Point> getObstaclesPoints() {
        return obstaclesPoints;
    }

    private Point getHead() {
        return snakePoints.get(0);
    }

    void step() {
        Point newHead = getNewHead(newDirection);
        if (!newHead.equals(snakePoints.get(snakePoints.size() - 1)))
            if (snakeContains(newHead) || obstaclesContains(newHead)) {
                crashed = true;
            }
        if (!crashed) {
            if (foodContains(newHead)) {
                insertAtFirst(newHead);
                removeApple(newHead);
                generateFood();
                score++;
            } else {
                removeLast();
                insertAtFirst(newHead);
            }
        }
        direction = newDirection;
    }

    private void insertAtFirst(Point point) {
        snakePoints.add(0, point);
    }

    private void removeLast() {
        snakePoints.remove(snakePoints.size() - 1);
    }

    private Point getNewHead(Direction direction) {
        Point newHead = new Point(getHead());
        newHead.add(directionPointMap.get(direction));
        newHead.limit(Config.MAX_X, Config.MAX_Y);
        return newHead;
    }

    private boolean foodContains(Point point) {
        return applePoints
                .stream()
                .anyMatch(applePoint -> applePoint.equals(point));
    }

    private boolean snakeContains(Point point) {
        return snakePoints
                .stream()
                .anyMatch(snakePoint -> snakePoint.equals(point));
    }

    private boolean obstaclesContains(Point point) {
        return obstaclesPoints
                .stream()
                .anyMatch(obstaclesPoint -> obstaclesPoint.equals(point));
    }

    private void generateFood() {
        boolean found = false;
        int newX = random.nextInt(Config.MAX_X);
        int newY = random.nextInt(Config.MAX_Y);
        Point point = new Point(newX, newY);
        int counterX = 0;
        int counterY = 0;
        while (!found && counterX <= Config.MAX_X) {
            point.setX(newX);
            while (!found && counterY <= Config.MAX_Y) {
                point.setY(newY);

                if (!isOccupied(point)) {
                    applePoints.add(point);
                    found = true;
                }
                newY++;
                newY = newY % (Config.MAX_Y + 1);
                counterY++;
            }
            newX++;
            newX = newX % (Config.MAX_X + 1);
            counterY = 0;
            counterX++;
        }
    }

    private boolean isOccupied(Point point) {
        return snakeContains(point) || foodContains(point) || obstaclesContains(point);
    }

    private void removeApple(Point head) {
        int index = 0;
        for (int i = 0; i < applePoints.size(); i++) {
            if (applePoints.get(i).equals(head)) {
                index = i;
            }
        }
        applePoints.remove(index);
    }
}
