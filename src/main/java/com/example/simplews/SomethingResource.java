package com.example.simplews;

import com.example.simplews.dao.SomethingModelService;
import com.example.simplews.exceptions.ModelNotFoundException;
import com.example.simplews.model.SomethingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.ws.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class SomethingResource {

    @Autowired
    private SomethingModelService modelService;

    @GetMapping(path = "/models")
    public List<SomethingModel> getAllModels() {
        return modelService.findAll();
    }

    @GetMapping(path = "/models/{id}")
    public Resource<SomethingModel> getSomethingModel(@PathVariable String id) {
        Optional<SomethingModel> model = modelService.findOne(id);
        if (!model.isPresent()) {
            throw new ModelNotFoundException(String.format("no model with id %s", id));
        }

        Resource<SomethingModel> resource = makeResponseHateoasCompatible(model.get());

        return resource;
    }

    private Resource<SomethingModel> makeResponseHateoasCompatible(SomethingModel model) {
        Resource<SomethingModel> resource = new Resource<SomethingModel>(model);
        ControllerLinkBuilder linkToAll = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass())
                .getAllModels());
        ControllerLinkBuilder linkToDelete = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this
                .getClass())
                .deleteSomethingModel(model.getId()));

        resource.add(linkToAll.withRel("all models"));
        resource.add(linkToDelete.withRel("delete model"));
        return resource;
    }

    @PostMapping(path = "/models")
    public ResponseEntity<Object> createSomethingModel(@Valid @RequestBody SomethingModel model) {
        SomethingModel newModel = modelService.save(model);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newModel.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/models/{id}")
    public ResponseEntity<Object> deleteSomethingModel(@PathVariable String id) {
        SomethingModel newModel = modelService.delete(id);
        if (newModel == null) {
            throw new ModelNotFoundException("no model with given idd");
        }
        return ResponseEntity.ok("Deleted successfully");
    }
}
