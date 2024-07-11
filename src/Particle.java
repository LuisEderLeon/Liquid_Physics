public class Particle {
    double xPos;
    double yPos;
    double xSpeed = 0;
    double ySpeed = 0;
    int height;
    int width;
    public Particle(int width, int height){
        this.xPos = (Math.random() * width);
        this.yPos = (Math.random() * height);
    }
    protected void startGravity(int height, int width){
        this.height = height + 20;
        this.width = width + 20;
        if (yPos < this.height || ySpeed < -0.5f) {
            // Gravity, only if the particle is above the screen edge
            ySpeed += 0.05;

            yPos += ySpeed;
        }
        // Bounce from the ground
        else{
            ySpeed *= -0.5;
        }
        // Bounce off the ceiling
        if (yPos < -5){
            yPos = -4;
            ySpeed = 0;
        }
        // Snap back if particle goes beyond screen floor
        if (yPos > this.height){
            yPos = this.height;
        }

        xPos += xSpeed;

        // Decrease X speed
        xSpeed += (xSpeed < 0) ? 0.001 : (xSpeed == 0) ? 0 : -0.001;
        // Snap back if particle goes beyond screen edges
        if (xPos < 0 || xPos > this.width + 2){
            xPos = (xPos < 0) ? 1 : this.width-1;
            xSpeed *= -0.1;
        }
        // Snap X speed if it's too fast or too slow
        if (Math.abs(xSpeed) > 10 || Math.abs(xSpeed) < 0.001){
            xSpeed = 0;
        }

        // Snap Y speed if it's too fast or too slow
        if (Math.abs(ySpeed) > 20){
            ySpeed = 0;
        }
    }
    protected void collision(Particle particle) {
        double yDistance = particle.yPos - yPos + 1;
        double xDistance = particle.xPos - xPos;
        double totalDistance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
        double repellingForce = (totalDistance < 50) ? (10 / ((10 * totalDistance) + 1)) : 0;
        double angle = Math.atan2(yDistance, xDistance);

        xSpeed -= repellingForce * Math.cos(angle);
        ySpeed -= repellingForce * Math.sin(angle);
        
    }
}

