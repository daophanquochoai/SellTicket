package doctorhoai.learn.roomservice.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;


@Converter
@Slf4j
public class PositionChairConverter implements AttributeConverter<Integer[][], String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Integer[][] positionChair) {
        try{
            return objectMapper.writeValueAsString(positionChair);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new IllegalArgumentException("Error converting Map to String: " + e.getMessage());
        }
    }

    @Override
    public Integer[][] convertToEntityAttribute(String s) {
        try{
            return objectMapper.readValue(s, new TypeReference<Integer[][]>(){});
        }catch (Exception e){
            log.error(e.getMessage());
            throw new IllegalArgumentException("Error converting String to Map: " + e.getMessage());
        }
    }
}
