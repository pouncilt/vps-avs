package gov.va.med.avs.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tonté Pouncil on 2/4/15.
 */
@Component
public class JsonDateDeserializer extends JsonDeserializer <Date>{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException{
        if(jsonParser.getText() != null && !jsonParser.getText().trim().equals("")) {
            String date = jsonParser.getText().substring(0, jsonParser.getText().length());
            try{
                return dateFormat.parse(date);
            } catch (ParseException pe) {
                throw new RuntimeException(pe);
            }
        } else {
            return null;
        }
    }
}
