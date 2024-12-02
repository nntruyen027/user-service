package qbit.entier.user_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class DataInitializer implements CommandLineRunner {
    private static final String UPLOADS_DIR = "uploads";

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing data...");
        createUploadsDirectory();
    }

    private void createUploadsDirectory() {
        Path path = Paths.get(UPLOADS_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                System.out.println("Uploads directory created.");
            } catch (Exception e) {
                System.out.println("Failed to create uploads directory.");
            }
        }
    }
}