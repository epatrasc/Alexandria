/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JSONManager {

    ObjectMapper objectMapper = new ObjectMapper();

    // Traduzione da stringa JSON a oggetto Java di tipo generico T
    public <T> T parseJson(String json, Class<T> targetType) throws Exception {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return objectMapper.readValue(json, targetType);
    }

    //Traduzione da oggetto Java di tipo generico T a stringa JSON
    public <T> String serializeJson(T o) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String stringaJSON = "";
        // ora genero stringa JSON
        try {
            stringaJSON = mapper.writeValueAsString(o);
        } catch (JsonGenerationException e) {
            System.out.println(e.getMessage());
        } catch (JsonMappingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return stringaJSON;
    }
}
