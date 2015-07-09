package model.drawObjects;

import model.gameState.PlayState;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * CoinAnimation klasse: Weergeeft een balletje (resembleerd een muntje) dat omlaag valt.
 *      Je hoeft alleen achter elkaar de paint(Graphics2D) methode aan te roepen, deze roep automatisch een 'reposition' aan,
 *      waardoor je het balletje alleen snel achter elkaar hoeft te repainten.
 *
 * *    Init (startX, startY):  Punt waar de animatie begint met vallen
 *
 *      start(): aanroepen om te starten
 * *
 * *
 **/
public class CoinAnimation extends DrawObject {

    private Ellipse2D       coinShape;
    private Point2D.Double  coinSetPoint;

    // Hoevaak de timer gelopen heeft
    private static int  timerLoops      = 0;
    private double      reach           = 0;
    private Color       color;

    public CoinAnimation(int startX, int startY) {
        coinSetPoint        = new Point2D.Double(startX + 180, startY - 10);
        coinShape           = new Ellipse2D.Double(startX + 180, startY - 10, 20, 20);
    }

    // Start
    public void start(Color c) {
        timerLoops          = 1;
        reach               = (180 - PlayState.comboScore * 2) / 14;  // 'Eindbestemming' van het muntje
        color               = c;
    }

    public void draw(Graphics2D g2) {
        Color oldColor = g2.getColor();
        update(timerLoops);                                             // UPDATE
        g2.setColor(color);    // GEEL
        g2.draw(coinShape);                                             // MUNTJE TEKENEN
        g2.fill(coinShape);
        g2.setColor(oldColor);
    }

    // Wordt na elke paint aangeroepen
    public void update(float factor) {
        // Coin omlaag verschuiven doordat Y * loops omlaag gaat.
        // Per frame verschuift het balletje delta 10 op Y-as.
        try {
            coinShape.setFrame(coinSetPoint.getX() - reach * timerLoops, coinSetPoint.getY(),
                                coinShape.getWidth(), coinShape.getHeight());
        } catch (ArithmeticException a) { a.printStackTrace(); }

        timerLoops++;
    }

    // Zijn we al begonnen/klaar?
    public boolean areWeDoneYet() {
        if ((timerLoops > 14 || timerLoops == 0) || (reach * timerLoops > 180)) {
            timerLoops = 0;
            return true;
        }
        return false;
    }

    // Zitten we in de laatste loop?
    public boolean isLastLoop() { return (timerLoops == 13) ? true : false;  }

}