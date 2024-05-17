package Controller;

import AI.AlbinosAI;
import AI.BasicAI;
import Rabbits.*;
import Template.Singleton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Habitat {
    private static int width = 800;
    private static int height = 1000;
    private int interval = 1000;
    private int intervalBasic = 1000;
    private int intervalAlbinos = 1000;
    private int time;
    private Timer timer, timerBasic, timerAlbinos, timerRepaint;
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JLabel textBasicRabbits;
    private JLabel textAlbinosRabbits;
    private boolean timeLabel = true;
    private boolean booleanBasicRab = false;
    private boolean booleanAlbinosRab = false;
    private Singleton rabbits;
    private Random random;
    private JButton startBut, stopBut;
    private boolean isStarting = false;
    private JCheckBox checkBox;
    private JRadioButton showTimeRadio, hideTimeRadio;
    private double basicChanche = 0.7, albinosChanche = 0.5;
    private boolean flagForInfo;
    private JTextField inputForFirst, inputForSecond, inputTimeLifeBasic, inputTimeLifeAlbinos;
    private int timeLifeBasic = 7;
    private int timeLifeAlbinos = 10;
    private BasicAI basicAI;
    private AlbinosAI albinosAI;
    private boolean basicAIIsStarted = true;
    private boolean albinosAIIsStarted = true;

    public static void main(String[] args) {
        Habitat habitat = new Habitat();
    }

    public Habitat() {
        random = new Random();
        rabbits = Singleton.getInstance();
        frame = new JFrame();
        frame.setTitle("labs");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < rabbits.getVectorList().size(); i++) {
                    if(((double) time/1000 - rabbits.getVectorList().get(i).timeBorn) >= rabbits.getVectorList().get(i).timeLife){
                        rabbits.removeObj(rabbits.getVectorList().get(i));
                    }else{
                        rabbits.getVectorList().get(i).draw(g);
                    }
                }
            }
        };
        label = new JLabel();
        label.setFont(new Font("Arial", 1, 24));
        label.setForeground(Color.BLACK);
        textBasicRabbits = new JLabel();
        textBasicRabbits.setFont(new Font("Arial", 1, 24));
        textBasicRabbits.setForeground(Color.BLUE);
        textAlbinosRabbits = new JLabel();
        textAlbinosRabbits.setFont(new Font("Arial", 1, 24));
        textAlbinosRabbits.setForeground(Color.MAGENTA);
        panel.add(label);
        panel.add(textBasicRabbits);
        panel.add(textAlbinosRabbits);
        frame.add(panel);
        controlMenu();
        controlPanel();
        keyBoardClick();
        frame.setVisible(true);
        frame.setFocusable(true);
    }
    public void controlMenu() {
        JMenuBar jMenuBar = new JMenuBar();
        //первая менюшка
        JMenu simulation = new JMenu("Simulation");
        JMenuItem start = new JMenuItem("Start");
        JMenuItem stop = new JMenuItem("Stop");
        JMenuItem show_info = new JMenuItem("Show info");
        JMenuItem exit = new JMenuItem("Exit");
        //вторая менюшка
        JMenu timer_menu = new JMenu("Timer");
        JMenuItem show_time = new JMenuItem("Show time");
        JMenuItem hide_time = new JMenuItem("Hide time");
        //добавления в менюшки данных
        simulation.add(start);
        simulation.add(stop);
        simulation.add(show_info);
        simulation.add(exit);

        timer_menu.add(show_time);
        timer_menu.add(hide_time);
        jMenuBar.add(simulation);
        jMenuBar.add(timer_menu);

        //действия по нажатию
        start.addActionListener(e -> {
            startButton();
        });
        stop.addActionListener(e -> {
            stopButton();
        });
        show_info.addActionListener(e -> {
            flagForInfo = true;
            checkBox.setSelected(true);
        });
        exit.addActionListener(e -> {
            System.exit(0);
        });
        show_time.addActionListener(e -> {
            show_time();
        });
        hide_time.addActionListener(e -> {
            hide_time();
        });

        //добавление в фрейм + антифокус
        jMenuBar.setFocusable(false);
        simulation.setFocusable(false);
        timer_menu.setFocusable(false);
        frame.setJMenuBar(jMenuBar);
    }
    public void controlPanel() {
        JPanel controlpanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        controlpanel.setPreferredSize(new Dimension(270, frame.getHeight()));
        controlpanel.setLayout(new FlowLayout());
        //Создание объектов для контрольной панели
        startBut = new JButton("Start");
        stopBut = new JButton("Stop");
        checkBox = new JCheckBox("show info");
        showTimeRadio = new JRadioButton("show time");
        hideTimeRadio = new JRadioButton("hide time");
        ButtonGroup buttonGroup = new ButtonGroup();
        JLabel textForChanceFirst = new JLabel("Шанс рождения первого:");
        JComboBox<Integer> boxForFirst = new JComboBox<>();
        JLabel textForChanceSecond = new JLabel("Шанс рождения второго:");
        JComboBox<Integer> boxForSecond = new JComboBox<>();
        JLabel textForInputFirst = new JLabel("Периодичность появления первого:");
        inputForFirst = new JFormattedTextField("1");
        JLabel textForInputSecond = new JLabel("Периодичность появления второго:");
        inputForSecond = new JFormattedTextField("1");
        JLabel timeLifeBasicText = new JLabel("Время жизни кролика:");
        inputTimeLifeBasic = new JFormattedTextField("7");
        JLabel timeLifeAlbinosText = new JLabel("Время жизни альбиноса:");
        inputTimeLifeAlbinos = new JFormattedTextField("10");
        Button buttonShowLifes = new Button("Показать объекты");
        Button startStopBasic = new Button("Stop/Enable Basic");
        Button startStopAlbinos = new Button("Stop/Enable Albinos");
        JComboBox<Integer> boxForBasic = new JComboBox<>();
        JComboBox<Integer> boxForAlbinos = new JComboBox<>();

        //Вид
        startBut.setForeground(Color.BLACK);
        startBut.setBackground(Color.GREEN);
        stopBut.setForeground(Color.WHITE);
        stopBut.setBackground(Color.RED);
        buttonShowLifes.setBackground(Color.BLACK);
        buttonShowLifes.setForeground(Color.WHITE);
        //размеры
        startBut.setPreferredSize(new Dimension(270, 50));
        stopBut.setPreferredSize(new Dimension(270, 50));
        boxForFirst.setPreferredSize(new Dimension(70, 30));
        boxForSecond.setPreferredSize(new Dimension(70, 30));
        inputForFirst.setPreferredSize(new Dimension(270, 40));
        inputForSecond.setPreferredSize(new Dimension(270, 40));
        inputTimeLifeBasic.setPreferredSize(new Dimension(270,40));
        inputTimeLifeAlbinos.setPreferredSize(new Dimension(270,40));
        buttonShowLifes.setPreferredSize(new Dimension(270,50));
        startStopBasic.setPreferredSize(new Dimension(130,50));
        startStopAlbinos.setPreferredSize(new Dimension(130,50));
        boxForBasic.setPreferredSize(new Dimension(70, 30));
        boxForAlbinos.setPreferredSize(new Dimension(70, 30));
        //Шрифты
        textForChanceFirst.setFont(new Font("Arial", Font.PLAIN, 14));
        textForChanceSecond.setFont(new Font("Arial", Font.PLAIN, 14));
        textForInputFirst.setFont(new Font("Arial", Font.PLAIN, 16));
        textForInputSecond.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLifeBasicText.setFont(new Font("Arial",Font.PLAIN, 16));
        timeLifeAlbinosText.setFont(new Font("Arial", Font.PLAIN, 16));

        //добавлние данных
        buttonGroup.add(showTimeRadio);
        buttonGroup.add(hideTimeRadio);
        showTimeRadio.setSelected(true);
        for (int i = 10; i <= 100; i += 10) {
            boxForFirst.addItem(i);
            boxForSecond.addItem(i);
        }
        for (int i = 1; i <= 10; i++) {
            boxForBasic.addItem(i);
            boxForAlbinos.addItem(i);
        }
        boxForFirst.setSelectedItem(70);
        boxForSecond.setSelectedItem(80);

        //Функции кнопок
        startBut.addActionListener(e -> {
            startButton();
        });
        stopBut.addActionListener(e -> {
            stopButton();
        });
        checkBox.addActionListener(e -> {
            if (flagForInfo) {
                flagForInfo = false;
            } else {
                flagForInfo = true;
            }
        });
        showTimeRadio.addActionListener(e -> {
            timeLabel = true;
            label.setVisible(true);
        });
        hideTimeRadio.addActionListener(e -> {
            timeLabel = false;
            label.setVisible(false);
        });
        boxForFirst.addActionListener(e -> {
            int selected = (int) boxForFirst.getSelectedItem();
            basicChanche = selected / 100;
        });
        boxForSecond.addActionListener(e -> {
            int selected = (int) boxForSecond.getSelectedItem();
            albinosChanche = selected / 100;
        });
        inputForFirst.addActionListener(e -> {
            try {
                int newValue = Integer.parseInt(inputForFirst.getText());
                if (newValue >= 0 && newValue <= 100)  {
                    timerBasic.setDelay(newValue * 1000);
                    panel.requestFocusInWindow();
                    panel.setFocusable(true);
                    frame.setFocusable(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Введите допустимое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Введите допустимое число", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        inputForSecond.addActionListener(e -> {
            try {
                int newValue = Integer.parseInt(inputForSecond.getText());
                if (newValue >= 0 && newValue <= 100) {
                    timerAlbinos.setDelay(newValue * 1000);
                    panel.requestFocusInWindow();
                    panel.setFocusable(true);
                    frame.setFocusable(true);
                    System.out.println(newValue);
                } else {
                    JOptionPane.showMessageDialog(frame, "Введите допустимое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Введите допустимое число", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        inputTimeLifeBasic.addActionListener(e -> {
            try{
                int newValue = Integer.parseInt(inputTimeLifeBasic.getText());
                if(newValue >=0 && newValue <=100){
                    timeLifeBasic = newValue;
                    panel.requestFocusInWindow();
                    panel.setFocusable(true);
                    frame.setFocusable(true);
                }else {
                    JOptionPane.showMessageDialog(frame, "Введите допустимое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Введите допустимое число", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        inputTimeLifeAlbinos.addActionListener(e -> {
            try{
                int newValue = Integer.parseInt(inputTimeLifeAlbinos.getText());
                if(newValue >=0 && newValue <=100){
                    timeLifeAlbinos = newValue;
                    panel.requestFocusInWindow();
                    panel.setFocusable(true);
                    frame.setFocusable(true);
                }else {
                    JOptionPane.showMessageDialog(frame, "Введите допустимое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Введите допустимое число", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonShowLifes.addActionListener(e -> {
            StringBuilder sb  = new StringBuilder();
            for (Rabbit rabbit : rabbits.getVectorList()){
                sb.append(rabbit.timeBorn + " sec " + " " + rabbit + "\n");
            }
            String message = sb.toString();
            JOptionPane.showOptionDialog(frame, message, "Живые кролики:",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, new String[]{"Ок"}, "Ок");
        });
        startStopBasic.addActionListener(e -> {
            if(basicAIIsStarted){
                stopBasicBase();
                basicAIIsStarted = false;
            }else{
                enableBasicBase();
                basicAIIsStarted = true;
            }
        });
        startStopAlbinos.addActionListener(e -> {
            if(albinosAIIsStarted){
                stopAlbinosBase();
                albinosAIIsStarted = false;
            }else{
                enableAlbinosBase();
                albinosAIIsStarted = true;
            }
        });
        boxForBasic.addActionListener(e -> {
            int selected = (int) boxForBasic.getSelectedItem();
            setPriorityBasic(selected);
        });
        boxForAlbinos.addActionListener(e -> {
            int selected = (int) boxForAlbinos.getSelectedItem();
            setPriorityAlbinos(selected);
        });


        //анти фокус
        startBut.setFocusable(false);
        stopBut.setFocusable(false);
        checkBox.setFocusable(false);
        showTimeRadio.setFocusable(false);
        hideTimeRadio.setFocusable(false);
        boxForFirst.setFocusable(false);
        boxForSecond.setFocusable(false);
        buttonShowLifes.setFocusable(false);
        startStopBasic.setFocusable(false);
        startStopAlbinos.setFocusable(false);
        boxForBasic.setFocusable(false);
        boxForAlbinos.setFocusable(false);


        controlpanel.add(startBut);
        controlpanel.add(stopBut);
        controlpanel.add(checkBox);
        controlpanel.add(showTimeRadio);
        controlpanel.add(hideTimeRadio);
        controlpanel.add(textForChanceFirst);
        controlpanel.add(boxForFirst);
        controlpanel.add(textForChanceSecond);
        controlpanel.add(boxForSecond);
        controlpanel.add(textForInputFirst);
        controlpanel.add(inputForFirst);
        controlpanel.add(textForInputSecond);
        controlpanel.add(inputForSecond);
        controlpanel.add(timeLifeBasicText);
        controlpanel.add(inputTimeLifeBasic);
        controlpanel.add(timeLifeAlbinosText);
        controlpanel.add(inputTimeLifeAlbinos);
        controlpanel.add(buttonShowLifes);
        controlpanel.add(startStopBasic);
        controlpanel.add(startStopAlbinos);
        controlpanel.add(boxForBasic);
        controlpanel.add(boxForAlbinos);
        frame.add(controlpanel, BorderLayout.WEST);
    }
    public void startTimers() {
        timer = new Timer(interval, e -> {
            panel.repaint();
            time += interval;
            label.setText(String.format("Время %d в секундах", time / 1000));
        });
        timerBasic = new Timer(intervalBasic, e -> {
            updateBasic();
        });
        timerAlbinos = new Timer(intervalAlbinos, e -> {
            updateAlbinos();
        });
        timerRepaint = new Timer(16, e -> {
            panel.repaint();
        });
        timer.start();
        timerBasic.start();
        timerAlbinos.start();
        timerRepaint.start();
    }
    public void startTreads(){
        basicAI = new BasicAI();
        basicAI.start();
        albinosAI = new AlbinosAI();
        albinosAI.start();

    }

    public void startButton() {
        if (!isStarting) {
            startBut.setBackground(Color.BLACK);
            startBut.setForeground(Color.WHITE);
            stopBut.setBackground(Color.WHITE);
            stopBut.setForeground(Color.BLACK);
            isStarting = true;
            startSimulation();
        }
    }

    public void stopButton() {
        if (isStarting) {
            stopBut.setBackground(Color.BLACK);
            stopBut.setForeground(Color.WHITE);
            startBut.setBackground(Color.WHITE);
            startBut.setForeground(Color.BLACK);
            isStarting = false;
            stopSimulation();
        }
    }

    public void show_time() {
        timeLabel = true;
        label.setVisible(true);
    }

    public void hide_time() {
        timeLabel = false;
        label.setVisible(false);
    }

    public void startSimulation() {
        rabbits.clear();
        startTimers();
        startTreads();
        booleanBasicRab = false;
        booleanAlbinosRab = false;
        textBasicRabbits.setVisible(booleanBasicRab);
        textAlbinosRabbits.setVisible(booleanAlbinosRab);
    }

    public void stopSimulation() {
        int countRabbitsBasic = 0;
        int countRabbitAlbinos = 0;
        for (Rabbit rabbit : rabbits) {
            if (rabbit instanceof BasicRabbit) {
                countRabbitsBasic++;
            }
            if (rabbit instanceof AlbinosRabbit) {
                countRabbitAlbinos++;
            }
        }
        if (flagForInfo) {
            String message = "Количество обычных = " + countRabbitsBasic + "\n" +
                    "Количество албиносов = " + countRabbitAlbinos + "\n" +
                    "Время выполнения: " + time / 1000 + " секунд";
            int result = JOptionPane.showOptionDialog(frame, message, "Хотите завершить работу?",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, null, null);
            if(result == JOptionPane.OK_CANCEL_OPTION){
                startBut.setBackground(Color.BLACK);
                startBut.setForeground(Color.WHITE);
                stopBut.setBackground(Color.WHITE);
                stopBut.setForeground(Color.BLACK);
                isStarting = true;
            }else{
                frame.repaint();
                timer.stop();
                timerBasic.stop();
                timerAlbinos.stop();
                textBasicRabbits.setText("Количество стандартных = " + countRabbitsBasic);
                textAlbinosRabbits.setText("Количество альбиносов = " + countRabbitAlbinos);
                time = 0;
                booleanBasicRab = true;
                booleanAlbinosRab = true;
                textBasicRabbits.setVisible(booleanBasicRab);
                textAlbinosRabbits.setVisible(booleanAlbinosRab);
            }
        }else{
            frame.repaint();
            timer.stop();
            timerBasic.stop();
            timerAlbinos.stop();
            textBasicRabbits.setText("Количество стандартных = " + countRabbitsBasic);
            textAlbinosRabbits.setText("Количество альбиносов = " + countRabbitAlbinos);
            time = 0;
            booleanBasicRab = true;
            booleanAlbinosRab = true;
            textBasicRabbits.setVisible(booleanBasicRab);
            textAlbinosRabbits.setVisible(booleanAlbinosRab);
        }

    }

    private void updateBasic() {
        if (Math.random() < basicChanche) {
            rabbits.addObj(new BasicRabbit(random.nextInt(width), random.nextInt(height), time/1000, timeLifeBasic));
        }
    }

    private void updateAlbinos() {
        if (Math.random() < albinosChanche) {
            rabbits.addObj(new AlbinosRabbit(random.nextInt(width), random.nextInt(height), time/1000, timeLifeAlbinos));
        }
    }

    private void keyBoardClick() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {

            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (!isStarting) {
                    if (e.getKeyCode() == KeyEvent.VK_B) {
                        startButton();
                    }
                } else {
                    if (e.getKeyCode() == KeyEvent.VK_E) {
                        stopButton();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    timeLabel = !timeLabel;
                    label.setVisible(timeLabel);
                    if (showTimeRadio.isSelected()) {
                        hideTimeRadio.setSelected(true);
                    } else if (hideTimeRadio.isSelected()) {
                        showTimeRadio.setSelected(true);
                    }
                }
            }
            return false;
        });
    }

    public static int getWidth() {
        return width;
    }

    public void stopBasicBase(){
        basicAI.setSleep(true);
    }
    public void enableBasicBase(){
        basicAI.setSleep(false);
        synchronized (BasicAI.class){
            BasicAI.class.notify();
        }
    }
    public void stopAlbinosBase(){
        albinosAI.setSleep(true);
    }
    public void enableAlbinosBase(){
        albinosAI.setSleep(false);
        synchronized (AlbinosAI.class){
            AlbinosAI.class.notify();
        }
    }
    public void setPriorityBasic(int priority){
        basicAI.getThread().setPriority(priority);
    }
    public void setPriorityAlbinos(int priority){
        albinosAI.getThread().setPriority(priority);
    }
}
