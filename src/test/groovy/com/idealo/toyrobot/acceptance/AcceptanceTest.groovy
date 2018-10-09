package com.idealo.toyrobot.acceptance

import com.idealo.toyrobot.controller.ToyrobotController
import com.idealo.toyrobot.meta.Endpoints
import com.idealo.toyrobot.model.Direction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.idealo.toyrobot.util.TestUtil.APPLICATION_JSON_UTF8
import static com.idealo.toyrobot.util.TestUtil.convertObjectToJsonBytes
import static com.idealo.toyrobot.util.TestUtil.createPlaceRequestDto
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ToyrobotController)
class AcceptanceTest extends Specification {
    @Autowired
    MockMvc mockMvc

    def cleanup() {
        mockMvc.perform(delete("${Endpoints.ROBOTS}/remove"))
    }


    def "should place robot on the tabletop and move around"() {
        given:
        def placeRequestDto = createPlaceRequestDto(Direction.EAST, 1, 2)

        when:
        mockMvc.perform(post("${Endpoints.ROBOTS}/place")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(placeRequestDto)))
        and:
        2.times {
            mockMvc.perform(put("${Endpoints.ROBOTS}/move")).andExpect(status().isOk())
        }
        and:
        mockMvc.perform(put("${Endpoints.ROBOTS}/left")).andExpect(status().isOk())
        and:
        mockMvc.perform(put("${Endpoints.ROBOTS}/move")).andExpect(status().isOk())

        then:
        mockMvc.perform(get("${Endpoints.ROBOTS}/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.report').value("X - 3, Y - 3, F - NORTH"))
    }

    def "should place robot on the tabletop and move"() {
        given:
        def placeRequestDto = createPlaceRequestDto(Direction.NORTH, 0, 0)

        when:
        mockMvc.perform(post("${Endpoints.ROBOTS}/place")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(placeRequestDto)))
        and:
        mockMvc.perform(put("${Endpoints.ROBOTS}/move")).andExpect(status().isOk())

        then:
        mockMvc.perform(get("${Endpoints.ROBOTS}/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.report').value("X - 0, Y - 1, F - NORTH"))
    }

    def "should place robot on the tabletop and rotate"() {
        given:
        def placeRequestDto = createPlaceRequestDto(Direction.NORTH, 0, 0)

        when:
        mockMvc.perform(post("${Endpoints.ROBOTS}/place")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(placeRequestDto)))
        and:
        mockMvc.perform(put("${Endpoints.ROBOTS}/left")).andExpect(status().isOk())

        then:
        mockMvc.perform(get("${Endpoints.ROBOTS}/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.report').value("X - 0, Y - 0, F - WEST"))
    }

    def "shouldn't move robot when it's not on the tabletop"() {
        when:
        mockMvc.perform(put("${Endpoints.ROBOTS}/move")).andExpect(status().isOk())
        and:
        mockMvc.perform(put("${Endpoints.ROBOTS}/left")).andExpect(status().isOk())

        then:
        mockMvc.perform(get("${Endpoints.ROBOTS}/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.report').value("The robot is missing"))
    }
}