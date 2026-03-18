import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Matrix2D {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Utilizare: java Matrix2D <n> <forma: rectangle/circle>");
            return;
        }

        int n = Integer.parseInt(args[0]);
        String shape = args[1].toLowerCase();

        long startTime = System.nanoTime();

        int[][] image = new int[n][n];

        if (shape.equals("rectangle")) {
            drawRectangle(image, n);
        } else if (shape.equals("circle")) {
            drawCircle(image, n);
        } else {
            System.out.println("Forma necunoscuta. Foloseste 'rectangle' sau 'circle'.");
            return;
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // conversie in milisecunde

        if (n > 100) {
            System.out.println("Timpul de executie: " + duration + " ms");
        } else {
            System.out.println(buildStringRepresentation(image, n));
        }
    }


    private static void drawRectangle(int[][] matrix, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 255;
            }
        }

        int offset = n / 4;
        for (int i = offset; i < n - offset; i++) {
            for (int j = offset; j < n - offset; j++) {
                matrix[i][j] = 0;
            }
        }
    }


    private static void drawCircle(int[][] matrix, int n) {
        // avem deja 0 peste tot de la initializare

        int center = n / 2;
        int radius = n / 3;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int dx = i - center;
                int dy = j - center;
                if (dx * dx + dy * dy <= radius * radius) {
                    matrix[i][j] = 255;
                }
            }
        }
    }

    private static String buildStringRepresentation(int[][] matrix, int n) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    sb.append("."); // negru pentru 0 ██
                } else {
                    sb.append("0"); // alb pentru 255 ░░
                }
            }
            sb.append("\n"); // Trecem la linia urmatoare
        }

        return sb.toString();
    }
}