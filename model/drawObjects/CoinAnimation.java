package model.drawObjects;

import model.gameState.PlayState;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * Created by Bilel on 4-6-2015.
 *
 * CoinAnimation klasse: Weergeeft een balletje (resembleerd een muntje) dat omlaag valt.
 *      Je hoeft alleen achter elkaar de paint(Graphics2D) methode aan te roepen, deze roep automatisch een 'reposition' aan,
 *      waardoor je het balletje alleen snel achter elkaar hoeft te repainten.
 */
public class CoinAnimation extends DrawObject {

    private Ellipse2D       coinShape;
    private Point2D.Double  coinSetPoint;

    // Hoevaak de timer gelopen heeft
    private static int timerLoops      = 0;

    public CoinAnimation(int startX, int startY) {
        coinSetPoint        = new Point2D.Double(startX, startY);
        coinShape           = new Ellipse2D.Double(startX, startY, 20, 20);
    }

    // Start
    public void start() {
        timerLoops          = 1;
    }

    public void draw(Graphics2D g2) {

        update(timerLoops);                                             // UPDATE
        g2.setColor(new Color(255, 255, 0));    // GEEL
        g2.draw(coinShape);                                             // MUNTJE TEKENEN
        g2.fill(coinShape);
    }

    // Wordt na elke paint aangeroepen
    public void update(float factor) {

        // Alleen tekenen wanner de animatie ingesteld is om te tekenen (zie: areWeDoneYet()), anders doei.
        if (areWeDoneYet()) {
            System.out.println("Call initCoin(int startX, startY) first!");
            return;
        }

        // Coin omlaag verschuiven doordat Y * loops omlaag gaat.
        // Per frame verschuift het balletje delta 10 op Y-as.
        try {
            coinShape.setFrame(coinSetPoint.getX(), coinSetPoint.getY() + (1000 - 7 * PlayState.comboScore) / 20 * timerLoops,
                    coinShape.getWidth(), coinShape.getHeight());
        } catch (ArithmeticException a) { a.printStackTrace(); }

        timerLoops++;
    }

    // Zijn we al begonnen/klaar?
    public boolean areWeDoneYet() {
        if (timerLoops > 19 || timerLoops == 0) {
            timerLoops = 0;
            return true;
        }
        return false;
    }

    // Zitten we in de laatste loop?
    public boolean isLastLoop() { return (timerLoops == 49) ? true : false;  }

}