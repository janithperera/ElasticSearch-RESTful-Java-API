package com.jcc.testApp.services;

import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.jcc.testApp.models.Employee;

public class EmployeeService {
	TransportClient client = null; 
	
	@SuppressWarnings("resource")
	public EmployeeService() {
		try {
			client = new PreBuiltTransportClient(Settings.EMPTY)
			        .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public  void addEmployee(Employee emp) {
		try {
			client.prepareIndex("employees","employee")
					.setSource(jsonBuilder()
							.startObject()
							.field("name",emp.getName())
							.field("address", emp.getAddress())
							.field("nic",emp.getNic())
							.field("mobile",emp.getMobile())
							.field("department", emp.getDepartment())
							.field("role", emp.getRole())
							.field("pictures", emp.getPictures())
							.endObject()
							)
					.get();
		}catch(Exception e) {
			System.out.println("Error: addEmployee(): " + e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Employee getEmployee(String id) {
		Employee s = new Employee();
		try {
			GetResponse response = client.prepareGet("employees", "employee", id).get();
			s.setId (response.getId().toString());
			s.setName(response.getSource().get("name").toString());
			s.setAddress(response.getSource().get("address").toString());
			s.setNic(response.getSource().get("nic").toString());
			s.setMobile(response.getSource().get("mobile").toString());
			s.setDepartment(response.getSource().get("department").toString());
			s.setRole(response.getSource().get("role").toString());
			s.setPictures((List<String>)response.getSource().get("pictures"));
		} catch(Exception e) {
			System.out.println("Error: getEmployee("+id+"): " + e);
		}
		return s;
	}
	
	@SuppressWarnings("unchecked")
	public List<Employee> getAllEmployees() {
		List<Employee> emps = new ArrayList<>();
		try {
			SearchResponse response = client.prepareSearch("employees").setTypes("employee")
					.setQuery(QueryBuilders.matchAllQuery()).get();

			for (SearchHit hit : response.getHits()) {				
				emps.add(new Employee((String) hit.getId(),
									  (String) hit.getSourceAsMap().get("name"), 
									  (String) hit.getSourceAsMap().get("address"), 
									  (String) hit.getSourceAsMap().get("nic"), 
									  (String) hit.getSourceAsMap().get("mobile"), 
									  (String) hit.getSourceAsMap().get("department"), 
									  (String) hit.getSourceAsMap().get("role"),
									  (List<String>) hit.getSourceAsMap().get("pictures")
									  ));
			}
		} catch (Exception e) {
			System.out.println("Error: getAllEmployees(): " + e);
		}
		for(Employee r: emps) {
			System.out.println(r);
		}
		return emps;
	}
	
	public List<Employee> searchEmployee(String keyword) {
		try {
			List<Employee> emps = new ArrayList<>();
			SearchResponse response = client.prepareSearch("employees").setTypes("employee")
					.setQuery(QueryBuilders.matchAllQuery()).get();

			for (SearchHit hit : response.getHits()) {				
				String id = hit.getId();
				String name = hit.getSourceAsMap().get("name").toString();
				String address = hit.getSourceAsMap().get("address").toString();
				String nic = hit.getSourceAsMap().get("nic").toString();
				String mobile = hit.getSourceAsMap().get("mobile").toString();
				String department = hit.getSourceAsMap().get("department").toString();
				String role = hit.getSourceAsMap().get("role").toString();
				@SuppressWarnings("unchecked")
				List<String> pictures = (List<String>) hit.getSourceAsMap().get("pictures");
				if (name.toLowerCase().contains(keyword.toLowerCase()))
					emps.add(new Employee(id, name, address, nic, mobile, department, role, pictures));
			}
			return emps;
		} catch(Exception e) {
			System.out.println("Error: searchEmployee("+keyword+"): " + e);
			return new ArrayList<Employee>();
		}
	}
	
	public void updateEmployee(Employee emp, String id) {
		try {
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.index("employees");
			updateRequest.type("employee");
			updateRequest.id(id);
			updateRequest.doc(jsonBuilder()
			        .startObject()
			        .field("name",emp.getName())
					.field("address", emp.getAddress())
					.field("nic",emp.getNic())
					.field("mobile",emp.getMobile())
					.field("department", emp.getDepartment())
					.field("role", emp.getRole())
			        .endObject());
			client.update(updateRequest).get();
		} catch(Exception e) {
			System.out.println("Error: updateEmployee("+id+"): " + e);
		}
	}
	
	public void deleteEmployee(String id) {
		try {
			client.prepareDelete("employees", "employee", id).get();
		} catch(Exception e) {
			System.out.println("Error: deleteEmployee("+id+"): " + e);
		}
	}
	
}
