import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Particle> particleList = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            particleList.add(new Particle(1000,600));
        }

        Renderer renderedParticle = new Renderer();
        for (Particle particle: particleList) {
            renderedParticle.addParticle(particle);
        }
        SwingUtilities.invokeLater(() -> renderedParticle.setVisible(true));
    }
}