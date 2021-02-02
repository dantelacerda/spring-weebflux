package com.example.demo.model;

public class Customer {

	public String companyName;
	public String employeeName;

	public Customer(String companyName, String employeeName) {
		this.companyName = companyName;
		this.employeeName = employeeName;
	}
	public Customer(String name, Company t2) {
		this.employeeName = name;
		this.companyName = t2.getName();
	}
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	@Override
	public String toString() {
		return "Customer [companyName=" + companyName + ", employeeName=" + employeeName + "]";
	}
}
