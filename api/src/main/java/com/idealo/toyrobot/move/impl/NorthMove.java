package com.idealo.toyrobot.move.impl;

import com.idealo.toyrobot.model.Direction;
import com.idealo.toyrobot.model.Position;
import com.idealo.toyrobot.move.Move;

import org.springframework.stereotype.Component;

@Component
public class NorthMove implements Move {
  @Override
  public Position move(Position oldPosition) {
    if (oldPosition != null) {
      return getPosition(oldPosition.getX(), oldPosition.getY() + 1);
    } else {
      return new Position();
    }
  }

  @Override
  public Direction getDirectionType() {
    return Direction.NORTH;
  }
}
