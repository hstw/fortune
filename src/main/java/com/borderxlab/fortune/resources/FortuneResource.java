package com.borderxlab.fortune.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/fortune")
@Produces(MediaType.APPLICATION_JSON)
public class FortuneResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(FortuneResource.class);

    private FortunePool fortunePool;

    public FortuneResource() {
        fortunePool = FortunePool.getInstance();
    }

    /**
     * HTTP GET invoke by:
     * 
     * curl -XGET localhost:8080/hello-world?name=aaa
     * 
     * curl -XGET localhost:8080/hello-world
     *
     * @param name Note that the URL parameter `name` you see above gets parsed to the `name` method
     *             parameter below.
     * @return
     */
    @GET
    public Response getFortune() {
        LOGGER.info("getFortune\n");

        Fortune f = null;
        try {
            f = fortunePool.getRandomFortune();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(f).build();
    }
}
