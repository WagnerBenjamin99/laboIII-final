package ar.edu.utn.frbb.tup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@CrossOrigin(origins = "http://localhost:4200")
public class App
{

    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }


}
