package com.atipera.kulig.task.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

class BranchDtoDeserializer extends JsonDeserializer<BranchDto> {

    @Override
    public BranchDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        BranchDto branchDto = new BranchDto();
        branchDto.setName(node.get("name").asText());
        branchDto.setLastCommitSha(node.get("commit").get("sha").asText());
        return branchDto;
    }
}
