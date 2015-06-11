package model.drawObjects;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Bilel on 4-6-2015.
 */
public class CoinAnimation extends DrawObject {

    private Ellipse2D       coinShape;
    private Point2D.Double  coinSetPoint;

    // Hoevaak de timer gelopen heeft
    private static int timerLoops = 0;

    public CoinAnimation(int startX, int startY) {
        coinSetPoint    = new Point2D.Double(startX, startY);
        coinShape       = new Ellipse2D.Double(startX, startY, 20, 20);
    }

    public void start() {
        timerLoops      = 1;
    }

    public void draw(Graphics2D g2) {

        g2.setColor(new Color(255, 255, 0, (255 - timerLoops * 5)));    // GEEL
        g2.draw(coinShape);                                             // MUNTJE TEKENEN
        g2.fill(coinShape);
        update(timerLoops);                                             // UPDATE

    }

    // Wordt na elke paint aangeroepen
    public void update(float factor) {

        if (areWeDoneYet()) {
            System.out.println("Call initCoin(int startX, startY) first!");
            return;
        }

        // Frame steeds aanpassen aan locatie van coin (Zodat hele coin wel te zien blijft)
        coinShape.setFrame(coinSetPoint.getX(), coinSetPoint.getY() + 10 * timerLoops,
                coinShape.getWidth(), coinShape.getHeight());

        timerLoops++;

    }

    public boolean areWeDoneYet() {
        System.out.println(timerLoops);
        if (timerLoops > 50 || timerLoops == 0) {
            timerLoops = 0;
            return true;
        }

        return false;
    }

}