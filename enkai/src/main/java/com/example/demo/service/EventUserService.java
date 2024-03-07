package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.EventUserDao;
import com.example.demo.entity.EventUser;

@Service
public class EventUserService implements BaseService<EventUser> {
	@Autowired
	private EventUserDao dao;

	@Override
	public List<EventUser> findAll() {
		return dao.findAll();
	}

	@Override
	public EventUser findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void save(EventUser eventUser) {
		dao.save(eventUser);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public EventUser findByuserId(Integer user_id) throws DataNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	public List<EventUser> findByEventId(Integer event_id) throws DataNotFoundException {
		return dao.findByEventId(event_id);
	}
}

