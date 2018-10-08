package com.idealo.toyrobot.dto;

import com.idealo.toyrobot.model.Direction;
import com.idealo.toyrobot.model.Position;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceRequestDto {
  @NotNull
  private Direction direction;
  @NotNull
  private Position position;
}
