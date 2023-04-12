/**
 * 
 */
package com.mneumann1.controller;

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
import com.mneumann1.model.Comment;
import com.mneumann1.repository.CommentRepository;
import com.mneumann1.repository.TutorialRepository;

/**
 * @author MNEUMANN1
 * 
 * 	POST 		/api/tutorials/:id/comments 	create new Comment for a Tutorial
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
public class CommentController {
	
	@Autowired
	TutorialRepository tutorialRepository;
	
	@Autowired
	CommentRepository commentRepository;

	
	@GetMapping("tutorials/{tutorialId}/comments")
	public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId) {
		
		if(!tutorialRepository.existsById(tutorialId)) {
			
			throw new ResourceNotFoundException("Not found Tutorial with id: " + tutorialId);
		}
			
		List<Comment> comments = commentRepository.findByTutorialId(tutorialId);
		return new ResponseEntity<>(comments, HttpStatus.OK);	
		
	}
	
	
	@GetMapping("/comments/{id}")
	public ResponseEntity<Comment> getCommentById(@PathVariable("id") long id) {
		
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id: " + id));
		
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}
	
	
	@PostMapping("/tutorials/{tutorialId}/comments")
	public ResponseEntity<Comment> createComment(@PathVariable(value = "tutorialId") long tutorialId, @RequestBody Comment commentRequest) {
		
		Comment comment = tutorialRepository.findById(tutorialId)
				.map(tutorial -> { 
					commentRequest.setTutorial(tutorial);
					return commentRepository.save(commentRequest); 
				}).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id: " + tutorialId));
			
		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/comments/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {
		
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id: " + id));
		
		comment.setContent(commentRequest.getContent());
		return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/comments/{id}")
	public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
		commentRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@DeleteMapping("/tutorials/{tutorialId}/comments")
	public ResponseEntity<HttpStatus> deleteAllCommentsOfTutorial(@PathVariable(value = "tutorialId") long tutorialId) {
		
		if (!tutorialRepository.existsById(tutorialId)) {
			throw new ResourceNotFoundException("Not found Tutorial with id: " + tutorialId);
		}
		
		commentRepository.deleteByTutorialId(tutorialId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
