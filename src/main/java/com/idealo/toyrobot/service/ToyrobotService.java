package com.idealo.toyrobot.service;

import com.idealo.toyrobot.dto.PlaceRequestDto;
import com.idealo.toyrobot.model.Rotation;
import com.idealo.toyrobot.model.Toyrobot;

public interface ToyrobotService {
  Toyrobot place(PlaceRequestDto placeRobotRequest);

  Toyrobot move();

  Toyrobot rotate(Rotation rotation);

  void removeFromTable();

  String getReport();
}
