package com.company;
import com.company.Board;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Game {

    private boolean Win = false;
    private boolean Lose = false;
    private String key="";
    Board board =  new Board();

    public boolean getWin() {
        return Win;
    }

    public boolean getLose() {
        return Lose;
    }


    public void setLose(boolean b) {
        Lose = b;
    }

    public void resetGame() {
        Win = false;
        Lose = false;
        board = new Board();
        addTile();
        addTile();

    }

    public void left() {
        key="left";
        boolean needAddTile = false;
        for (int i = 0; i < 4; i++) {
            Tile[] line = board.getLine(i);
            Tile[] merged = mergeLine(moveLine(line));
            board.setLine(i, merged);
            if (!needAddTile && !compare(line, merged)) {
                needAddTile = true;
            }
        }
        if (needAddTile) {
            addTile();
        }
    }

    public void right() {
        key="right";
        boolean needAddTile = false;
        for (int i = 0; i < 4; i++) {
            Tile[] line = board.getLine(i);
            Tile[] merged = mergeLine(moveLine(line));
            board.setLine(i, merged);
            if (!needAddTile && !compare(line, merged)) {
                needAddTile = true;
            }
        }
        if (needAddTile) {
            addTile();
        }
    }

    public void up() {
        key="up";
        boolean needAddTile = false;
        for(int i =0; i< 4;i++){
            Tile[] line = board.up_getLine(i);
            Tile[] merged= mergeLine(moveLine(line));
            if(!needAddTile && !compare(line, merged)) {
                needAddTile = true;
            }
            board.up_setLine(i,merged);
        }
        if(needAddTile){
            addTile();
        }
    }

    public void down() {
        key="down";
        boolean needAddTile = false;
        for(int i =0; i< 4;i++){
            Tile[] line = board.down_getLine(i);
            Tile[] merged= mergeLine(moveLine(line));
            if(!needAddTile && !compare(line, merged)) {
                needAddTile = true;
            }
            board.down_setLine(i,merged);
        }
        if(needAddTile){
            addTile();
        }
    }

    private void addTile() {
        List<Tile> list = board.availableSpace();
        if (!board.availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Tile emptyTile = list.get(index);
            emptyTile.number = Math.random() < 0.9 ? 2 : 4;
        }
    }

    private boolean isFull() {
        return board.availableSpace().size() == 0;
    }

    public boolean canMove() {
        if (!isFull()) {
            return true;
        }

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Tile t = board.tileAt(x, y);
                if ((x < 3 && t.number == board.tileAt(x + 1, y).number)|| ((y < 3) && t.number == board.tileAt(x, y + 1).number)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean compare(Tile[] line1, Tile[] line2) {
        for (int i = 0; i < line1.length; i++) {
            if (line1[i].number != line2[i].number) {
                return false;
            }
        }
        return true;
    }


    private Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> l = new LinkedList<Tile>();
        for (int i = 0; i < 4; i++) {
            if (!oldLine[i].isEmpty())
                l.addLast(oldLine[i]);
        }
        if (l.size() == 0) {
            return oldLine;
        }
        else {
            Tile[] newLine = new Tile[4];
            ensureSize(l, 4);
            for (int i = 0; i < 4; i++) {
                newLine[i] = l.removeFirst();
            }
            return newLine;
        }
    }

    private Tile[] mergeLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<Tile>();
        for (int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) {
            int num = oldLine[i].number;
            if (i < 3 && oldLine[i].number == oldLine[i + 1].number) {
                num *= 2;
                int ourTarget = 2048;
                if (num == ourTarget) {
                    Win = true;
                }
                i++;
            }
            list.add(new Tile(num));
        }
        if (list.size() == 0) {
            return oldLine;
        }
        else {
            if(key.equals("right"))
                ensureSize(list,4,0);
            else
                ensureSize(list,4);
            return list.toArray(new Tile[4]);
        }
    }

    private void ensureSize(List<Tile> l, int s) {
        while (l.size() != s) {
            l.add(new Tile());
        }
    }

    private void ensureSize(List<Tile> l, int s,int p) {
        while (l.size() != s) {
            l.add(p,new Tile());
        }
    }

    public Tile[] getBoard() {
        return board.getTiles();
    }
}
