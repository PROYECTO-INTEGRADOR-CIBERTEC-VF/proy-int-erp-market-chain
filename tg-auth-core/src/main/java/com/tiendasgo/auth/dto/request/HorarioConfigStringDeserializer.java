package com.tiendasgo.auth.dto.request;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class HorarioConfigStringDeserializer extends JsonDeserializer<String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonToken token = parser.currentToken();

        if (token == JsonToken.VALUE_NULL) {
            return null;
        }

        if (token == JsonToken.VALUE_STRING) {
            String raw = parser.getValueAsString();
            return raw == null ? null : raw.trim();
        }

        if (token == JsonToken.START_OBJECT || token == JsonToken.START_ARRAY) {
            JsonNode node = parser.readValueAsTree();
            return OBJECT_MAPPER.writeValueAsString(node);
        }

        return parser.getValueAsString();
    }
}

