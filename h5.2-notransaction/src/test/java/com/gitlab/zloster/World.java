package com.gitlab.zloster;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "world")
public class World {

	@Id
	private int id;

	@Column(name = "randomNumber", nullable = false)
	private int randomNumber;

	public World() {
	}

	public World(int id, int randomNumber) {
		this.id = id;
		this.randomNumber = randomNumber;
	}

	public int getId() {
		return id;
	}

	public int getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(int randomNumber) {
		this.randomNumber = randomNumber;
	}
}