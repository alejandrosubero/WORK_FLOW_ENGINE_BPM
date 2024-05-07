
/*
Create on Sun Sep 24 00:38:17 EDT 2023
*Copyright (C) 123.
@author Alejandro Subero
@author open
@author  
@since 1.8
@version1.0.0.0
@version  %I%, %G%
*<p>Description: Business Project Management engine  </p>
*/


package com.bpm.engine.controller;

import com.bpm.engine.componets.BpmAssignedManager;
import com.bpm.engine.dto.BpmAssignedDTO;
import com.bpm.engine.entitys.Assigned;
import com.bpm.engine.service.BpmAssignedService;
import com.bpm.engine.validation.AssignedValidation;
import com.bpm.engine.mapper.AssignedMapper;
import com.bpm.engine.service.AssignedService;
import com.bpm.engine.mapper.MapperEntityRespone;
import com.bpm.engine.dto.EntityRespone;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.bpm.engine.model.AssignedModel;
import com.bpm.engine.model.AssignedModel;
import com.bpm.engine.entitys.Role;
import com.bpm.engine.validation.RoleValidation;
import com.bpm.engine.mapper.RoleMapper;
import com.bpm.engine.model.RoleModel;
import com.bpm.engine.model.RoleModel;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/assigned")
public class AssignedController {

    @Autowired
    private AssignedService assignedService;

    @Autowired
   private AssignedValidation assignedValidationService;

    @Autowired
    private AssignedMapper assignedMapper;

    @Autowired
    private MapperEntityRespone mapperEntityRespone;

    @Autowired
    private RoleValidation roleValidationService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private BpmAssignedManager bpmAssignedManager;


    /***
     *
     * @param assignedBPM
     * @return value boolean before the save a new assigned approver
     */
    @PostMapping("bpm/saveOrUpdate")
    private boolean saveOrUpdateBpmAssigned(@RequestBody BpmAssignedDTO assignedBPM) {
        return  bpmAssignedManager.saveOrUpdateBpmAssigned(assignedBPM);
    }

    @PostMapping("/saveOrUpdate")
    private boolean saveOrUpdateAssigned(@RequestBody AssignedModel assigned) {
        return assignedService.saveOrUpdateAssigned(assignedMapper.pojoToEntity(assignedValidationService.valida(assigned)));
    }


    @GetMapping("/get/code/employee/{codeEmployee}")
    private ResponseEntity<EntityRespone> findByCodeEmployee(@PathVariable("codeEmployee") String codeEmployee) {
        try {
            String code = (String) assignedValidationService.validation(codeEmployee);
            if(code == null){
                EntityRespone entityRespone = mapperEntityRespone.setEntityResponT(null, "codeEmployee = null Error", "the variable is null");
                return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.BAD_REQUEST);
            }
            EntityRespone entityRespone = mapperEntityRespone.setEntityTobj(assignedService.findByCodeEmployee(code));
            return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.OK);
        } catch (DataAccessException e) {
            EntityRespone entityRespone = mapperEntityRespone.setEntityResponT(null, "Ocurrio un error", e.getMessage());
            return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/GetAssigned/{id}")
    private ResponseEntity<EntityRespone> findById(@PathVariable("id") String id) {
        EntityRespone entityRespone = mapperEntityRespone.setEntityTobj(assignedService.findById(assignedValidationService.valida_id(id)));
        return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.OK);
    }


    @GetMapping("/get/All/Assigned")
    private ResponseEntity<EntityRespone> getAllAssigned() {
        EntityRespone entityRespone = mapperEntityRespone.setEntityT(assignedService.getAllAssigned());
        return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.OK);
    }


    @PostMapping("/find/Assigned/Role")
    private ResponseEntity<EntityRespone> findRelacionRole(@RequestBody RoleModel role) {
        EntityRespone entityRespone = mapperEntityRespone.setEntityT(assignedService.findByRelacionRole(roleMapper.pojoToEntity(roleValidationService.valida(role))));
        return new ResponseEntity<EntityRespone>(entityRespone, HttpStatus.OK);
    }

}
 /*
 Copyright (C) 2008 Google Inc.
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


