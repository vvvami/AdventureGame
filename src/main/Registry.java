package main;

import interactable.Interactable;
import interactable.entity.Knight;
import interactable.entity.Player;
import interactable.tile.Platform;
import interactable.tile.Tile;
import render.light.Light;

import java.awt.*;

public class Registry {

    public static void register() {
        int i = 0;
        int j = 0;
        while (i < Game.windowWidth * 4) {
            while (j < Game.windowHeight * 4) {
                Tile tile = new Tile(i,j);
                j += 48;
            }
            j = 0;
            i += 48;
        }

        Knight knight = new Knight(0, 0);


        Player player = new Player((float) Game.windowWidth / 2, (float) Game.windowHeight / 2);

        new Platform(player.getX(), player.getY() + 200);

        System.out.println("Registered!");
    }
}
