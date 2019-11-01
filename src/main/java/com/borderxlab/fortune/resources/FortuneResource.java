package com.borderxlab.fortune.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.borderxlab.fortune.core.Fortune;
import com.borderxlab.fortune.service.FortunePool;
import com.borderxlab.fortune.core.Error;;


@Path("/fortune")
@Produces(MediaType.APPLICATION_JSON)
public class FortuneResource {
    private static final String ERROR_NO_FORTUNE = "No any fortune";

    private static final Logger LOGGER = LoggerFactory.getLogger(FortuneResource.class);

    private FortunePool fortunePool;

    public FortuneResource(FortunePool fortunePool) {
        this.fortunePool = fortunePool;
    }

    /**
     * HTTP GET invoke by:
     * 
     * curl -XGET localhost:8080/fortune
     * 
     * @return a randomly selected fortune
     */
    @GET
    public Response getFortune() {
        LOGGER.info("getFortune\n");

        Fortune fortune = null;
        try {
            fortune = fortunePool.getRandomFortune();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Error(ERROR_NO_FORTUNE))
                    .build();
        }

        return Response.ok().entity(fortune).build();
    }
}
