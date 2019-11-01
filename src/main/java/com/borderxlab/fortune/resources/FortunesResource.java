package com.borderxlab.fortune.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.borderxlab.fortune.core.Fortune;
import com.borderxlab.fortune.service.FortunePool;;

@Path("/fortunes")
@Produces(MediaType.APPLICATION_JSON)
public class FortunesResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(FortunesResource.class);

    private FortunePool fortunePool;

    public FortunesResource(FortunePool fortunePool) {
        this.fortunePool = fortunePool;
    }

    /**
     * HTTP GET invoke by: curl -XGET localhost:8080/hello-world?name=aaa
     * 
     * curl -XGET localhost:8080/hello-world
     *
     * @param name Note that the URL parameter `name` you see above gets parsed to the `name` method
     *             parameter below.
     * @return
     */
    @GET
    public Response listAllFortunes() {
        LOGGER.info("listAllFortunes\n");
        return Response.ok().entity(fortunePool.getAllFortunes()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFrotune(@Valid Fortune fortune) {
        LOGGER.info("createFrotune: {}\n", fortune);

        if (fortune.getContent().isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        Fortune f = fortunePool.insertFortune(fortune.getContent());
        return Response.ok().entity(f).build();
    }

    @Path("/{fortuneId}")
    @DELETE
    public Response deleteFortune(@PathParam("fortuneId") Optional<Integer> fortuneId) {
        LOGGER.info("deleteFortune: id: {}\n", fortuneId);

        if (!fortuneId.isPresent())
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {
            fortunePool.removeFortune(fortuneId.get());
        } catch (Exception e) {
            if (e instanceof NoSuchElementException)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.serverError().build();
        }

        return Response.ok().build();
    }
}
