/**
 * 
 */
package com.mneumann1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mneumann1.exception.ResourceNotFoundException;
import com.mneumann1.model.Tutorial;
import com.mneumann1.repository.TutorialRepository;

/**
 * @author MNEUMANN1
 * 
  	POST 		/api/tutorials				 	create new Tutorial
  	POST 		/api/tutorials/:id/comments 	create new Comment for a Tutorial
	GET 		/api/tutorials/:id/comments 	retrieve all Comments of a Tutorial
	GET 		/api/comments/:id 				retrieve a Comment by :id
	PUT 		/api/comments/:id 				update a Comment by :id
	DELETE 		/api/comments/:id 				delete a Comment by :id
	DELETE 		/api/tutorials/:id 				delete a Tutorial (and its Comments) by :id
	DELETE 		/api/tutorials/:id/comments 	delete all Comments of a Tutorial
 *
 */

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1")
public class TutorialController {

	@Autowired
	TutorialRepository tutorialRepository;
		
	
	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) { 
		
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		
		if (title == null)
			tutorialRepository.findAll().forEach(tutorials::add);
		else
			tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
		
		if (tutorials.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(tutorials, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
		
		Tutorial tutorial = tutorialRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Not found tutorial with id: " + id));
		
		return new ResponseEntity<>(tutorial, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
	
		Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), true)); 
		
		return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
		
		Tutorial _tutorial = tutorialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found tutorial with id: " + id));
		
		_tutorial.setTitle(tutorial.getTitle());
		_tutorial.setDescription(tutorial.getDescription());
		_tutorial.setPublished(tutorial.isPublished());
		
		return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		tutorialRepository.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@DeleteMapping("/tutorials")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		
		tutorialRepository.deleteAll();
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/tutorials/published")
	public ResponseEntity<List<Tutorial>> findByPublished() {
		
		List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
		
		if(tutorials.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}
	
	
	@GetMapping("/tutorials/search")
	public ResponseEntity<List<Tutorial>> findTutorialsByTitleContaining(@RequestParam("searchTerm") String searchTerm) {
		
		List<Tutorial> tutorials = tutorialRepository.findByTitleContaining(searchTerm);
		
		if(tutorials.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(tutorials, HttpStatus.OK);
		
	}
}
