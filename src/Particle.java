public class Particle {
    double xPos;
    double yPos;
    double xSpeed = 0;
    double ySpeed = 0;
    double viscosity = 0.99;
    double gravity = 0.1;

    public Particle(int width, int height){
        this.xPos = (Math.random() * width);
        this.yPos = (Math.random() * height);
    }
    protected void startGravity(int height, int width){
        if (yPos < height - 1) {
            // Gravity, only if the particle is above the screen edge
            ySpeed += gravity;
        }
        yPos += ySpeed;
        xPos += xSpeed;

        // Decrease speed
        xSpeed *= viscosity;
        ySpeed *= viscosity;

        // Snap back if particle goes beyond screen edges
        if ((xPos < -(width / 2d)) || (xPos > (width / 2d))){
            xPos = (xPos < - width + (width / 2d)) ? - width + (width / 2d) + 5 : width - (width / 2d) - 5;
            xSpeed *= -1;
        }
        if (yPos < 0 || yPos >= height){
            yPos = (yPos < 0) ? 1 : height;
            ySpeed *= -1;
        }
        // Snap speed if it's too fast or too slow
        if (Math.abs(xSpeed) > 5 || Math.abs(xSpeed) < 0.01) xSpeed = 0;
        if (ySpeed < -10) ySpeed = -10;
        if (Math.abs(ySpeed) < 0.01) ySpeed = 0;
    }

    public double getDistance(Particle particle){
        double yDistance = particle.yPos - yPos;
        double xDistance = particle.xPos - xPos;
        return (xDistance * xDistance) + (yDistance * yDistance);
    }

    protected void collision(Particle particle) {
        double yDistance = particle.yPos - yPos;
        double xDistance = particle.xPos - xPos;
        double totalDistance = Math.sqrt(this.getDistance(particle));
        double repellingForce = 2 - Math.sqrt(totalDistance / 7.5);
        double angle = Math.atan2(yDistance, xDistance);

        xSpeed -= repellingForce * Math.cos(angle);
        ySpeed -= repellingForce * Math.sin(angle);
    }
}
