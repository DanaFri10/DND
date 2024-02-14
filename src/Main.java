import game.Game;

public class Main {

    public static void main(String[] args) {
        //if (args.length < 1)
        //    System.out.println("Expected the levels directory as an argument.");
        //else {
            //String levels_dir = args[0];
            String levels_dir = "C:\\Users\\danaf\\OneDrive\\שולחן העבודה\\uni\\OOP\\עבודה 3\\Assignment3\\levels_dir";
            Game game = new Game(levels_dir);
            game.startGame();
        //}
    }
}
