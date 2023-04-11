/**
 * 
 */
package com.mneumann1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mneumann1.model.Comment;

import jakarta.transaction.Transactional;

/**
 * @author MNEUMANN1
 *
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByTutorialId(long tutorialId);
	
	@Transactional
	void deleteByTutorialId(long tutorialId);

}
