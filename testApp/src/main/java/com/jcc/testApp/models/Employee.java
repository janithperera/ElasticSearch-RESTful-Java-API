package com.jcc.testApp.models;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private String id;
	private String name;
	private String address;
	private String nic;
	private String mobile;
	private String department;
	private String role;
	private List<String> pictures = new ArrayList<String>();
	
	public Employee() {}
	
	public Employee(String id, String name, String address, String nic, String mobile, String department, String role) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.nic = nic;
		this.mobile = mobile;
		this.department = department;
		this.role = role;
	}

	public Employee(String id, String name, String address, String nic, String mobile, String department, String role, List<String> pictures) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.nic = nic;
		this.mobile = mobile;
		this.department = department;
		this.role = role;
		this.pictures = pictures;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}

	public String toString() {
		String s =  
			   "ID         : " + this.id + "\n" +
			   "Name       : " + this.name + "\n" +
			   "Address    : " + this.address + "\n" +
			   "NIC        : " + this.nic + "\n" +
			   "Mobile     : " + this.mobile + "\n" +
			   "Department : " + this.department + "\n" +
			   "Role       : " + this.role + "\n";
		for (String l : this.pictures) {
			s = s + 
			   "Picture    : " + l + "\n";
		}
		return s;
	}
	
}
