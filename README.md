
# Liquid Physics

![Liquid Physics Simulation Screenshot](https://github.com/user-attachments/assets/d37f95fd-255f-4cf4-bc2b-17334f91652b)

A small particle simulator that mimics liquid behavior through a dynamic repulsion force, which adjusts based on the distance between particles. The simulator demonstrates how particles in a liquid interact on an atomic scale, balancing repulsive and attractive forces to create fluid-like motion.

## Particles

Each particle is an object of the `Particle` class, which has methods to calculate forces, track distance, and adjust speed to simulate liquid behavior. This class structure aims to offer insights into how liquids work at a microscopic level.

### Repulsion Force

Each particle calculates the distance to nearby particles and applies a force on itself based on that distance. The repelling force increases as particles get closer, causing them to move apart and behave fluidly. The force is calculated using the following formula:

$$\text{repellingForce} = 2 - \sqrt{\frac{\text{totalDistance}}{7.5}}$$

This formula, derived through experimentation, provides a balance where particles neither move too fast nor too slow. When the distance between two particles exceeds 30 pixels, the repelling force becomes negative, creating an attractive effect that mimics **surface tension**. This behavior pulls particles together at larger distances, simulating the cohesive force present in liquids.

### Distance Calculation

To optimize performance, each particle only applies the repelling force to other particles within a 40-pixel radius. This radius reduces computational load, ensuring only nearby particles impact each other's movement. The `getDistance` method in the `Particle` class calculates the squared distance between two particles:

$$\text{totalDistanceSquared} = (x_{\text{other}} - x)^2 + (y_{\text{other}} - y)^2$$

This avoids the more computationally expensive square root calculation in cases where the full distance is unnecessary. Here’s how the `getDistance` method looks in code:

```java
public double getDistance(Particle particle) {
    double yDistance = particle.yPos - this.yPos;
    double xDistance = particle.xPos - this.xPos;
    return (xDistance * xDistance) + (yDistance * yDistance);
}
```

### Speed and Direction

Particles have `xSpeed` and `ySpeed` attributes that control their movement. When a repelling force is applied, it is decomposed into x and y components based on the angle between the two particles, ensuring that each particle is pushed directly away from the other.

The angle is calculated using `atan2` to handle all quadrants of the coordinate system:

$$\text{angle} = \text{atan2}(y_{\text{distance}}, x_{\text{distance}})$$

Then, the force is split into x and y components:

$$\text{repellingForce}_x = \text{repellingForce} \times \cos(\text{angle})$$
$$\text{repellingForce}_y = \text{repellingForce} \times \sin(\text{angle})$$

These components are added to the particle's `xSpeed` and `ySpeed`, respectively:

```java
this.xSpeed -= repellingForce * Math.cos(angle);
this.ySpeed -= repellingForce * Math.sin(angle);
```

### Color-Based Speed Indicator

Each particle’s color changes based on its total velocity, which is the sum of the absolute values of `xSpeed` and `ySpeed`. This color change gives a visual indicator of speed: faster particles appear in different colors, allowing viewers to quickly gauge the motion dynamics.
