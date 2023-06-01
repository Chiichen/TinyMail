package edu.scut.tinymail.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.List;

public interface RememberMeServices extends org.springframework.security.web.authentication.RememberMeServices, InitializingBean, LogoutHandler, MessageSourceAware {
    String REMEMBER_ME_KEY = "rememberMe";
    List<String> TRUE_VALUES = List.of("true", "yes", "on", "1");

    boolean rememberMeRequested(HttpServletRequest request, String parameter);
}
