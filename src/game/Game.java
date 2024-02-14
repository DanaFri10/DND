package game;

import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;
import game.tiles.Tile;
import game.tiles.TileFactory;
import game.utils.AbilityCastInfo;
import game.utils.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Game {
    private List<List<String>> levels;
    private TileFactory tileFactory;
    private GameBoard board;
    private Scanner scanner;
    private int currentLevel;
    private Player player;
    private List<Enemy> enemies;

    public Game(String levels_dir) {
        levels = new ArrayList<List<String>>();
        tileFactory = new TileFactory((message) -> System.out.print(message),(e) -> removeEnemy(e));
        scanner = new Scanner(System.in);
        currentLevel = 0;
        initGame(levels_dir);
    }

    private void initGame(String levels_dir) {
        int i = 1;
        while (true) {
            List<String> level = readLevel(levels_dir + "/level" + i + ".txt");
            if (level == null)
                break;
            levels.add(level);
            i++;
        }
    }

    private static List<String> readLevel(String path) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            return null;
        }
        return lines;
    }

    public void startGame() {
        while(!selectPlayerMenu());
        loadLevel();
        while (!isGameOver())
            gameStep();
        System.out.println(board);
        System.out.println(player.describe());
        System.out.println("Game over.");
    }

    private boolean isGameOver() {
        return currentLevel>=levels.size() || !player.isAlive();
    }

    private void gameStep() {
        System.out.println(board);
        System.out.println(player.describe());
        playerStep();
        enemiesStep();
    }

    private void playerStep() {
        String playerInput = scanner.nextLine();
        while (!playerInput.equals("w") && !playerInput.equals("a") && !playerInput.equals("s") && !playerInput.equals("d") && !playerInput.equals("e") && !playerInput.equals("q"))
            playerInput = scanner.nextLine();
        char playerMove = playerInput.charAt(0);
        player.onGameTick(playerMove, (char move) -> board.moveTo(player, playerMove), () -> new AbilityCastInfo(enemies,player));
    }

    private void enemiesStep() {
        for(Enemy e: enemies) {
            e.onGameTick(player, (char move) -> board.moveTo(e, move));
        }
    }

    public boolean selectPlayerMenu() {
        System.out.println("Select player:");
        List<Player> playersList = tileFactory.listPlayers();
        for (int i = 0; i < playersList.size(); i++)
            System.out.println(i+1+". " + playersList.get(i).describe());
        try {
            int playerIndex = scanner.nextInt();
            scanner.nextLine();
            player = tileFactory.producePlayer(playerIndex-1);
            System.out.println("You have selected:");
            System.out.println(player.getName());
            return true;
        }
        catch (InputMismatchException ex)
        {
            System.out.println("Not a number.");
            scanner.next();
        }
        catch (Exception e) {
            System.out.println("Not a valid player index.");
        }
        return false;
    }

    public void loadLevel() {
        List<String> levelText = levels.get(currentLevel);
        Tile[][] level = new Tile[levelText.size()][];
        for (int i = 0; i < levelText.size(); i++) {
            String line = levelText.get(i);
            level[i] = new Tile[line.length()];
            for (int j = 0; j < line.length(); j++) {
                level[i][j] = tileFactory.produceTile(line.charAt(j),new Position(j,i));
            }
        }
        board = new GameBoard(level);
        enemies = tileFactory.getEnemies();
        //for (game.tiles.units.enemies.Enemy e : enemies) {
        //    e.addObserver(this);
        //    e.addObserver(player);
        //}
    }

    public void levelUp() {
        currentLevel++;
        if(!isGameOver())
            loadLevel();
    }

    private void removeEnemy(Enemy e) {
        board.remove(e);
        enemies.remove(e);
        if(enemies.isEmpty())
            levelUp();
    }
}
