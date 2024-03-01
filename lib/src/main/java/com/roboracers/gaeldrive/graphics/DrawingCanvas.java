package com.roboracers.gaeldrive.graphics;

import com.roboracers.gaeldrive.particles.Particle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class DrawingCanvas extends JComponent {

    private int width, height;

    private static double scalar = 4;

    public ArrayList<Particle> particles;

    public DrawingCanvas(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d =  (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2d.setRenderingHints(rh);

        drawParticles(g2d, particles);
    }

    public static double particleRadius = 5;

    public void drawParticle(Graphics2D g2d, Particle particle) {
        g2d.setColor(new Color(100,149,237));
        double x =  particle.getState().getEntry(0);
        double y = particle.getState().getEntry(1);
        double heading = particle.getState().getEntry(2);
        double radius = particleRadius * particle.getWeight() * 1000000000;
        Ellipse2D.Double body = new Ellipse2D.Double(scalar * x + (double) width /2, scalar * y + (double) height /2, particleRadius, particleRadius);
        g2d.fill(body);
    }

    public void drawParticles(Graphics2D g2d, ArrayList<Particle> particles) {
        for (Particle particle: particles) {
            drawParticle(g2d, particle);
        }
    }
}
