package webservice.config.jsonAdapter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.Gson;
import trapx00.tagx00.vo.mission.instance.InstanceDetailVo;

import java.io.IOException;

public class InstanceDetailAdapter extends JsonDeserializer<InstanceDetailVo> {
    @Override
    public InstanceDetailVo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Gson gson = new Gson();
        return null;
    }
}
