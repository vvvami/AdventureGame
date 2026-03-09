package main;

import interactable.Interactable;
import interactable.entity.Knight;
import interactable.entity.Player;
import interactable.tile.Platform;
import interactable.tile.Tile;

import java.awt.*;

public class Registry {

    public static void register() {
//        int i = 0;
//        int j = 0;
//        while (i < Game.windowWidth * 4) {
//            while (j < Game.windowHeight * 4) {
//                Tile tile = new Tile(i,j);
//                j += 48;
//            }
//            j = 0;
//            i += 48;
//        }



        Player player = new Player((float) Game.windowWidth / 2, (float) Game.windowHeight / 2);
        Knight knight = new Knight(player.getX() + 60, player.getY());


        new Platform(player.getX(), player.getY() + 200);
        new Platform(player.getX() + Game.tileSize, player.getY() + 200);
        new Platform(player.getX() + Game.tileSize * 2, player.getY() + 200);
        new Platform(player.getX() + Game.tileSize * 3, player.getY() + 200);
        new Platform(player.getX(), player.getY() + 220);
        new Platform(player.getX() + Game.tileSize, player.getY() + 220);
        new Platform(player.getX() + Game.tileSize * 2, player.getY() + 220);
        new Platform(player.getX() + Game.tileSize * 3, player.getY() + 220);



        System.out.println("Registered!");
    }
}
