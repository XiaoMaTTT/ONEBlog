package com.controller;

import com.entity.Comment;
import com.entity.Msg;
import com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;

	// 提交评论
	@RequestMapping(value="/sendcomment", method=RequestMethod.POST)
	@ResponseBody
	public Msg sendcomment(@RequestParam(value="id", required=true)int id,
                           @RequestParam("name") String name, @RequestParam("comment") String comment) throws ParseException {
		System.out.println("name = " + name + " \ncomment = " + comment);
		commentService.sendcomment(id, name, comment);
		Msg msg = Msg.success();
		msg.setMsg("提交评论成功！");
		return msg;
	}

	// 获得评论
	@RequestMapping(value="/getComments", method=RequestMethod.GET)
	@ResponseBody
	public Msg getComments(@RequestParam("id")int id) {
		// 获取文章所有的评论
		List<Comment> list = commentService.getComments(id);
		return Msg.success().add("commentsList", list);
	}

	// 获得所有评论
	@RequestMapping(value="/getallcomments", method=RequestMethod.GET)
	@ResponseBody
	public Msg getallcomments() {
		List<Comment> list = commentService.selectAll();
		return Msg.success().add("commentList", list);
	}

	// 评论后台管理
	@RequestMapping("/commentback")
	public String commentback(HttpServletRequest request,HttpServletResponse response) {
		//得到 session 的值，判断是否已经登陆
		if(request.getSession().getAttribute("username") != null) {
			String username = request.getSession().getAttribute("username").toString();
			if(username != null) {
				return "admin/commentadmin";
			}
		}
		return "admin/error";
	}

	// 删除一条评论
	@RequestMapping(value="/deleteacomment",method=RequestMethod.GET)
	@ResponseBody
	public Msg deleteacomment(@RequestParam("id") int id) {
		commentService.deletea(id);
		Msg msg = Msg.success();
		msg.setMsg("此条评论已删除！");
		return msg;
	}

	// 提交评论
	@RequestMapping(value="/sendheartword", method=RequestMethod.POST)
	@ResponseBody
	public Msg sendheartword(@RequestParam(value="id", required=true)int id,
                             @RequestParam("name") String name, @RequestParam("comment") String comment) throws ParseException {
		System.out.println("name = " + name + " \ncomment = " + comment);
		commentService.sendcomment(id, name, comment);
		Msg msg = Msg.success();
		msg.setMsg("感谢大佬的评论");
		return msg;
	}

}
