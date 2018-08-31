package com.jerry.geekdaily.controller;

import com.jerry.geekdaily.domain.Article;
import com.jerry.geekdaily.repository.ArticleRepository;
import com.jerry.geekdaily.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    private ArticleRepository articleRepository;
    /**
     * 查看主页的web页面
     * @return
     */
    @GetMapping(value = {"/", "/index"})
    public String index(){
        return "index";
    }

    /**
     * 登陆
     * @return
     */
    @GetMapping(value = ("/login"))
    public String login(){
        return "login";
    }

    /**
     * 注册
     * @return
     */
    @GetMapping(value = ("/register"))
    public String register(){
        return "register";
    }

    //退出登录
    @GetMapping(value = ("/logout"))
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //清空cookies
        CookieUtils.removeCookie(response, request);
        return "login";
    }

    @GetMapping(value = ("/webArticle"))
    public ModelAndView article(){
        Page<Article> pages = articleRepository.findAll(PageRequest.of(0,15, new Sort(Sort.Direction.DESC, "date")));
        ModelAndView view = new ModelAndView("article");
        view.addObject("articleList", pages.getContent());
        view.addObject("pageCount", pages.getTotalPages());
        return view;
    }

    @GetMapping(value = ("/webAddArticle"))
    public String addArticle(){
        return "add-article";
    }

    @GetMapping(value = ("/webUpdateArticle"))
    public ModelAndView updateArticle(@RequestParam int article_id){
        Article article = articleRepository.findArticleByArticle_id(article_id);
        ModelAndView view = new ModelAndView("update-article");
        view.addObject("article",article);
        return view;
    }
}
