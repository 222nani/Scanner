package com.yourpackage.resources;

import com.yourpackage.model.Search;
import com.yourpackage.model.SearchResult;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final List<SearchResult> searchResults;

    public SearchResource(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @POST
    public Response search(Search search) {
        if (search == null || search.getCity() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("City must be provided").build();
        }

        String cityToSearch = search.getCity().toLowerCase();

        List<SearchResult> results = searchResults.stream()
                .filter(result -> result.getCity().toLowerCase().equals(cityToSearch))
                .collect(Collectors.toList());

        return Response.ok(results).build();
    }
}
