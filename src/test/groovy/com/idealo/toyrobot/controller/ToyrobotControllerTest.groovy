package com.idealo.toyrobot.controller

import com.idealo.toyrobot.controller.ToyrobotController
import com.idealo.toyrobot.dto.PlaceRequestDto
import com.idealo.toyrobot.meta.Endpoints
import com.idealo.toyrobot.model.Direction
import com.idealo.toyrobot.model.Position
import com.idealo.toyrobot.service.ToyrobotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.idealo.toyrobot.util.TestUtil.APPLICATION_JSON_UTF8
import static com.idealo.toyrobot.util.TestUtil.convertObjectToJsonBytes
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ToyrobotController)
class ToyrobotControllerTest extends Specification {
    @Autowired
    MockMvc mockMvc

    @Autowired
    private ToyrobotService toyrobotService

    def cleanup() {
        toyrobotService.removeFromTable()
    }

    def "should place robot on the tabletop"() {
        given:
        def placeRequestDto = createPlaceRequestDto(0, 0)

        expect:
        mockMvc.perform(post("${Endpoints.ROBOTS}/place")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(placeRequestDto))
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath('$.direction').value("NORTH"))
                .andExpect(jsonPath('$.position.x').value(0))
                .andExpect(jsonPath('$.position.y').value(0))
    }

    def "shouldn't place robot on the tabletop if the coordinates are out of the tabletop"() {
        given:
        def placeRequestDto = createPlaceRequestDto(5, 5)

        expect:
        mockMvc.perform(post("${Endpoints.ROBOTS}/place")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(placeRequestDto))
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath('$.direction').value(null))
                .andExpect(jsonPath('$.position').value(null))
    }

    def "shouldn't move robot if not placed on the tabletop"() {
        expect:
        mockMvc.perform(put("${Endpoints.ROBOTS}/move"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath('$.direction').value(null))
                .andExpect(jsonPath('$.position').value(null))
    }

    def "shouldn't rotate robot left if not placed on the tabletop"() {
        expect:
        mockMvc.perform(put("${Endpoints.ROBOTS}/left"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath('$.direction').value(null))
                .andExpect(jsonPath('$.position').value(null))
    }

    def "shouldn't rotate robot right if not placed on the tabletop"() {
        expect:
        mockMvc.perform(put("${Endpoints.ROBOTS}/right"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath('$.direction').value(null))
                .andExpect(jsonPath('$.position').value(null))
    }

    def "should report that robot is missing when robot is not placed on the tabletop"() {
        expect:
        mockMvc.perform(get("${Endpoints.ROBOTS}/report"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath('$.report').value("The robot is missing"))
    }

    def "should report robot place  when robot is on the tabletop"() {
        given:
        toyrobotService.place(new PlaceRequestDto(Direction.WEST, new Position(1, 3)))

        expect:
        mockMvc.perform(get("${Endpoints.ROBOTS}/report"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath('$.report').value("X - 1, Y - 3, F - WEST"))
    }

    def "should remove robot from tabletop"() {
        given:
        toyrobotService.place(new PlaceRequestDto(Direction.NORTH, new Position(0, 0)))

        expect:
        mockMvc.perform(delete("${Endpoints.ROBOTS}/remove"))
                .andExpect(status().isNoContent())
                .andDo(print())
    }

    PlaceRequestDto createPlaceRequestDto(int x, int y) {
        return new PlaceRequestDto(Direction.NORTH, new Position(x, y))
    }
}