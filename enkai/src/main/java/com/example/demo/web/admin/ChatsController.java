package com.example.demo.web.admin;

import java.util.List;

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
import com.example.demo.entity.Chat;
import com.example.demo.service.ChatService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = {"/admin/chats"})
public class ChatsController {
	@Autowired
	ChatService chatService;
	@Autowired
	UserService usersservice;
	
	
	/*
	 * チャット表示
	 */
	@GetMapping(value =	 "/talk/{id}")
	public String talk(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			Chat chat = chatService.findById(id);
			// 存在確認
			List<Chat> chats = chatService.findByEventId(id);
			model.addAttribute("chat", chat);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/chats";
		}
		return "admin/chats/talk";
	}
	
	@GetMapping(value = "/create")
	public String register(@Valid  Chat chat, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			// 新規登録
			String email = SecurityContextHolder.getContext().getAuthentication().getName(); //Eメールを取ってくる
			chat.setUser(usersservice.findByEmail(email));//Emeilに紐づユーザテーブルを取得
			chatService.save(chat);
			flash = new FlashData().success("投稿しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/";
	}

	
}