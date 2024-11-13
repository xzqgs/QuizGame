import java.io.*;
import java.net.*;
import java.util.*;

public class QuizClient2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 35326);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String question;

        Scanner scanner = new Scanner(System.in);

        while ((question = in.readLine()) != null) {
            System.out.println(question);
            String answer = scanner.nextLine();
            out.println(answer);
            String feedback = in.readLine();
            System.out.println(feedback);

        }

        socket.close();

    }
}
