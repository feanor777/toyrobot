package com.idealo.toyrobot.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Tabletop {
  @Value("${tabletop.xDimensionSize}")
  private int xDimensionSize;
  @Value("${tabletop.yDimensionSize}")
  private int yDimensionSize;
}
