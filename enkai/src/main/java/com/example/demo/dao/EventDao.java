package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;

@Repository
public class EventDao implements BaseDao<Event> {
	@Autowired
	EventRepository repository;

	@Override
	public List<Event> findAll() {
		return repository.findAll();
	}

	@Override
	public Event findById(Integer id) throws DataNotFoundException {
		return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(Event event) {
		this.repository.save(event);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Event event = this.findById(id);
			this.repository.deleteById(event.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}
	
	public Event findByuserId(Integer user_id) throws DataNotFoundException {
		Event event = this.repository.findByuserId(user_id);
		if (event == null) {
			throw new DataNotFoundException();
		}
		return event;
	}
}

