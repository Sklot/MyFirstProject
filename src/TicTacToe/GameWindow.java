package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 1 наследуемся от JFrame
class GameWindow extends JFrame {

    // 5 создаем размеры окон и начальные координаты
    private static final int WIN_HEIGHT = 555; // высота окна
    private static final int WIN_WIDTH = 507; // ширина окна
    private static final int WIN_POS_X = 800; // начальная координата
    private static final int WIN_POS_Y = 300; // начальная координата

    // 13 создаем константы наших классов
    private static StartNewGameWindow startNewGameWindow;
    private static Map field;

    GameWindow() {
        // 4 при закрытии окна прерашение работы программы
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 6 устанавливаем размеры
        setBounds(WIN_POS_X, WIN_POS_Y, WIN_WIDTH, WIN_HEIGHT);

        // 2 задаем заголовок
        setTitle("Tic Tac Toe");

        // 7 запрещаем изменение размеров окна
        setResizable(false);

        // 15 создаем наше новое окно и указываем ему координаты первого окна с помощью "this"
        startNewGameWindow = new StartNewGameWindow(this);

        // 9 создаем панель гридов 1 строка 2 столбца
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        // 8 создаем кнопку старт игры
        JButton btnNewGame = new JButton("Start new game");
        // 17 создаем обработчик событий
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGameWindow.setVisible(true);
            }
        });
        // 9.1 - добавляем кнопку в панель
        bottomPanel.add(btnNewGame);

        // 8.1 создаем кнопку выход из игры
        JButton btnExit = new JButton("Exit game");
        // 17.1 создаем обработчик событий
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // 9.1 - добавляем кнопку в панель
        bottomPanel.add(btnExit);

        // 14 добавляем поле в наше окно
        field = new Map();
        add(field, BorderLayout.CENTER);

        // 9.2 добавляем панель к нашему окну
        add(bottomPanel, BorderLayout.SOUTH);
        // 3 делаем окно видимым
        setVisible(true);
    }

    // 17 старт игры будет происходить из дополнительного окна, а этот метод в основом окне, связываем два этих окна (лучше нарисовать картинку)
    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLength) {
        field.startNewGame(mode, fieldSizeX, fieldSizeY, winLength);
    }

}