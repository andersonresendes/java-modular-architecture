package io.pismo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"io.pismo.demo"}
)
public class SpringSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSampleApplication.class, args);
  }

}
