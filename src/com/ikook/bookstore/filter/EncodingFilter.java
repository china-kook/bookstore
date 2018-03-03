package com.ikook.bookstore.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;


public class EncodingFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// 拦截所有的请求，解决全站中文编码
		// 指定 request 和 response 的编码
		request.setCharacterEncoding("utf-8"); // 只对消息体有效
		response.setContentType("text/html;charset=utf-8");
		
		/*
		 * 在放行时，应该给目标资源一个 request 对象，
		 * 让目标资源调用 getParameter 时调到我们写的 getParameter 
		 */
		//对 request 进行包装
		CharacterRequest characterRequest = new CharacterRequest(request);
		chain.doFilter(characterRequest, response);
	}

	public void destroy() {
	}
}

// 针对 request 对象进行包装
// 继承 默认包装类 HttpServletRequestWrapper
class CharacterRequest extends HttpServletRequestWrapper {
	
	public CharacterRequest(HttpServletRequest request) {
		super(request);
	}

	// 子类继承父类一定会复写一些方法，此处用于重写 getParamter() 方法
	public String getParameter(String name) {
		// 调用 被包装对象的 getParameter() 方法 获得请求参数
		String value = super.getParameter(name);
		if (value == null)
			return null;
		// 判断请求方式
		String method = super.getMethod();
		if ("get".equalsIgnoreCase(method)) {
			try {
				value = new String(value.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		// 解决乱码后返回结果
		return value;
	}
}