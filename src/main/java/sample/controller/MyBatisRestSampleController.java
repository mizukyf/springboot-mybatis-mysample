package sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import sample.entity.ActorEntity;
import sample.service.MyBatisSampleService;

@RestController
@RequestMapping("/mybatis-rest")
public class MyBatisRestSampleController {

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Specified resource is not found.")
	public static class NotFoundException extends RuntimeException {
		private static final long serialVersionUID = 6241322176053451224L;
		public NotFoundException() {
			super();
		}
		public NotFoundException(String message) {
			super(message);
		}
	}
	
	@Autowired
	private MyBatisSampleService sampleService;
	
    @RequestMapping("/actor/{id}")
    public ActorEntity actor(@PathVariable int id) {
    	return checkThenReturn(sampleService.getActorById(id));
    }
	
    @RequestMapping("/actors")
    public List<ActorEntity> actors() {
    	return sampleService.getActorsList();
    }
    
    @RequestMapping(value = "/to-upper-case", method = RequestMethod.POST)
    public ActorEntity toUpperCase(
    		@RequestBody ActorEntity actor,
    		BindingResult result,
    		Model model) {

    	if (actor.getFirstName() == null || actor.getFirstName().isEmpty() 
    			|| actor.getLastName() == null || actor.getLastName().isEmpty()) {
    		throw new IllegalArgumentException("First name and last name must be not null (and not empty).");
    	}

    	if (result.hasErrors()) {
    		throw new IllegalArgumentException();
    	}
    	
    	final ActorEntity upperCased = new ActorEntity();
    	upperCased.setActorId(actor.getActorId());
    	upperCased.setFirstName(actor.getFirstName().toUpperCase());
    	upperCased.setLastName(actor.getLastName().toUpperCase());
    	upperCased.setLastUpdate(actor.getLastUpdate());
    	return upperCased;
    }
    
    private<T> T checkThenReturn(T data) {
    	if (data == null) {
    		throw new NotFoundException();
    	}
    	return data;
    }
}
