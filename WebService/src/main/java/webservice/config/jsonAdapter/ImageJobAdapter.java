package webservice.config.jsonAdapter;

import com.google.gson.*;
import trapx00.tagx00.publicdatas.mission.image.ImageJob;
import trapx00.tagx00.vo.mission.image.ImageMissionType;

import java.lang.reflect.Type;

public class ImageJobAdapter implements JsonDeserializer<ImageJob> {

    @Override
    public ImageJob deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        return gson.fromJson(json,
                ImageMissionType.valueOf(
                        json.getAsJsonObject().get("type").getAsString()
                ).clazz);
    }
}
