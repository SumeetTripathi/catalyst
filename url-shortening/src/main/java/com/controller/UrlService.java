package com.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cache.RedisCache;

@Controller
public class UrlService {

	@Autowired
	private RedisCache redisCache;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		return "home";
	}

	@RequestMapping(value = "/shrinkurl", method = RequestMethod.POST)
	public String shrinkUrl(String url, String customUrl, Integer expiry,
			Integer privateCount, Model model) {
		if (customUrl != null && customUrl.trim().length() > 0) {
			if (redisCache.validateUrl(customUrl)) {
				model.addAttribute("shrinkurl",
						redisCache.addUrl(customUrl, url, expiry));
			} else {
				model.addAttribute("shrinkurl",
						redisCache.addUrl(null, url, expiry));
				model.addAttribute("message",
						"Custom alias already in use new key is generated");
			}
		} else {
			model.addAttribute("shrinkurl",
					redisCache.addUrl(customUrl, url, expiry));
		}
		return "shrinkurl";
	}

	@RequestMapping(value = "/trackusage", method = RequestMethod.POST)
	public String get(String shrinkurl, Model model) {
		System.out.println("shrinkurl" + shrinkurl);

		try {
			model.addAttribute("hits", redisCache.getHits(shrinkurl));
		} catch (Throwable e) {
			// do-nothing url read never occurred
		}
		model.addAttribute("shrinkurl", shrinkurl);

		return "trackusage";
	}

	@RequestMapping(value = "/uri/{url}", method = RequestMethod.GET)
	public String geturl(@PathVariable("url") String url, Model model) {
		System.out.println("url" + url);

		try {
			model.addAttribute("shrinkurl", redisCache.getUrl(url));
			return "geturl";
		} catch (Exception e) {
			return "notfound";
		}
	}

}
