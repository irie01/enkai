package com.example.demo.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.entity.Event;
import com.example.demo.entity.EventUser;
import com.example.demo.service.BaseService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/eventusers")
public class EventUsersController {
	@Autowired
	BaseService<EventUser> eventusersservice;
	@Autowired
	BaseService<Event> eventsservice;
	@Autowired
	UserService usersservice;
	/*
	 * 新規登録
	 */
	@GetMapping(value = "/create/{id}")
	public String register(@PathVariable Integer id, @Valid EventUser eventuser, BindingResult result, Model model, RedirectAttributes ra) {
		model.addAttribute("id", id);
		FlashData flash;
		try {
			eventuser.setEvent(eventsservice.findById(id)); //イベントIDに紐づくイベントテーブルを取得
			String email = SecurityContextHolder.getContext().getAuthentication().getName(); //Eメールを取ってくる
			eventuser.setUser(usersservice.findByEmail(email));//Emeilに紐づユーザテーブルを取得
			
			// 新規登録	
			eventusersservice.save(eventuser);
			flash = new FlashData().success("参加しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/events/view/{id}";
	}

}
