package org.softuni.workman.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String link = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJvrL6KH8Hp1BXSOeO3IjZlM9a1Az_BHToGJ47dSfRXB4_ZY62&s";

        if (modelAndView != null) {
            modelAndView.addObject("favicon", link);
        }
    }
}
