package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.entity.Event;
import com.example.demo.entity.EventUser;
import com.example.demo.service.BaseService;
import com.example.demo.service.EventUserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = {"/events/","/admin/events"})
public class EventsController {
	@Autowired
	BaseService<Event> eventService;
	@Autowired
	EventUserService eventusersService;
	
	/*
	 * 新規作成画面表示
	 */
	@GetMapping(value = "/create")
	public String form(Event event, Model model) {
		model.addAttribute("event", event);
		return "admin/events/create";
	}

	/*
	 * 新規登録
	 */
	@PostMapping(value = "/create")
	public String register(@Valid Event event, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "admin/events/create";
			}
			// 新規登録
			eventService.save(event);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/";
	}

	/*
	 * 編集
	 */
	/*
	 * 編集画面表示
	 */
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			// 存在確認
			Event event = eventService.findById(id);
			model.addAttribute("event", event);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/events";
		}
		return "admin/events/edit";
	}
	
	/*
	 * 更新
	 */
	@PostMapping(value = "/edit/{id}")
	public String update(@PathVariable Integer id, @Valid Event event, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "admin/events/edit";
			}
			eventService.findById(id);
			// 更新
			eventService.save(event);
			flash = new FlashData().success("更新しました");
		} catch (Exception e) {
			flash = new FlashData().danger("該当データがありません");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/";
	}
	
	/*
	 * マイリスト表示
	 */
	@GetMapping(value = "/mylist/{id}")
	public String mylist(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			// 存在確認
			Event events = eventService.findByuserId(id);
			model.addAttribute("events", events);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/";
		}
		return "admin/events/mylist";
	}
	
	/*
	 * イベント表示
	 */
	@GetMapping(value =	 "/view/{id}")
	public String view(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			// 存在確認
			Event events = eventService.findById(id);
			model.addAttribute("events", events);
			List<EventUser> eventusers = eventusersService.findByEventId(id);
			model.addAttribute("eventusers", eventusers);
			int size = eventusers.size();
			model.addAttribute("size", size);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/events";
		}
		return "admin/events/view";
	}
	
	/*
	 * イベント表示
	 * ログイン後のview画面と干渉するためviewbeforクラスを作成し対応
	 */
	@GetMapping(value =	 "/viewbefor/{id}")
	public String viewbefor(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			// 存在確認
			Event events = eventService.findById(id);
			model.addAttribute("events", events);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/events";
		}
		return "/events/viewbefor";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes ra) {
		FlashData flash;
		try {
			eventService.findById(id);
			eventService.deleteById(id);
			flash = new FlashData().success("イベントの削除が完了しました");
			ra.addFlashAttribute("flash", flash);
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/";
	}	
}
