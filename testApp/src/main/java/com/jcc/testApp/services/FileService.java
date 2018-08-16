package com.jcc.testApp.services;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.List;

import javax.ws.rs.core.Response.ResponseBuilder;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.jcc.testApp.models.Employee;
import com.jcc.testApp.models.Response;

public class FileService {
	TransportClient client = null; 
	
	@SuppressWarnings("resource")
	public FileService() {
		try {
			client = new PreBuiltTransportClient(Settings.EMPTY)
			        .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void uploadImages(InputStream stream, FormDataContentDisposition details, String id) {
		try {
			Employee emp = new EmployeeService().getEmployee(id);
			System.out.println(emp);
			Response res = saveProfilePicture(stream, details, emp);
			List<String> images = emp.getPictures();
			if (res.isStatus()) {
				System.out.println("Res message : " + res.getMessage());
				images.add(res.getMessage());
				emp.setPictures(images);
				updateProfilePicture(emp);
			}
		}catch(Exception e) {
			System.out.println("Error: uploadImages(): " + e);
		}
	}
	
	private Response saveProfilePicture(InputStream stream, FormDataContentDisposition details, Employee emp) {
		try {
			String location = "D://" + emp.getId() + details.getFileName();
			System.out.println(location);
			OutputStream out = new FileOutputStream(new File(location));
			int read = 0;
			byte[] bytes = new byte[1024]; 
			out = new FileOutputStream(new File(location));
			while((read = stream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			return new Response(true, location);
		}catch(Exception ex) {
			return new Response(false, "Failed");
		}
	}
	
	private boolean updateProfilePicture(Employee emp) {
		try {
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.index("employees");
			updateRequest.type("employee");
			updateRequest.id(emp.getId());
			updateRequest.doc(jsonBuilder()
			        .startObject()
			        .field("pictures", emp.getPictures())
			        .endObject());
			client.update(updateRequest).get();
			System.out.println("Image uploaded successfully!");
			return true;
		} catch(Exception e) {
			System.out.println("Error: updateProfilePicture(): " + e);
			return false;
		}
	}
	
	public javax.ws.rs.core.Response downloadImage(String id, int index){
		Employee emp = new EmployeeService().getEmployee(id);
		System.out.println(emp.getPictures().size() + " " + index);
		
		if (emp.getPictures().size() > index) {
			File file = new File(emp.getPictures().get(index));
    		ResponseBuilder response = javax.ws.rs.core.Response.ok((Object) file);
    		response.header("Content-Disposition", "attachment; filename="+id+".jpg");
    		return response.build();
    	}else
    		return null;
	}
}
