package ec.edu.utpl.ti.pintegrativa.microservices.biblioteca;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonObject;
import java.util.UUID;

public class ActivitiesResourceService implements Service {
    public ActivitiesRepository repository;

    public ActivitiesResourceService(ActivitiesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void update(Routing.Rules rules) {
        rules
                .get("/get/{id}", this::getHandler)
                .get("/list", this::listHandler)
                .post("/create", this::createHandler)
                .put("/update/{id}", this::updateHandler)
                .delete("/delete/{id}", this::deleteHandler);
    }

    private synchronized void getHandler(ServerRequest request, ServerResponse response) {
        // Retorna el elemento del repositorio con el ID especificado
        JsonObject activity = repository.activities.get(getID(request));

        if (activity != null) {
            response.status(200).send(activity);
        } else {
            response.status(404).send();
        }
    }

    private synchronized void listHandler(ServerRequest request, ServerResponse response) {
        // Retorna todos los elementos de repositorio
        JsonArrayBuilder builder = Json.createArrayBuilder();
        repository.activities.values().forEach(builder::add);

        JsonObject result = Json.createObjectBuilder()
                .add("items", builder.build())
                .build();

        response.status(200).send(result);
    }

    private void createHandler(ServerRequest request, ServerResponse response) {
        // Crea un nuevo elemento en el repositorio
        request.content()
                .as(JsonObject.class)
                .thenAccept(payload -> {
                    // That's right, no validation for this sample service.
                    boolean valid = validateActivity(payload);
                    if (valid) {
                        String id = generateID();
                        JsonObject activity = Json.createObjectBuilder(payload)
                                .add("id", id)
                                .build();

                        repository.activities.put(id, activity);
                        response.status(201).send(activity);
                    } else
                        response.status(422).send();
                });
    }

    private void updateHandler(ServerRequest request, ServerResponse response) {
        // Actualiza un elemento en el repositorio con el ID especificado
        request.content()
                .as(JsonObject.class)
                .thenAccept(payload -> {
                    boolean valid = validateActivity(payload);
                    if (valid) {
                        String id = getID(request);
                        JsonObject activity = Json.createObjectBuilder(payload)
                                .add("id", id)
                                .build();

                        repository.activities.put(id, activity);
                        response.status(202).send(activity);
                    } else
                        response.status(422).send();

                });
    }

    private void deleteHandler(ServerRequest request, ServerResponse response) {
        // Elimina un elemento del repositorio con el ID especificado
        JsonObject activity = repository.activities.remove(getID(request));

        if (activity != null) {
            response.status(202).send(activity);
        } else {
            response.status(404).send();
        }
    }

    private static String getID(ServerRequest request) {
        // Obtiene el parámetro ID del ServerRequest
        return request.path().param("id");
    }

    static String generateID() {
        // Genera una ID alfanumérica aleatoria
        return UUID.randomUUID().toString();
    }

    private boolean validateActivity(JsonObject activity) {
        // Valida los datos ingresados
        try {
            if (activity.getString("materia") == "" || activity.getString("titulo") == ""
                    || (activity.getInt("calificacion")) < 0
                    || (activity.getInt("calificacion")) > 10) {
                return false;
            } else
                return true;

        } catch (JsonException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
