package com.atipera.kulig.task.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

class RepositoryDtoDeserializer extends JsonDeserializer<RepositoryDto> {

    @Override
    public RepositoryDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        RepositoryDto repositoryDto = new RepositoryDto();
        repositoryDto.setName(node.get("name").asText());
        repositoryDto.setOwnerLogin(node.get("owner").get("login").asText());
        repositoryDto.setNotFork(!node.get("fork").asBoolean());

        return repositoryDto;
    }
}
