package com.example.lab2;

import com.example.lab2.Utils.ResponseWrapper;
import com.example.lab2.beans.City;
import com.example.lab2.beans.Coordinates;
import com.example.lab2.beans.ResponseMessageDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;


@Path("/calculate")
public class CalculateController {

    @GET
    @Path("/")
    public Response hello() {
        return Response.ok().entity("hello").build();
    }
    
    @GET
    @Path("/between-largest-and-smallest")
    @Produces({MediaType.APPLICATION_JSON})
    public Response betweenLargesAndSmallest() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://localhost:9443/spring/city/area/max");
        ResponseMessageDTO jsonMax = target.request().get().readEntity(ResponseMessageDTO.class);
        Coordinates coordMax = jsonMax.getAnswer().getCoordinates();

        WebTarget target2 = client.target("https://localhost:9443/spring/city/area/min");
        ResponseMessageDTO jsonMin = target2.request().get().readEntity(ResponseMessageDTO.class);
        Coordinates coordMin = jsonMin.getAnswer().getCoordinates();

        double res = Math.sqrt(Math.pow((coordMax.getX() - coordMin.getX()), 2) + Math.pow((coordMax.getY() - coordMin.getY()), 2));

        JSONObject jsonRes = new JSONObject();
        jsonRes.put("result", res);

        return Response.ok().entity(jsonRes).build();
    }

    @GET
    @Path("/to-max-populated")
    @Produces({MediaType.APPLICATION_JSON})
    public Response toMaxPopulation() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://localhost:9443/spring/city/population/max");
        ResponseMessageDTO jsonMax = target.request().get().readEntity(ResponseMessageDTO.class);
        Coordinates coordMax = jsonMax.getAnswer().getCoordinates();
        int z = jsonMax.getAnswer().getArea() == null ? 0 : jsonMax.getAnswer().getArea();
        double res = Math.sqrt(Math.pow(coordMax.getX(), 2) + Math.pow(coordMax.getY(), 2) + Math.pow(z, 2));

        JSONObject jsonRes = new JSONObject();
        jsonRes.put("result", res);

        return Response.ok(jsonRes, MediaType.APPLICATION_JSON).build();
    }
}