package VentaObserver;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.FileHandler;
import java.io.File;
import java.io.IOException;


public class LoggerConfig {
    private static final String LOG_FILE = "logs/cobro-events.log";

    public static void configureLogger() {
        try {
            File logDir = new File("logs");
            if (!logDir.exists()) {
                boolean created = logDir.mkdir();
                if (created) {
                    System.out.println("Directorio de logs creado en " + logDir.getAbsolutePath());
                } else {
                    System.err.println("No se pudo crear el directorio de logs.");
                }
            } else {
                System.out.println("Directorio de logs creado en " + logDir.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Error al crear el directorio de logs: " + e.getMessage());
        }
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);

        for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new SimpleFormatter());
        rootLogger.addHandler(consoleHandler);

        try {
            // Agregar un FileHandler para guardar logs en un archivo
            FileHandler fileHandler = new FileHandler("logs/cobro-events.log", true);
            fileHandler.setLevel(Level.INFO);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);

            File logFile = new File(LOG_FILE);
            System.out.println("Logger configurado para guardar eventos en " + logFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("No se pudo crear el archivo de log: " + e.getMessage());
        }
    }
}
