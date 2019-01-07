package TicTacToe;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private static Random rand = new Random();

    public static final int MODE_H_V_A = 0;
    public static final int MODE_H_V_H = 1;

    private static final int EMPTY_DOT = 0;
    private static final int HUMAN_DOT = 1;
    private static final int AI_DOT = 2;

    private static final int DRAW = 0;
    private static final int HUMAN_WIN = 1;
    private static final int AI_WIN = 2;

    private static final int DOTS_MARGIN = 4;
    private static final String DRAW_MSG = "Ничья";
    private static final String HUMAN_WIN_MSG = "Выиграл игрок";
    private static final String AI_WIN_MSG = "Выиграл Скайнет";
    // 24.1 чтобы заполнить поле
    int[][] field;
    int fieldSizeX;
    int fieldSizeY;
    int winLeght;
    // 25 высота и ширина каждоый ячейки
    int cellheight;
    int cellWidth;
    private boolean game_over;
    private int game_over_state;
    private final Font font = new Font("Times new roman", Font.BOLD, 48);

    // 27 если ничего не нарисовано
    boolean isInitialized = false;

    // 10 создаем конструктор и задаем цвет поля
    Map() {
        setBackground(Color.lightGray);
        // 30 создаем слушателя шелчка мышки
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
    }

    // 31 создаем метод который определяем куда челкнули
    void update(MouseEvent e) {
        if (game_over || !isInitialized) return;
        // пиксели делим на ширину и высоту
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellheight;
        if (!isCellEmpty(cellX, cellY)) return;
        field[cellY][cellX] = HUMAN_DOT;
//        System.out.println("x: " + cellX + " y: " + cellY);
        // после каждого действия перерисовываем
        repaint();
        if (checkWin(HUMAN_DOT)) {
            game_over_state = HUMAN_WIN;
            game_over = true;
            return;
        }
        if(isFieldFull()){
            game_over_state = DRAW;
            game_over = true;
            return;
        }
        aiTurn();
        repaint();
        if(checkWin(AI_DOT)){
            game_over_state = AI_WIN;
            game_over = true;
            return;
        }
        if(isFieldFull()){
            game_over_state = DRAW;
            game_over = true;
        }
    }

    // 24 метод для рисования нашего поля вцелом
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    // 11 создаем метод который говорит о типе игры, размеры поля, и выигрышная длина
    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLength) {

        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLeght = winLength;
        field = new int[fieldSizeY][fieldSizeX];
        game_over = false;
        isInitialized = true;
        repaint();
    }

    // 24.1 метод для рисование
    void render(Graphics g) {
        if (!isInitialized) return;

        int panelWidth = getWidth();
        int panelHeigt = getHeight();
        cellheight = panelHeigt / fieldSizeY;
        cellWidth = panelWidth / fieldSizeX;

        // 26 отрисовываем по Y (горизонтальные полоски)
        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellheight;
            g.drawLine(0, y, panelWidth, y);
        }

        // 29 отрисовываем по X (вертикальные полоски)
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, panelHeigt);
        }
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isCellValid(j, i) && isCellEmpty(j, i)) continue;
                if (field[i][j] == HUMAN_DOT) {
                    g.setColor(Color.BLUE);
                } else if (field[i][j] == AI_DOT) {
                    g.setColor(Color.RED);
                } else {
                    throw new RuntimeException("Что-то пошло не так!");
                }
                g.fillOval(j * cellWidth + DOTS_MARGIN, i * cellheight + DOTS_MARGIN,
                        cellWidth - 2 * DOTS_MARGIN, cellheight - 2 * DOTS_MARGIN);
            }
        }
        if(game_over) showGameOver(g);
    }

    private void showGameOver(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 200, getWidth(), 70);
        g.setColor((Color.yellow));
        g.setFont(font);
        switch (game_over_state) {
            case DRAW:
                g.drawString(DRAW_MSG, 180,getHeight()/2);
                break;
            case HUMAN_WIN:
                g.drawString(HUMAN_WIN_MSG, 70,getHeight()/2);
                break;
            case AI_WIN:
                g.drawString(AI_WIN_MSG, 20,getHeight()/2);
                break;
            default:
                throw new RuntimeException("----");
        }
    }

         void aiTurn() {
        if (checkAi()) return;
        if (checkHuman()) return;
        int x;
        int y;
        do {
            x = rand.nextInt(fieldSizeX);
            y = rand.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[y][x] = AI_DOT;
    }


     boolean isFieldFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == EMPTY_DOT) {
                    return false;
                }
            }

        }
        return true;
    }
//
    boolean checkWin(int dot) {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (checkLine(i, j, 0, 1, winLeght, dot)) return true;
                if (checkLine(i, j, 1, 1, winLeght, dot)) return true;
                if (checkLine(i, j, 1, 0, winLeght, dot)) return true;
                if (checkLine(i, j, -1, 1, winLeght, dot)) return true;
            }
        }
        return false;
    }

    //
    boolean checkLine(int x, int y, int vx, int vy, int leng, int dot) {
        int far_x = x + (leng - 1) * vx;
        int far_y = y + (leng - 1) * vy;
        if (!isCellValid(far_x, far_y)) return false;
        for (int i = 0; i < leng; i++) {
            if (field[y + i * vy][x + i * vx] != dot) return false;
        }
        return true;
    }


     boolean checkAi() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isCellEmpty(j, i)) {
                    field[i][j] = AI_DOT;
                    if (checkWin(AI_DOT)) return true;
                    field[i][j] = EMPTY_DOT;
                }
            }
        }
        return false;
    }

     boolean checkHuman() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isCellEmpty(j, i)) {
                    field[i][j] = HUMAN_DOT;
                    if (checkWin(HUMAN_DOT)) {
                        field[i][j] = AI_DOT;
                        return true;
                    }
                    field[i][j] = EMPTY_DOT;
                }
            }
        }
        return false;
    }
    boolean isCellValid(int x, int y) {
        if ((x >= 0 && x < fieldSizeX) && (y >= 0 && y < fieldSizeY)) {
            return true;
        }
        return false;
    }

    boolean isCellEmpty(int x, int y) {
        if (field[y][x] == EMPTY_DOT) {
            return true;
        }
        return false;
    }
}
