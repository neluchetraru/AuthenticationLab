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
    private FileWriter fw;
    private String filename;
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
            this.filename = "./Logger/log_" + timeStamp + ".txt";
            File file = new File(filename);
            try {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    throw new RuntimeException("Failed to create new file");
                }
                fw = new FileWriter(filename, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public void log(String message) {
            try {
                fw = new FileWriter(this.filename, true);
                String sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss").format(new Date());
                fw.write("[" + sdf + "]: " + message + '\n');
                System.out.println("[" + sdf + "]: " + message + '\n');
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

}
