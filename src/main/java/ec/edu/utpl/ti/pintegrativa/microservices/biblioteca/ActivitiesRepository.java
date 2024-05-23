package ec.edu.utpl.ti.pintegrativa.microservices.biblioteca;

import javax.json.Json;
import javax.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ActivitiesRepository {
    final static Map<String, JsonObject> activities = new HashMap<>();
    /*
     * static List<String> dataBase;
     * 
     * static {
     * dataBase = List.of("ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STU", "VWX",
     * "YZ");
     * }
     * 
     * public boolean exists(String code) {
     * return dataBase.contains(code);
     * }
     * 
     * 
     * 
     * 
     * static List<JsonObject> activities;
     */
    static {

        JsonObject act1 = Json.createObjectBuilder()
                .add("materia", "Programación Integrativa")
                .add("titulo", "Foro 1")
                .add("calificacion", "10")
                .build();
        JsonObject act2 = Json.createObjectBuilder()
                .add("materia", "Programación Integrativa")
                .add("titulo", "Caso 1")
                .add("calificacion", "10")
                .build();
        JsonObject act3 = Json.createObjectBuilder()
                .add("materia", "Seguridad en TI")
                .add("titulo", "Chat 1")
                .add("calificacion", "10")
                .build();
        JsonObject act4 = Json.createObjectBuilder()
                .add("materia", "Estadística en TI")
                .add("titulo", "Caso 1")
                .add("calificacion", "10")
                .build();
        JsonObject act5 = Json.createObjectBuilder()
                .add("materia", "Sistemas distribuidos")
                .add("titulo", "Foro 1")
                .add("calificacion", "10")
                .build();
        JsonObject act6 = Json.createObjectBuilder()
                .add("materia", "Infraestructura de TI")
                .add("titulo", "Cuestionario 1")
                .add("calificacion", "10")
                .build();
        JsonObject act7 = Json.createObjectBuilder()
                .add("materia", "Sistemas distribuidos")
                .add("titulo", "Cuestionario 3")
                .add("calificacion", "10")
                .build();
        // activities = List.of(act1, act2, act3, act4, act5);

        activities.put(ActivitiesResourceService.generateID(), act1);
        activities.put(ActivitiesResourceService.generateID(), act2);
        activities.put(ActivitiesResourceService.generateID(), act3);
        activities.put(ActivitiesResourceService.generateID(), act4);
        activities.put(ActivitiesResourceService.generateID(), act5);
        activities.put(ActivitiesResourceService.generateID(), act6);
        activities.put("id0001", act7);
    }
    /*
     * public boolean existsInJson(String key) {
     * return activities.contains(key);
     * }
     */
}