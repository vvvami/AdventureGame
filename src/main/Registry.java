package main;

import interactable.Interactable;
import interactable.entity.Knight;
import interactable.entity.Player;
import interactable.tile.Tile;

public class Registry {

    public static void register() {
        int i = 0;
        int j = 0;
        while (i < GamePanel.windowWidth * 4) {
            while (j < GamePanel.windowHeight * 4) {
                Tile tile = new Tile(i,j);
                j += 48;
            }
            j = 0;
            i += 48;
        }

        Knight knight = new Knight();

        Player player = (Player) new Player().setX(GamePanel.windowWidth / 2).setY(GamePanel.windowHeight / 2);
        registerSprites();
        System.out.println("Registered!");
    }

    private static void registerSprites() {
        for (Interactable interactable : Interactable.getList()) {
            interactable.registerSprites();
        }
    }
}
