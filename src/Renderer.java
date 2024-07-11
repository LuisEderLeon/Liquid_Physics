import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Renderer extends JFrame {
    private static final ArrayList<Particle> particleList = new ArrayList<>();

    public Renderer(){
        setTitle("Liquid Physics");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CustomPanel customPanel = new CustomPanel();
        add(customPanel, BorderLayout.CENTER);

        Timer timer = new Timer(3, new TimerRenderer());
        timer.start();
    }
    public void addParticle(Particle particle){
        particleList.add(particle);
    }
    private class TimerRenderer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Particle particle: particleList) {
                for (Particle particle2: particleList) {
                    if (particle != particle2) {
                        particle.collision(particle2);
                    }
                }
                particle.startGravity(getHeight()-65, getWidth()-35);
                repaint();
            }
        }
    }
    private static class CustomPanel extends JPanel {
        private Color getCurrentColor(Particle particle){
            double totalSpeed = particle.ySpeed;
            if (particle.ySpeed < 0){
                totalSpeed *= -1;
            }
            if (particle.xSpeed < 0){
                totalSpeed -= particle.xSpeed;
            } else {
                totalSpeed += particle.xSpeed;
            }
            Color color = Color.getHSBColor(0.7f / ((float)(totalSpeed/4) + 1),1f,1f);
            color = new Color(color.getRed(),color.getGreen(),color.getBlue());
            return color;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Particle particle: particleList) {
                g.setColor(getCurrentColor(particle));
                g.fillOval((int)particle.xPos,(int)particle.yPos,10,10);
            }
        }
    }
}
