package TicTacToe;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StartNewGameWindow extends JFrame{

    // 16 переменная нового окна
    private final GameWindow gameWindow;

    // 18 объявляем параметры окна
    private static final int WIN_HEIGHT = 230; // высота
    private static final int WIN_WIDTH = 350; // ширина
    private static final int MIN_WIN_LEN = 3; // минимальная выйграшная длина
    private static final int MIN_FIELD_SIZE = 3; // минимальный размер поля
    private static final int MAX_FIELD_SIZE = 10; // максимальный размер поля
    private static final int MAX_WIN_LEN = 10; // максимальная выйграшная длина
    private static final String STR_WIN_LEN = "Winning Length: "; // сообщение о победе
    private static final String STR_FIELD_SIZE = "Field Size: "; // сообщение о размере поля

    private JRadioButton jrbHumVsAi = new JRadioButton("Human vs. AI", true); // указываем что это кнопка выбрана при старте
    private JRadioButton jrbHumVsHum = new JRadioButton("Human vs. Human");
    // 22.3 радио кнопки нужно добавить в группу, чтобы можно было выбрать только 1
    private ButtonGroup gameMode = new ButtonGroup();

    // 23.2 создаем слайдер
    private JSlider slFieldSize;
    private JSlider slWinLength;

    // 12 создаем окошко в центре нашего первого окна(только пустой конструктор)
    StartNewGameWindow(GameWindow gameWindow) {
        // 16.1 даем параметры первого окна
        this.gameWindow = gameWindow;
        // 19 задаем размеры
        setSize(WIN_WIDTH, WIN_HEIGHT);
        // 20 вычисляем центр поля с помощью класса прямоугольник Rectangle
        Rectangle gameWindowBounds = gameWindow.getBounds();
        // 20.2 берем центр стороны первого окна - половина стороны от второго окна, чтобы установить окно поцентру
        int posX = (int)gameWindowBounds.getCenterX() - WIN_WIDTH / 2;
        int posY = (int)gameWindowBounds.getCenterY() - WIN_HEIGHT / 2;
        // 20.1 и задаем координаты
        setLocation(posX, posY);
        // 20.3 задаем загаловок
        setTitle("New game parameters");

        // 21 задаем 10 строчек и 1 колонка
        setLayout(new GridLayout(10, 1));

        // 22 добавляем настроечный контрол
        addGameContolsMode();
        // 23 добавляем слайдер
        addGameControlsFieldWinLenght();

        JButton btnStartGame = new JButton("Start a game");
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStartGameClick();
            }
        });
        add(btnStartGame);
    }

    void btnStartGameClick() {
        int gameMode;
        if(jrbHumVsAi.isSelected())
            gameMode = Map.MODE_H_V_A;
        else if(jrbHumVsHum.isSelected())
            gameMode = Map.MODE_H_V_H;
        else
            throw new RuntimeException("No buttons selected");

        int fieldSize = slFieldSize.getValue();
        int winLength = slWinLength.getValue();
        gameWindow.startNewGame(gameMode, fieldSize, fieldSize, winLength);
        setVisible(false);
    }

    // 22.1 создаем контрол
    void addGameContolsMode() {
        add(new JLabel("Choose gaming mode:"));
        // 22.2 добавляем радио кнопки и заголовок
        gameMode.add(jrbHumVsAi);
        gameMode.add(jrbHumVsHum);
        add(jrbHumVsHum);
        add(jrbHumVsAi);
    }

    // 23.1 создаем слайдер
    void addGameControlsFieldWinLenght() {
        // лайбел будет менять поэтому размер + число
        add(new JLabel("Choose field size:"));
        final JLabel lblFieldSize = new JLabel(STR_FIELD_SIZE + MIN_FIELD_SIZE);
        add(lblFieldSize);


        slFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        // вешаем слушателя
        slFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // это потом, берем текущее значение
                int currentFieldSize = slFieldSize.getValue();
                // меняем значение на лейбле
                lblFieldSize.setText(STR_FIELD_SIZE + currentFieldSize);
                // меняем значение максимальной выйграшной длины
                // выйграшная длина не может быть больше размеров поля
                slWinLength.setMaximum(currentFieldSize);
            }
        });
        // добавляем слайдер
        add(slFieldSize);

        add(new JLabel("Choose winning length:"));
        final JLabel lblWinLen = new JLabel(STR_WIN_LEN + MIN_WIN_LEN);
        add(lblWinLen);

        // 23.3 заполняем слайдер (минимальное значение, максимальное и текущее)
        slWinLength = new JSlider(MIN_WIN_LEN, MAX_WIN_LEN, MIN_WIN_LEN);
        // вешаем слушателя
        slWinLength.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // отображем изменение нашего слайдера
                lblWinLen.setText(STR_WIN_LEN + slWinLength.getValue());
            }
        });
        // добавляем слайдер
        add(slWinLength);
    }

}