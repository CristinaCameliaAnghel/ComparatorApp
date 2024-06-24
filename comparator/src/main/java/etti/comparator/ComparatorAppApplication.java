package etti.comparator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"etti.comparator"})
public class ComparatorAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComparatorAppApplication.class, args);
	}

}
