package main;

import interactable.entity.Player;
import tile.Tile;

public class Registry {

    public static void register() {
        Tile tile = new Tile();
        Player player = new Player();
        System.out.println("Registered!");
    }
}
