package com.idealo.toyrobot;

import com.idealo.toyrobot.config.ToyrobotConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ToyrobotConfig.class)
public class ToyrobotApplication {

  public static void main(String[] args) {
    SpringApplication.run(ToyrobotApplication.class, args);
  }
}