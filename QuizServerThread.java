import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class QuizServerThread {
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(35326);
        System.out.println("The Quiz server is running...");
        ExecutorService pool = Executors.newFixedThreadPool(20);

        while (true) {
            Socket sock = listener.accept();
            pool.execute(new Quizrunner(sock));
        }
    }

    private static class Quizrunner implements Runnable {

        private Socket socket;

        Quizrunner(Socket socket) {
            this.socket = socket;
        }

        String[] questions = { "1. What is the capital city of France?", "2. What is the chemical symbol for water?",
                "3. What planet is known as the Red Planet?" };
        String[] answers = { "Paris", "H2O", "Mars" };

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                int score = 0;

                for (int i = 0; i < questions.length; i++) {
                    out.println(questions[i]);
                    String inputMessage = in.readLine();
                    if (inputMessage.equalsIgnoreCase(answers[i])) {
                        out.println("Correct!");
                        score++;
                    } else {
                        out.println("Wrong answer.");
                    }
                }

                out.println("Your total score is " + score);
            } catch (Exception e) {
                System.out.println("Error: " + socket);
            } finally {
                try {

                    socket.close();
                } catch (IOException e) {
                }
                System.out.println("Closed: " + socket);
            }
        }
    }
}
