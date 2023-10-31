package Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
        private static Logger instance = null;
    private final FileWriter fw;
        public static Logger getInstance() {
            if (instance == null) {
                instance = new Logger();
            }
            return instance;
        }
        private Logger() {
            try {
                Files.createDirectories(Paths.get("./Logger"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
            String fileName = "./Logger/log_" + timeStamp + ".txt";
            File file = new File(fileName);
            try {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    throw new RuntimeException("Failed to create new file");
                }
                fw = new FileWriter(fileName, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public void log(String message) {
            try {
                String sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss").format(new Date());
                fw.write("[" + sdf + "]: " + message + '\n');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void stop() {
            try {
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
