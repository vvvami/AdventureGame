package main;

import interactable.Interactable;
import interactable.entity.Player;
import tile.Tile;

public class Registry {

    public static void register() {
        Tile tile = new Tile();
        Player player = new Player();


        registerSprites();
        System.out.println("Registered!");
    }

    private static void registerSprites() {
        for (Interactable interactable : Interactable.getList()) {
            interactable.registerSprites();
        }
    }
}
