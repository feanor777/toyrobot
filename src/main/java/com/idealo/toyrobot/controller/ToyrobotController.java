package com.idealo.toyrobot.controller;

import com.idealo.toyrobot.dto.PlaceRequestDto;
import com.idealo.toyrobot.dto.ReportDto;
import com.idealo.toyrobot.meta.Endpoints;
import com.idealo.toyrobot.model.Rotation;
import com.idealo.toyrobot.model.Toyrobot;
import com.idealo.toyrobot.service.ToyrobotService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.ROBOTS)
public class ToyrobotController {
  private final ToyrobotService toyrobotService;

  @PostMapping("/place")
  @ResponseStatus(HttpStatus.CREATED)
  public Toyrobot place(@RequestBody @Valid PlaceRequestDto placeRobotRequest) {
    return toyrobotService.place(placeRobotRequest);
  }

  @PutMapping("/move")
  public Toyrobot move() {
    return toyrobotService.move();
  }

  @PutMapping("/left")
  public Toyrobot left() {
    return toyrobotService.rotate(Rotation.LEFT);
  }

  @PutMapping("/right")
  public Toyrobot right() {
    return toyrobotService.rotate(Rotation.RIGHT);
  }

  @GetMapping("/report")
  public ReportDto report() {
    String report = toyrobotService.getReport();
    return new ReportDto(report);
  }

  @DeleteMapping("/remove")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remove() {
    toyrobotService.removeFromTable();
  }
}
