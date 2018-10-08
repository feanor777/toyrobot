package com.idealo.toyrobot.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import com.idealo.toyrobot.model.Direction;
import com.idealo.toyrobot.model.Toyrobot;

import static com.idealo.toyrobot.model.Direction.EAST;
import static com.idealo.toyrobot.model.Direction.NORTH;
import static com.idealo.toyrobot.model.Direction.SOUTH;
import static com.idealo.toyrobot.model.Direction.WEST;

public final class RotationUtil {
  private RotationUtil() {

  }

  private static final BiMap<Direction, Integer> DIRECTION_INDEX = ImmutableBiMap
      .of(NORTH, 0, EAST, 1, SOUTH, 2, WEST, 3);
  private static final int MODULE = 4;

  public static void rotateRight(Toyrobot toyrobot) {
    if (toyrobot.getDirection() != null) {
      int nextPosition = DIRECTION_INDEX.get(toyrobot.getDirection()) + 1;
      Direction direction = DIRECTION_INDEX.inverse().get(nextPosition % MODULE);
      toyrobot.setDirection(direction);
    }
  }

  public static void rotateLeft(Toyrobot toyrobot) {
    if (toyrobot.getDirection() != null) {
      int nextPosition = DIRECTION_INDEX.get(toyrobot.getDirection()) - 1 + MODULE;
      Direction direction = DIRECTION_INDEX.inverse().get(nextPosition % MODULE);
      toyrobot.setDirection(direction);
    }
  }
}
