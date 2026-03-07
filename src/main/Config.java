package main;

import java.io.*;

public class Config {
    private Game game;
    public Config(Game game) {
        this.game = game;
    }

    public void saveConfig() {
        try {
            BufferedWriter configWriter = new BufferedWriter(new FileWriter("config.txt"));
            if (game.debug) {
                configWriter.write("debug");
            } else  {
                configWriter.write("noDebug");
            }
            configWriter.newLine();

            if (game.fullscreen) {
                configWriter.write("fullscreen");
            } else  {
                configWriter.write("windowed");
            }
            configWriter.newLine();

            configWriter.write(String.valueOf(game.renderFPS));
            configWriter.newLine();
            configWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConfig() {
        try {
            BufferedReader configReader = new BufferedReader(new FileReader("config.txt"));

            String read = configReader.readLine();
            if (read.equals("debug")) {
                game.debug = true;
            }
            if (read.equals("noDebug")) {
                game.debug = false;
            }

            read = configReader.readLine();
            if (read.equals("fullscreen")) {
                game.fullscreen = true;
            }
            if (read.equals("windowed")) {
                game.fullscreen = false;
            }

            read = configReader.readLine();
            game.renderFPS = Integer.parseInt(read);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
