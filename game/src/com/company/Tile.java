package com.company;

import java.awt.*;

public class Tile {
    public int number;
    public Tile() {
        this(0);
    }
    public Tile(int num)
    {
        number= num;
    }
    public boolean isEmpty()
    {
        return number==0;
    }
    public Color getNumberColor()
    {
        return number <16 ? new Color(0x776e65) : new Color(0xf9f6f2);
    }


    public Color getBackground()
    {
        switch (number){
            case 2: return new Color(0xeee4da);
            case 4: return new Color(0xede0c8);
            case 8: return new Color(0xf2b179);
            case 16: return new Color(0xf59563);
            case 32: return new Color(0xf67c5f);
            case 64: return new Color(0xf65e3b);
            case 128: return new Color(0xedcf72);
            case 256: return new Color(0xedcc61);
            case 512: return new Color(0xedc850);
            case 1024: return new Color(0xedc53f);
            case 2048: return new Color(0xedc22e);
        }
        return new Color(0xcdc1b4);
    }
}
