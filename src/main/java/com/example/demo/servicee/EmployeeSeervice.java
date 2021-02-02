package com.example.demo.servicee;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Company;
import com.example.demo.model.Customer;
import com.example.demo.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public class EmployeeSeervice {

	public Flux<Employee> findAll() {

		List<Employee> emps = createMockEmployeeList();

		Mono<List<Employee>> aew = Mono.just(emps);

		Flux<Employee> daoFlux = aew.flatMapMany(Flux::fromIterable);

		return daoFlux;
	}

	public List<Employee> createMockEmployeeList() {
		int size = 80000;
		Employee emp = null;
		List<Employee> emps = new ArrayList<Employee>();
		for (int i = 0; i < size; i++) {
			emp = new Employee();

			emp.setId(i);
			emp.setName("Employee n: " + i);
			emp.setSalary(300l);
			emps.add(emp);
		}

		return emps;
	}

	public Mono<Company> getSingleCompany() {

		Company cp = new Company();
		cp.setId(1l);
		cp.setName("Empresa Teste");

		return Mono.just(cp);
	}

	public String findEmployeesCompany(Integer idCompany) {

		Company cp = new Company();
		cp.setId(1l);
		cp.setName("Empresa do Employee");

		return cp.getName();
	}

	public Mono<Employee> getSingleEmployee() {

		Employee emp = new Employee();

		emp.setId(1);
		emp.setName("Employee Cool");
		emp.setSalary(300l);

		return Mono.just(emp);
	}

	public Mono<Customer> createCustomer(String companyName, String employeeName) {

		Customer customer = new Customer(companyName, employeeName);

		return Mono.just(customer);

	}

	public Mono<Customer> createZipCustomer() {

		EmployeeSeervice service = new EmployeeSeervice();
		Mono<Tuple2<Company, Employee>> conjunto = Mono.zip(service.getSingleCompany(), service.getSingleEmployee());

		Mono<Customer> customer = conjunto.map(tuple -> new Customer(tuple.getT1().getName(), tuple.getT2().getName()));

		return customer;
	}
}
