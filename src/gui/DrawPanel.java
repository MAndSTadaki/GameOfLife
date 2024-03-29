package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.GameOfLife;

/**
 *
 * @author tadaki
 */
public class DrawPanel extends javax.swing.JPanel implements Runnable {

    protected Dimension dimension;
    protected BufferedImage image;
    protected volatile boolean running = false;
    protected int sleep = 100;
    protected Point offset = new Point(50, 50);
    protected Point2D.Double f;
    private GameOfLife gol;

    /**
     * Creates new form DrawPanel2
     */
    public DrawPanel() {
        initComponents();
    }

    public void initializePanel() {
        dimension = this.getPreferredSize();
        image = new BufferedImage(dimension.width, dimension.height,
                BufferedImage.TYPE_INT_RGB);
        clearImage();

    }

    public void setGameOfLife(GameOfLife gol) {
        this.gol = gol;
        int n = gol.getN();
        double ff = (double) (dimension.height - 2 * offset.y) / n;
        f = new Point2D.Double(ff, ff);
    }

    public void mkImage() {
        int n = gol.getN();
        gol.updateState();
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(this.getForeground());
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                int s = gol.getState(y * n + x);
                if (s == 1) {
                    Rectangle2D.Double rect
                            = new Rectangle2D.Double(
                                    x * f.x + offset.x, y * f.y + offset.y,
                                    f.x, f.y);
                    g.fill(rect);
                }
            }
        }
    }

    public void clearImage() {
        if (image == null) {
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(this.getBackground());
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        if (image == null) {
            clearImage();
        }
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void run() {
        while (running) {
            clearImage();
            mkImage();
            repaint();
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public void setOffset(Point offset) {
        this.offset = offset;
    }

    /**
     * イメージの保存
     *
     * @param filename
     * @throws java.io.IOException
     */
    public void saveImage(String filename) throws IOException {
        File file = new File(filename);
        FileOutputStream out = new FileOutputStream(file);
        String ext = "png";
        ImageIO.write(image, ext, out);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
