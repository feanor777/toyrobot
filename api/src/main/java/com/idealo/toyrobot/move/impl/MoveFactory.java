package com.idealo.toyrobot.move.impl;

import com.idealo.toyrobot.model.Direction;
import com.idealo.toyrobot.move.Move;

import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoveFactory {
  private final List<Move> moves;

  private final Map<Direction, Move> directionMove = new EnumMap<>(Direction.class);

  @PostConstruct
  private void initDirectionMove() {
    directionMove.putAll(moves.stream().collect(Collectors.toMap(Move::getDirectionType, Function.identity())));
  }

  public Move getMove(Direction direction) {
    return directionMove.get(direction);
  }
}
