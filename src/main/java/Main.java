import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.EndOfFileException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Main {
    public static void main(String[] args) throws Exception {

        // Build the terminal
        Terminal terminal = null;
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (Exception e) {
            // If terminal can't be created, fallback to Scanner (for tester)
        }

        LineReader reader = null;
        if (terminal != null) {
            reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true)
                    .build();
        }

        // Fallback: if terminal is null, just use Scanner
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            String line;
            if (reader != null) {
                try {
                    line = reader.readLine("$ ");
                } catch (UserInterruptException | EndOfFileException e) {
                    break;
                }
            } else {
                System.out.print("$ ");
                line = scanner.nextLine();
            }

            if (line.isEmpty()) continue;

            // Run builtins
            if (!Builtins.runCommand(line)) {
                // Run external programs
                if (!Executor.runProgram(line)) {
                    System.out.println(line + ": command not found");
                }
            }
        }
    }
}
