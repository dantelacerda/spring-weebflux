package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Customer;
import com.example.demo.model.Employee;
import com.example.demo.servicee.EmployeeSeervice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/webflux")
public class WebluxController {

	@GetMapping("/mono-just")
	public Mono<String> monoJust() {

		return Mono.just("Mono just");

	}

	@RequestMapping(value = "flux", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public Flux<Employee> fluxStreams() {
		EmployeeSeervice service = new EmployeeSeervice();
		return service.findAll();
	}

	@RequestMapping(value = "zip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Mono<Object> zip() {
		EmployeeSeervice service = new EmployeeSeervice();
		return Mono.zip(service.getSingleCompany(), service.getSingleEmployee())
				.map(tuple -> service.createCustomer(tuple.getT1().getName(), tuple.getT2().getName()));
	}

	@RequestMapping(value = "zip-tupple", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Mono<Customer> zipTupple2() {

		EmployeeSeervice service = new EmployeeSeervice();

		Mono<Customer> customer = service.createZipCustomer();
		return customer;
	}

	@RequestMapping(value = "zip-when", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Mono<Customer> zipWhen() {

		EmployeeSeervice service = new EmployeeSeervice();

		Mono<Employee> first = service.getSingleEmployee();
		// Quando precisamos buscar primeiro o funcionário para depois buscar a empresa
		Mono<Tuple2<Employee, Object>> result = first.zipWhen(employee -> Mono
				.just(new Customer(service.findEmployeesCompany(employee.getId()), employee.getName())));

		// Aqui poderíamos tratar os tuples do resultado acima e manipular como
		// quiséssemos,
		// Mas só vou mostrar o valor alterado, fiz um Cast direto =)
		Mono<Customer> customer = result.map(tuple -> (Customer) tuple.getT2());
		return customer;
	}

}
