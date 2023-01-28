package pl.juraszek.sociabletests.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("domain-test")
@SpringBootApplication
public class ApplicationDomain {

   public static void main(String[] args) {
      SpringApplication.run(ApplicationDomain.class, args);
   }
}
