package com.example.simplews;

import com.example.simplews.dao.SomeRepository;
import com.example.simplews.dao.SomethingModelDaoService;
import com.example.simplews.dao.SomethingModelRepository;
import com.example.simplews.exceptions.ModelNotFoundException;
import com.example.simplews.model.Some;
import com.example.simplews.model.SomethingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class SomethingResource {

    @Autowired
    private SomethingModelRepository modelService;
    @Autowired
    private SomeRepository someService;


    @GetMapping(path = "/models")
    public List<SomethingModel> getAllModels() {
        return modelService.findAll();
    }

    @GetMapping(path = "/models/{id}")
    public Resource<SomethingModel> getSomethingModel(@PathVariable String id) {
        Optional<SomethingModel> model = modelService.findById(id);
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
        modelService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping(path = "/models/{id}/somes")
    public List<Some> getSomethingModelSomes(@PathVariable String id) {
        Optional<SomethingModel> model = modelService.findById(id);
        if (!model.isPresent()) {
            throw new ModelNotFoundException(String.format("no model with id %s", id));
        }

        return model.get().getSomeList();
    }

    @PostMapping(path = "/models/{id}/somes")
    public ResponseEntity<Object> createSomethingModelSomes(@PathVariable String id, @RequestBody Some some) {
        Optional<SomethingModel> model = modelService.findById(id);
        if (!model.isPresent()) {
            throw new ModelNotFoundException(String.format("no model with id %s", id));
        }
        SomethingModel somethingModel = model.get();

        some.setModel(somethingModel);

        someService.save(some);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(some.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
