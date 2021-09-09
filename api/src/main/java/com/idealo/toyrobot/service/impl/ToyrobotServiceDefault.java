package com.idealo.toyrobot.service.impl;

import com.idealo.toyrobot.dto.PlaceRequestDto;
import com.idealo.toyrobot.model.Position;
import com.idealo.toyrobot.model.Rotation;
import com.idealo.toyrobot.model.Tabletop;
import com.idealo.toyrobot.model.Toyrobot;
import com.idealo.toyrobot.move.Move;
import com.idealo.toyrobot.move.impl.MoveFactory;
import com.idealo.toyrobot.service.ToyrobotService;
import com.idealo.toyrobot.utils.ReportConstants;
import com.idealo.toyrobot.utils.RotationUtil;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToyrobotServiceDefault implements ToyrobotService {
  private final Toyrobot toyrobot;
  private final Tabletop tabletop;
  private final MoveFactory moveFactory;

  public Toyrobot place(PlaceRequestDto placeRobotRequest) {
    if (isOnTabletop(placeRobotRequest.getPosition())) {
      toyrobot.setDirection(placeRobotRequest.getDirection());
      toyrobot.setPosition(placeRobotRequest.getPosition());
    }
    return toyrobot;
  }

  public Toyrobot move() {
    if (isNotPlaced(toyrobot)) {
      return toyrobot;
    }
    Move move = moveFactory.getMove(toyrobot.getDirection());
    Position newPosition = move.move(toyrobot.getPosition());
    if (isOnTabletop(newPosition)) {
      toyrobot.setPosition(newPosition);
    }
    return toyrobot;
  }

  @Override
  public Toyrobot rotate(Rotation rotation) {
    if (rotation.equals(Rotation.RIGHT)) {
      RotationUtil.rotateRight(toyrobot);
    } else {
      RotationUtil.rotateLeft(toyrobot);
    }
    return toyrobot;
  }

  @Override
  public void removeFromTable() {
    toyrobot.setDirection(null);
    toyrobot.setPosition(null);
  }

  @Override
  public String getReport() {
    if (isNotPlaced(toyrobot)) {
      return ReportConstants.MISSING_TEMPLATE;
    }
    return String.format(ReportConstants.PLACE_TEMPLATE, toyrobot.getPosition().getX(),
        toyrobot.getPosition().getY(), toyrobot.getDirection());
  }

  private boolean isNotPlaced(Toyrobot robot) {
    return robot.getDirection() == null || robot.getPosition() == null;
  }

  private boolean isOnTabletop(Position position) {
    return position.getY() >= 0 && position.getY() < tabletop.getYDimensionSize()
        && position.getX() >= 0 && position.getX() < tabletop.getXDimensionSize();
  }
}
