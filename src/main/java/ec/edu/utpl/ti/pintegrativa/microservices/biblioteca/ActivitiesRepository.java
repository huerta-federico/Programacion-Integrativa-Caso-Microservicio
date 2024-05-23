package ec.edu.utpl.ti.pintegrativa.microservices.biblioteca;

import javax.json.Json;
import javax.json.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitiesRepository {
        // HashMap de las actividades calificadas
        final Map<String, JsonObject> activities = new HashMap<>();
        {
                addTestData();
        }

        public void addTestData() {
                // Genera datos iniciales de prueba
                final List<JsonObject> testData;

                {
                        JsonObject act1 = Json.createObjectBuilder()
                                        .add("materia", "Programación Integrativa")
                                        .add("titulo", "Foro 1")
                                        .add("calificacion", 10)
                                        .build();
                        JsonObject act2 = Json.createObjectBuilder()
                                        .add("materia", "Programación Integrativa")
                                        .add("titulo", "Caso 1")
                                        .add("calificacion", 10)
                                        .build();
                        JsonObject act3 = Json.createObjectBuilder()
                                        .add("materia", "Seguridad en TI")
                                        .add("titulo", "Chat 1")
                                        .add("calificacion", 10)
                                        .build();
                        JsonObject act4 = Json.createObjectBuilder()
                                        .add("materia", "Estadística en TI")
                                        .add("titulo", "Caso 1")
                                        .add("calificacion", 10)
                                        .build();
                        JsonObject act5 = Json.createObjectBuilder()
                                        .add("materia", "Sistemas distribuidos")
                                        .add("titulo", "Foro 1")
                                        .add("calificacion", 10)
                                        .build();
                        JsonObject act6 = Json.createObjectBuilder()
                                        .add("materia", "Infraestructura de TI")
                                        .add("titulo", "Cuestionario 1")
                                        .add("calificacion", 10)
                                        .build();
                        JsonObject act7 = Json.createObjectBuilder()
                                        .add("materia", "Sistemas distribuidos")
                                        .add("titulo", "Cuestionario 3")
                                        .add("calificacion", 10)
                                        .build();

                        testData = List.of(act1, act2, act3, act4, act5, act6, act7);

                }

                

                // Ingresa los datos de prueba al HashMap de actividades, generando IDs únicas para cada una
                for (int i = 0; i < testData.size(); i++) {
                        String id = ActivitiesResourceService.generateID();
                        JsonObject activity = Json.createObjectBuilder(testData.get(i))
                                        .add("id", id)
                                        .build();

                        activities.put(id, activity);
                }
        }

}