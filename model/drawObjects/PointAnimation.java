package model.drawObjects;

import model.gameState.PlayState;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class PointAnimation extends DrawObject {

    LinkedList<Point2D.Double>   puntenPos   =   new LinkedList<>();
    LinkedList<Integer>          puntenGain  =   new LinkedList<>();
    LinkedList<Integer>          iterations  =   new LinkedList<>();

    public static double LastPointIncrement = 0;

    public PointAnimation() {    }

    public void enemyDied(Point2D.Double p) {
        puntenPos.add(new Point2D.Double(p.getX(), p.getY()));
        puntenGain.add((int)LastPointIncrement);
        iterations.add(0);
    }

    @Override
    public void draw(Graphics2D g2) {
            for (int x = 0; x < puntenPos.size(); x++) {
                g2.drawString("" + puntenGain.get(x), (int) puntenPos.get(x).getX(), (int) puntenPos.get(x).getY());

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
            }
        }
    }
}
