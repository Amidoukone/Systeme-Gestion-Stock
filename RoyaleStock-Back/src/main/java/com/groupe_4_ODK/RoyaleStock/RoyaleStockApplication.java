package com.groupe_4_ODK.RoyaleStock;

import com.groupe_4_ODK.RoyaleStock.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityConfig.class })
public class RoyaleStockApplication {

  public static void main(String[] args) {
    SpringApplication.run(RoyaleStockApplication.class, args);
  }

}
