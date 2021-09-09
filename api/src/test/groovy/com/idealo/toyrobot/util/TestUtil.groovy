package com.idealo.toyrobot.util

import com.idealo.toyrobot.model.Direction
import com.idealo.toyrobot.dto.PlaceRequestDto
import com.idealo.toyrobot.model.Position
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType

import java.nio.charset.Charset

class TestUtil {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))

    static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
        return mapper.writeValueAsBytes(object)
    }

    static PlaceRequestDto createPlaceRequestDto(Direction direction, int x, int y) {
        return new PlaceRequestDto(direction, new Position(x, y))
    }
}
