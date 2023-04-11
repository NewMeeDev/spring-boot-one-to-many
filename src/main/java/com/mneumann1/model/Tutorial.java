/**
 * 
 */
package com.mneumann1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author MNEUMANN1
 *
 */
@Entity
@Table(name="tutorials")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tutorial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tutorial_generator")
	private long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "published")
	private boolean published;

}
