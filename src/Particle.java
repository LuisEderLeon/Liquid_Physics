public class Particle {
    double xPos;
    double yPos;
    double xSpeed = 0;
    double ySpeed = 0;
    public Particle(int width, int height){
        this.xPos = (Math.random() * width);
        this.yPos = (Math.random() * height);
    }
    protected void startGravity(int height, int width){
        height += 30;
        width += 30;
        if (yPos < height - 1) {
            // Gravity, only if the particle is above the screen edge
            ySpeed += 0.1;
        }
        yPos += ySpeed;
        xPos += xSpeed;

        // Decrease speed
        xSpeed += (xSpeed < 0) ? 0.01 : (xSpeed == 0) ? 0 : -0.01;
        ySpeed += (ySpeed < 0) ? 0.01 : (ySpeed == 0) ? 0 : -0.01;

        // Snap back if particle goes beyond screen edges
        if (xPos < 0 || xPos >= width){
            xPos = (xPos < 0) ? 1 : width;
            xSpeed *= -0.75;
        }
        if (yPos < 0 || yPos >= height){
            yPos = (yPos < 0) ? 1 : height;
            ySpeed *= -0.75;
        }
        // Snap speed if it's too fast or too slow
        if (Math.abs(xSpeed) > 5 || Math.abs(xSpeed) < 0.01) xSpeed = 0;
        if (ySpeed < -10) ySpeed = -10;
        if (Math.abs(ySpeed) < 0.01) ySpeed = 0;
    }
    protected void collision(Particle particle) {
        double yDistance = particle.yPos - yPos;
        double xDistance = particle.xPos - xPos;
        double totalDistanceSq = (xDistance * xDistance) + (yDistance * yDistance);

        if (totalDistanceSq < 40 * 40) {
            double totalDistance = Math.sqrt(totalDistanceSq);
            double repellingForce = 2 / (0.2 * totalDistance + 1);
            double angle = Math.atan2(yDistance, xDistance);

            xSpeed -= repellingForce * Math.cos(angle);
            ySpeed -= repellingForce * Math.sin(angle);

            if (totalDistance < 10) {
                ySpeed = 5 * Math.random() - 2.5;
            }
        }
    }
}
