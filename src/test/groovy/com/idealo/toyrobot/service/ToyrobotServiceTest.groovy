package com.idealo.toyrobot.service

import com.idealo.toyrobot.ToyrobotApplication
import com.idealo.toyrobot.dto.PlaceRequestDto
import com.idealo.toyrobot.model.Direction
import com.idealo.toyrobot.model.Rotation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import static com.idealo.toyrobot.util.TestUtil.createPlaceRequestDto

@SpringBootTest(classes = ToyrobotApplication, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ToyrobotServiceTest extends Specification {
    @Autowired
    private ToyrobotService toyrobotService

    def cleanup() {
        toyrobotService.removeFromTable()
    }

    @Unroll
    def "place robot to X:0, Y:0, F:#direction, rotate #n times and check position - #report"() {
        given:
        PlaceRequestDto placeRequestDto = createPlaceRequestDto(direction, 0, 0)
        toyrobotService.place(placeRequestDto)

        when:
        n.times {
            toyrobotService.rotate(rotation)
        }

        then:
        toyrobotService.report == report

        where:
        n | direction       | rotation       || report
        0 | Direction.SOUTH | Rotation.LEFT  || "X - 0, Y - 0, F - SOUTH"
        4 | Direction.SOUTH | Rotation.LEFT  || "X - 0, Y - 0, F - SOUTH"
        8 | Direction.SOUTH | Rotation.LEFT  || "X - 0, Y - 0, F - SOUTH"
        8 | Direction.SOUTH | Rotation.RIGHT || "X - 0, Y - 0, F - SOUTH"

        1 | Direction.SOUTH | Rotation.RIGHT || "X - 0, Y - 0, F - WEST"
        2 | Direction.SOUTH | Rotation.RIGHT || "X - 0, Y - 0, F - NORTH"
        3 | Direction.SOUTH | Rotation.RIGHT || "X - 0, Y - 0, F - EAST"

        1 | Direction.NORTH | Rotation.LEFT  || "X - 0, Y - 0, F - WEST"
        2 | Direction.NORTH | Rotation.LEFT  || "X - 0, Y - 0, F - SOUTH"
        3 | Direction.NORTH | Rotation.LEFT  || "X - 0, Y - 0, F - EAST"
        4 | Direction.NORTH | Rotation.LEFT  || "X - 0, Y - 0, F - NORTH"

        1 | Direction.NORTH | Rotation.RIGHT || "X - 0, Y - 0, F - EAST"
        2 | Direction.NORTH | Rotation.RIGHT || "X - 0, Y - 0, F - SOUTH"
        3 | Direction.NORTH | Rotation.RIGHT || "X - 0, Y - 0, F - WEST"
    }

    @Unroll
    def "place robot to X:#x, Y:#y, F:#direction, move #n times and check position - #report"() {
        given:
        PlaceRequestDto placeRequestDto = createPlaceRequestDto(direction, x, y)
        toyrobotService.place(placeRequestDto)

        when:
        n.times {
            toyrobotService.move()
        }

        then:
        toyrobotService.report == report

        where:
        n | direction       | x | y || report

        0 | Direction.NORTH | 0 | 0 || "X - 0, Y - 0, F - NORTH"
        4 | Direction.NORTH | 0 | 0 || "X - 0, Y - 4, F - NORTH"
        8 | Direction.NORTH | 0 | 0 || "X - 0, Y - 4, F - NORTH"
        1 | Direction.SOUTH | 3 | 3 || "X - 3, Y - 2, F - SOUTH"
    }

    @Unroll
    def "place robot x - #x, y - #y, move around the tabletop and check position - #report"() {
        given:
        PlaceRequestDto placeRequestDto = createPlaceRequestDto(direction, x, y)
        toyrobotService.place(placeRequestDto)

        when:
        toyrobotService.move()
        toyrobotService.move()
        toyrobotService.rotate(Rotation.LEFT)
        toyrobotService.move()

        then:
        toyrobotService.report == report

        where:
        x | y | direction       || report
        // correct movements
        3 | 0 | Direction.NORTH || "X - 2, Y - 2, F - WEST"
        1 | 2 | Direction.EAST  || "X - 3, Y - 3, F - NORTH"

        // movements out of the tabletop
        0 | 0 | Direction.SOUTH || "X - 1, Y - 0, F - EAST"
    }

}