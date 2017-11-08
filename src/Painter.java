import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.List;

class Painter {

    private Rectangle[][] rectangles = new Rectangle[Config.COLUMNS][Config.ROWS];
    private Snake snake;

    Painter(Snake snake) {
        this.snake = snake;
        initializeRectangles();
    }

    private void initializeRectangles() {
        for (int columnIndex = 0; columnIndex < Config.COLUMNS; columnIndex++) {



            for (int rowIndex = 0; rowIndex < Config.ROWS; rowIndex++) {
                initializeSingleRectangle(columnIndex, rowIndex);
            }
        }
    }

    private void initializeSingleRectangle(int columnIndex, int rowIndex) {
        int squareSize = Config.CELL_SIZE - 2 * Config.INSETS_SIZE;
        rectangles[columnIndex][rowIndex] = new Rectangle(squareSize, squareSize);
        rectangles[columnIndex][rowIndex].setFill(Config.backgroundPaint);
        rectangles[columnIndex][rowIndex].setArcWidth(Config.RECTANGLE_ARC_WIDTH);
        rectangles[columnIndex][rowIndex].setArcHeight(Config.RECTANGLE_ARC_HEIGHT);
    }

    Rectangle[][] getRectangles() {
        return rectangles;
    }

    void paintElements() {
        fillPoints(snake.getSnakePoints(), Config.snakePaint);
        Point head = snake.getSnakePoints().get(0);
        rectangles[head.getX()][head.getY()].setFill(Config.headPaint);
        fillPoints(snake.getApplePoints(), Config.foodPaint);
        fillPoints(snake.getObstaclesPoints(), Config.obstaclePaint);
    }

    void removeElements() {
        fillPoints(snake.getSnakePoints(), Config.backgroundPaint);
        fillPoints(snake.getApplePoints(), Config.backgroundPaint);
    }

    private void fillPoints(List<Point> points, Paint paint) {
        points.forEach(point -> {
            final int x = point.getX();
            final int y = point.getY();
            rectangles[x][y].setFill(paint);
        });
    }
}

