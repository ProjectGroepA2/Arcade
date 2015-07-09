package model.drawObjects;

import model.gameState.PlayState;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class PointAnimation extends DrawObject {

    ArrayList<Point2D.Double>   puntenPos   =   new ArrayList<>();
    ArrayList<Integer>          puntenGain  =   new ArrayList<>();
    ArrayList<Integer>          iterations  =   new ArrayList<>();
    ArrayList<Color>            colors      =   new ArrayList<>();

    public static double LastPointIncrement = 0;

    public PointAnimation() {    }

    public void enemyDied(Point2D.Double p, Color c) {
        puntenPos.add(new Point2D.Double(p.getX(), p.getY()));
        puntenGain.add((int)LastPointIncrement);
        iterations.add(0);
        colors.add(c);
    }

    @Override
    public void draw(Graphics2D g2) {
            for (int x = 0; x < puntenPos.size(); x++) {
                Color oldColor = g2.getColor();
                g2.setColor(colors.get(x));
                g2.drawString("" + puntenGain.get(x), (int) puntenPos.get(x).getX(), (int) puntenPos.get(x).getY());
                g2.setColor(oldColor);
                update(0);
            }
    }

    @Override
    public void update(float factor) {

        for (int x = 0; x < puntenPos.size(); x++) {
            puntenPos.get(x).setLocation(puntenPos.get(x).getX(), puntenPos.get(x).getY() - 3);
            iterations.set(x, iterations.get(x) + 1);

            if (iterations.get(x) > 100) {
                puntenPos.remove(x);
                iterations.remove(x);
                puntenGain.remove(x);
                colors.remove(x);
            }
        }
    }
}
