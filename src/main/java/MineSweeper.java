import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MineSweeper extends JFrame {

    private final int PICTURE_SIZE = 50;
    private Game game;
    private Solver solver = new Solver();
    private static JPanel panel;

    public static void main(String[] args) {
        initSettings();
    }

    MineSweeper(int col, int row, int bomb){
        game = new Game(col, row, bomb);
        game.start();
        initMenu();
        initPanel();
        initFrame();
        initLabel();
        setImage();
        setVisible(true);
    }

    private void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createSettingsMenu());
        setJMenuBar(menuBar);
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("Игра");
        JMenuItem newGame = new JMenuItem("Новая игра");
        JMenuItem exit = new JMenuItem("Выход");
        file.add(newGame);
        file.addSeparator();
        file.add(exit);
        newGame.addActionListener(e -> restart());
        exit.addActionListener(e -> System.exit(0));
        return file;
    }

    private JMenu createSettingsMenu(){
        JMenu file = new JMenu("Настройки");
        JMenuItem sett = new JMenuItem("Настроить");
        sett.addActionListener(e -> {dispose(); initSettings();});
        file.add(sett);
        return file;
    }

    private void initSolver(){
        solver.update(game);
        solver.solverStart();
    }

    private void initLabel(){
        JLabel label = new JLabel("Нажмите среднюю кнопку мыши для выполнения следующего хода ботом");
        add (label, BorderLayout.SOUTH);
    }

    private void initFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MineSweeper");
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(getImage("icon"));
    }

    private static void initSettings(){
        Settings settings = new Settings();
        settings.setVisible(true);
        settings.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        settings.setResizable(false);
    }

    private void initPanel(){
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Field.getAllCoords()){
                    g.drawImage((Image) game.getCell(coord).image, coord.x * PICTURE_SIZE, coord.y * PICTURE_SIZE, PICTURE_SIZE, PICTURE_SIZE, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / PICTURE_SIZE;
                int y = e.getY() / PICTURE_SIZE;
                Coord coord = new Coord(x, y);
                if(e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if(e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if(e.getButton() == MouseEvent.BUTTON2){
                    initSolver();
                }
                panel.repaint();
                getMessage();
            }
        });
        panel.setPreferredSize(new Dimension(Field.getSize().x * PICTURE_SIZE, Field.getSize().y * PICTURE_SIZE));
        add(panel);
    }

    private void getMessage() {
        if (game.getGameState() == GameState.BOMBED)
            JOptionPane.showMessageDialog(null, "You lose!","Ups", JOptionPane.INFORMATION_MESSAGE);
        if (game.getGameState() == GameState.WINNER)
            JOptionPane.showMessageDialog(null, "You win!","Congratulation!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setImage(){
        for(Cell cell : Cell.values()){
            cell.image = getImage(cell.name());
        }
    }

    private Image getImage (String name){
        String fileName = "resource/pictures/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(fileName);
        return icon.getImage();
    }

    private void restart(){
        dispose();
        new MineSweeper(Settings.getCols(), Settings.getCols(), Settings.getBombs());
    }
}
