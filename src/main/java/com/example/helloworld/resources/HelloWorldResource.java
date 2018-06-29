package com.example.helloworld.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world") // this is where the http path is set
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldResource.class);

    private final AtomicLong counter;

    public HelloWorldResource() {
        this.counter = new AtomicLong();
    }

    /**
     * HTTP GET
     * invoke by:
     * curl  localhost:8080/hello-world?name=aaa
     * curl  localhost:8080/hello-world
     *
     * @param name
     * @return
     */
    @GET
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return new Saying(counter.incrementAndGet(), name.isPresent() ? "Hello: " + name.get() : "Hello: Stranger");
    }

    /**
     * HTTP POST
     * invoke by:
     * curl -XPOST localhost:8080/hello-world -H "Content-Type: application/json"   -H "Accept: application/json" -d '{"id":333}'
     *
     * @param saying
     */
    @POST
    public void receiveHello(@Valid Saying saying) {
        LOGGER.info("Received a saying: {}\n\n", saying);
    }

    /**
     * HTTP DELETE
     * curl -XDELETE  'localhost:8080/hello-world?id=1234'
     *
     * @param id
     */
    @DELETE
    public void deleteIt(@QueryParam("id") Optional<String> id) {
        if (id.isPresent()) {
            LOGGER.info("delete object with id:=" + id.get());
        } else {
            LOGGER.info("delete. id not supplied");

        }
    }
}
