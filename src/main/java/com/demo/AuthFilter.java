package com.demo;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;


/**
 * Jersey HTTP Basic Auth filter
 * @author Deisss (LGPLv3)
 */
public class AuthFilter implements ContainerRequestFilter {
    /**
     * Apply the filter : check input request, validate or not with user auth
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) throws WebApplicationException {
        //GET, POST, PUT, DELETE, ...
        String method = containerRequest.getMethod();
        // myresource/get/56bCA for example
        String path = containerRequest.getPath(true);
 
        //We do allow wadl to be retrieve
        if(method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))){
            return containerRequest;
        }
 
        //Get the authentification passed in HTTP headers parameters
        String auth = containerRequest.getHeaderValue("authorization");
 
        //If the user does not have the right (does not provide any HTTP Basic Auth)
        if(auth == null){
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
 
        //lap : loginAndPassword
        String[] lap = BasicAuth.decode(auth);
 
        //If login or password fail
        if(lap == null || lap.length != 2){
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
 
        //DO YOUR DATABASE CHECK HERE (replace that line below)...        
        boolean authentication = false;
        
        if("user".equals(lap[0]) && "password".equals(lap[1]) )  {
        	authentication = true;
        }
        
 
        //Our system refuse login and password
        if(!authentication){
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
 
        //TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER USER ON YOUR REST SERVICE...
 
        return containerRequest;
    }
}
