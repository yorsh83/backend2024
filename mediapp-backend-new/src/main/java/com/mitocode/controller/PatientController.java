package com.mitocode.controller;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.model.Patient;
import com.mitocode.service.IPatientService;

import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

	// @Autowired
	private final IPatientService service; // = new PatientService();

//	public PatientController(IPatientService service) {
//		this.service = service;
//	}

	@GetMapping
	public ResponseEntity<List<Patient>> findAll() {
		List<Patient> list = service.findAll();
		return new ResponseEntity<List<Patient>>(list, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patient> findById(@PathVariable("id") Integer id) {
		Patient p = service.findById(id);
		return new ResponseEntity<Patient>(p, HttpStatus.OK);
	}

//	@PostMapping
//	public ResponseEntity<Patient> save(@RequestBody Patient patient) {
//		Patient p = service.save(patient);
//		return new ResponseEntity<Patient>(p, HttpStatus.CREATED);
//	}

	@PostMapping
	public ResponseEntity<Patient> save(@RequestBody Patient patient) {
		Patient p = service.save(patient);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdPatient())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Patient> update(@PathVariable("id") Integer id, @RequestBody Patient patient) {
		patient.setIdPatient(id);
		Patient p = service.save(patient);
		return new ResponseEntity<Patient>(p, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		service.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<Patient> findByIdHateoas(@PathVariable("id") Integer id) {
		EntityModel<Patient> resource = EntityModel.of(service.findById(id));
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
		resource.add(link1.withRel("patient-info"));
		return resource;
	}

}
