package com.js.employee.controller;
import com.google.gson.Gson;
import com.js.employee.componet.EmployeeManager;
import com.js.employee.mapper.EmpleadoMapper;
import com.js.employee.mapper.MapperEntityRespone;
import com.js.employee.model.AssignedModel;
import com.js.employee.pojo.EntityRespone;
import com.js.employee.service.EmpleadoService;
import com.js.employee.validation.EmpleadoValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/employee/connect/")
public class EmployeeController {


	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private EmpleadoValidation empleadoValidationService;

	@Autowired
	private EmpleadoMapper empleadoMapper;

	@Autowired
	private MapperEntityRespone mapperEntityRespone;

	@Autowired
	private EmployeeManager employeeManager;


	//http://localhost:1112/wfe/employee/user/name/{userName}
	@GetMapping("user/name/{userName}")
	private ResponseEntity<EntityRespone> findEmployeeByUserName (@PathVariable("userName") String userName) {
		try {
			AssignedModel assigned = employeeManager.findEmployeeByUserName(userName);
			Gson gson = new Gson();
			String value = gson.toJson(assigned);
			String menssage = assigned != null ? "Ok" : "The list was not Found";
			return new ResponseEntity<EntityRespone>(mapperEntityRespone.setEntityRespon(value, menssage), HttpStatus.OK);
		} catch (DataAccessException e) {
			EntityRespone entityRespone = mapperEntityRespone.setEntityResponT(null, "Fail error", e.getMessage());
			return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.BAD_REQUEST);
		}
	}

	//http://localhost:1112/wfe/employee/approve/{employeeNumber}/{positionCode}
	@GetMapping("/approve/{employeeNumber}/{positionCode}")
	private ResponseEntity<EntityRespone> findApprover(@PathVariable("employeeNumber") String employeeNumber, @PathVariable("positionCode") String positionCode) {
		try {
			AssignedModel assigned = employeeManager.findApprover(employeeNumber, positionCode);
			String menssage = assigned != null ? "Ok" : "The Approver was not Found";
			EntityRespone entityRespone = mapperEntityRespone.setEntityRespon(assigned, menssage);
			return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.OK);
		} catch (DataAccessException e) {
			EntityRespone entityRespone = mapperEntityRespone.setEntityResponT(null, "Ocurrio un error", e.getMessage());
			return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.BAD_REQUEST);
		}
	}


	//http://localhost:1112/wfe/employee/code/number/{employeeNumber}
	@GetMapping("/code/number/{employeeNumber}")
	private ResponseEntity<EntityRespone> findByEmployeeNumber(@PathVariable("employeeNumber") String numeroempleado) {
		String codeEmployee = (String) empleadoValidationService.validation(numeroempleado);
		try {
			AssignedModel assigned = employeeManager.findEmployee(codeEmployee);
			EntityRespone entityRespone = null;
			if (assigned != null){entityRespone = mapperEntityRespone.setEntityTobj(assigned);}
			return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.OK);
		} catch (DataAccessException e) {
			EntityRespone entityRespone = mapperEntityRespone.setEntityResponT(null, "Faill in the system (Error tray to find the employee)", e.getMessage());
			return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/start")
	public String startTest() {
		return " <h1>!!!!!!!!!!!!!!!!!Hello Mundo!!!!!!!!!!!!</h1>"+ 
		"<br>" + 

		"<h2> !!!!!!!!!!!Estoy funcionando!!!!!!!!! </h2>"; 
	}
}
