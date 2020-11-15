package com.company;
import com.company.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JPanel {
    private static Color Bg_Color = new Color(0xbbada0);
    private static String Font_Name = "Arial";
    private static int Tile_Size = 64;
    private static int Tiles_Margin = 16;

    Game game = new Game();

    public Main(){

        setFocusable(true);
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    game.resetGame();
                }
                if(!game.canMove()){
                    game.setLose(true);
                }
                if(!game.getWin() && !game.getLose()){
                    switch(e.getKeyCode()){
                        case KeyEvent.VK_LEFT:
                            game.left();
                            break;
                        case KeyEvent.VK_RIGHT:
                            game.right();
                            break;
                        case KeyEvent.VK_UP:
                            game.up();
                            break;
                        case KeyEvent.VK_DOWN:
                            game.down();
                            break;

                    }
                }
                if(!game.getWin() && !game.canMove()){
                    game.setLose(true);
                }
                repaint();
            }
        });
        game.resetGame();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Bg_Color);
        g.fillRect(0,0,this.getSize().width,this.getSize().height);
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                Tile[] tiles = game.getBoard();
                drawTile(g,tiles[x+y*4],x,y);
            }
        }
    }
    private void drawTile(Graphics g, Tile tile, int x, int y){
        int value = tile.number;
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(tile.getBackground());
        g.fillRoundRect(xOffset,yOffset,Tile_Size,Tile_Size,0,0);
        g.setColor(tile.getNumberColor());

        final int size = value < 100 ? 36 : value <1000 ? 32:24;
        final Font font = new Font(Font_Name,Font.BOLD,size);
        g.setFont(font);
        String s = String.valueOf(value);
        final FontMetrics fm = getFontMetrics(font);
        final int w = fm.stringWidth(s);
        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];
        if(value != 0)
            g.drawString(s,xOffset +(Tile_Size -w)/2, yOffset + Tile_Size - (Tile_Size -h)/2-2);

        if (game.getWin() || game.getLose()) {
            g.setColor(new Color(255, 255, 255, 30));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(78, 139, 202));
            g.setFont(new Font(Font_Name, Font.BOLD, 48));
            if (game.getWin()) {
                g.drawString("You won!", 68, 150);
            }
            if (game.getLose()) {
                g.drawString("Game over!", 15, 130);
                g.drawString("You lose!", 44, 180);
            }
            if (game.getWin() || game.getLose()) {
                g.setFont(new Font(Font_Name, Font.PLAIN, 16));
                g.setColor(new Color(128, 128, 128, 128));
                g.drawString("Press ESC to play again", 80, getHeight() - 60);
            }
        }
    }


    private int offsetCoors(int value){
        return value*(Tiles_Margin + Tile_Size) + Tiles_Margin;

    }




    public static void main(String[] args) {
        JFrame game = new JFrame();
        game.setTitle("2048 Game");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(340, 400);
        game.setResizable(false);
        game.add(new Main());
        game.setLocationRelativeTo(null);
        game.setVisible(true);

    }
}
