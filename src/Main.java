import javax.swing.JFrame;

public class Main{

    public Main(){
        JFrame f = new JFrame();
        GamePanel gp = new GamePanel();


        f.add(gp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("SNAKE");
        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);

    }

    public static void main(String[] args) {
        new Main();
    }

}