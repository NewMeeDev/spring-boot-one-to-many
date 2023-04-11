/**
 * 
 */
package com.mneumann1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mneumann1.model.Tutorial;

/**
 * @author MNEUMANN1
 *
 */
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

	List<Tutorial> findByPublished(boolean published);
	
	List<Tutorial> findByTitleContaining(String title);
	
}
