package mw.offers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class to launch the spring application.
 */
@SpringBootApplication
public class Launcher
{
    /**
     * Entry method for the application.
     * @param args Program arguments, expected to be empty.
     */
    public static void main(String[] args)
    {
        SpringApplication.run(Launcher.class, args);
    }
}
