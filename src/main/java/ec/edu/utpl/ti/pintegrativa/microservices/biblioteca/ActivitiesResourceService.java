package ec.edu.utpl.ti.pintegrativa.microservices.biblioteca;

import io.helidon.common.http.Http;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.UUID;

public class ActivitiesResourceService implements Service {
    private ActivitiesRepository repository;
    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    public ActivitiesResourceService(ActivitiesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void update(Routing.Rules rules) {
        rules
                // .get("/get/{code}", this::getHandler)
                .get("/get/{id}", this::getHandler)
                // .get("/list", this::listHandler)
                // .get("/list2", this::listHandler2)
                .get("/list", this::listHandler)
                .post("/create", this::createHandler)
                .put("/update/{id}", this::updateHandler)
                .delete("/delete/{id}", this::deleteHandler);

    }

    /*
     * private void getHandler(ServerRequest request, ServerResponse response) {
     * String code = request.path().param("code").toUpperCase();
     * boolean exists = repository.exists(code);
     * if (exists) {
     * JsonObject returnObject = JSON.createObjectBuilder()
     * .add("code", code)
     * .add("exist", exists)
     * .build();
     * response.send(returnObject);
     * } else {
     * response.status(Http.Status.NOT_FOUND_404).send();
     * System.out.println("No hay nada");
     * }
     * }
     */
    private synchronized void getHandler(ServerRequest request, ServerResponse response) {
        JsonObject user = ActivitiesRepository.activities.get(getID(request));

        if (user != null) {
            response.status(200).send(user);
        } else {
            response.status(404).send();
        }
    }

    /*
     * private void listHandler(ServerRequest request, ServerResponse response) {
     * JsonArrayBuilder builder = Json.createArrayBuilder();
     * repository.dataBase.forEach(builder::add);
     * ActivitiesRepository.dataBase.forEach(System.out::println);
     * // repository.dataBase.forEach( builder::add);
     * 
     * JsonObject result = Json.createObjectBuilder()
     * .add("items", builder.build())
     * .build();
     * 
     * response.status(200).send(result);
     * 
     * }
     * 
     * private synchronized void listHandler2(ServerRequest req, ServerResponse res)
     * {
     * JsonArrayBuilder builder = Json.createArrayBuilder();
     * ActivitiesRepository.activities.forEach(builder::add);
     * 
     * JsonObject result = Json.createObjectBuilder()
     * .add("items", builder.build())
     * .build();
     * 
     * res.status(200).send(result);
     * }
     */
    private synchronized void listHandler(ServerRequest request, ServerResponse response) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        ActivitiesRepository.activities.values().forEach(builder::add);

        JsonObject result = Json.createObjectBuilder()
                .add("items", builder.build())
                .build();

        response.status(200).send(result);
    }

    private void createHandler(ServerRequest request, ServerResponse response) {
        request.content()
                .as(JsonObject.class)
                .thenAccept(payload -> {
                    // That's right, no validation for this sample service.
                    String id = generateID();
                    JsonObject user = Json.createObjectBuilder(payload)
                            .add("id", id)
                            .build();

                    ActivitiesRepository.activities.put(id, user);
                    response.status(201).send(user);
                });
    }

    private void updateHandler(ServerRequest request, ServerResponse response) {
        request.content()
                .as(JsonObject.class)
                .thenAccept(payload -> {
                    String id = getID(request);
                    // Make sure the ID doesn't change.
                    JsonObject user = Json.createObjectBuilder(payload)
                            .add("id", id)
                            .build();

                    ActivitiesRepository.activities.put(id, user);
                    response.status(202).send(user);
                });
    }

    private void deleteHandler(ServerRequest request, ServerResponse response) {
        JsonObject user = ActivitiesRepository.activities.remove(getID(request));

        if (user != null) {
            response.status(202).send(user);
        } else {
            response.status(404).send();
        }
    }

    private static String getID(ServerRequest req) {
        return req.path().param("id");
    }

    static String generateID() {
        return UUID.randomUUID().toString();
    }
}
