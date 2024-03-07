package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.EventUser;
import com.example.demo.repository.EventUserRepository;

@Repository
public class EventUserDao implements BaseDao<EventUser> {
	@Autowired
	EventUserRepository repository;

	@Override
	public List<EventUser> findAll() { //List　複数取れるとき
		return repository.findAll();
	}

	@Override
	public EventUser findById(Integer id) throws DataNotFoundException {
		return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(EventUser eventuser) {
		this.repository.save(eventuser);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			EventUser eventUser = this.findById(id);
			this.repository.deleteById(eventUser.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}

	@Override
	public EventUser findByuserId(Integer user_id) throws DataNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	public List<EventUser> findByEventId(Integer event_id) throws DataNotFoundException {
		List<EventUser> eventUser = this.repository.findByEventId(event_id);
		if (eventUser == null) {
			throw new DataNotFoundException();
		}
		return eventUser;
	}
	
	
}
