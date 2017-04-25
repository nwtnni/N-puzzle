package puzzle;

import java.util.Scanner;

public class Main {

    /*
     * Temporary
     */
    public static void main (String[] args) {

        int m;
        int n;

        try {
            m = Integer.parseInt(args[0]);
            n = Integer.parseInt(args[1]);
        } catch(Exception e){
            System.out.println("Usage: java -jar N-puzzle.jar <m> <n>");
            System.out.println("This generates an m x n initialized puzzle.");
            return;
        }

        AbstractPuzzle p = new ArrayPuzzle(m, n);
        p.randomize();
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the tile to move, or 'quit' to quit.\n"); 

        while (true) {

            System.out.println(p);

            if (p.solved()) {
                System.out.println("You win!"); 
                return;
            }

            String input = s.next();

            try {
                int i = Integer.parseInt(input);
                if (!p.move(i)) {
                    System.out.println("Invalid move. Enter 'quit' to quit.\n"); 
                } else {
                    System.out.print("\n"); 
                }
            } catch(Exception e){
                if (input.equals("quit")) {
                    break;
                }
                System.out.println("Invalid move. Enter 'quit' to quit.\n"); 
            }
        }
        s.close();
    }

}
