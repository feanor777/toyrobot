package com.idealo.toyrobot.move;

import com.idealo.toyrobot.model.Direction;
import com.idealo.toyrobot.model.Position;

public interface Move {
  Position move(Position oldPosition);

  Direction getDirectionType();

  default Position getPosition(int x, int i) {
    Position position = new Position();
    position.setX(x);
    position.setY(i);
    return position;
  }
}
