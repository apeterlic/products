package dev.beenary.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProductApplication {

    /**
     * The entry point of application.
     *
     * @param args [{@link String}[]] :: the input arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}