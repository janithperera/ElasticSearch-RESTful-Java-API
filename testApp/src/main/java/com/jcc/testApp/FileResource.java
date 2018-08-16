package com.jcc.testApp;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.jcc.testApp.services.FileService;

@Path("file")
public class FileResource {
	
	private FileService service = new FileService();
	
    @POST
    @Path("upload/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void updateProfilePicture(
    		@FormDataParam("file") InputStream stream,
    		@FormDataParam("file") FormDataContentDisposition details,
    		@PathParam("id")String id) {
    	service.uploadImages(stream, details, id);
    }
    
    @GET
    @Path("download/{id}/{index}")
    @Produces("image/png")
    public Response getImage(@PathParam("id")String id, @PathParam("index")int index) {
    	return service.downloadImage(id, index);
    }
    
	
}
