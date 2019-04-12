package com.winbaoxian.module.example.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum CookieUtils {
    INSTANCE;

    /**
	 * 查询cookie
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public String getCookie(HttpServletRequest request, String cookieName){
		Cookie cookies[]=request.getCookies();//创建一个Cookie对象数组
		if(null!=cookies && cookies.length>0){
			for(int i=0;i<cookies.length;i++){
				Cookie newCookie= cookies[i];
				if(newCookie.getName().equals(cookieName)){
					return newCookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 添加cookie
	 * @param response
	 * @param cookieName
	 * @param cookieValues
	 */
	public void addCookie(HttpServletResponse response, String cookieName, String cookieValues, int maxage){

        Cookie c = new Cookie(cookieName,cookieValues) ;
        //设定有效时间  以s为单位
        c.setMaxAge(maxage) ;
        //设置Cookie路径和域名
        c.setPath("/") ;
       // c.setDomain(".zl.org") ;
        //发送Cookie文件
        response.addCookie(c) ;
	}
	
	/**
	 * 删除cookie
	 * @param request
	 * @param response
	 * @param cookieName
	 */
	public void delCookie(HttpServletRequest request, HttpServletResponse response, String cookieName){
        if(request.getCookies() != null){
            Cookie cookies[] = request.getCookies() ;
            Cookie c = null ;
            for(int i=0;i<cookies.length;i++){
                c = cookies[i] ;
                if(c.getName().equals(cookieName)){
                    c.setPath("/");
                    c.setMaxAge(0);
                    c.setValue(null);
                    response.addCookie(c) ;
                }
            }
        }
	}

    /**
	 * 删除cookie
	 * @param request
	 * @param response
	 * @param cookieName
	 */
	public void delCookie(HttpServletRequest request, HttpServletResponse response, String domain, String cookieName){
        if(request.getCookies() != null){
            Cookie cookies[] = request.getCookies() ;
            Cookie c ;
            for(int i=0;i<cookies.length;i++){
                c = cookies[i] ;
                if(c.getName().equals(cookieName)){
                    c.setDomain(domain);
                    c.setPath("/");
                    c.setMaxAge(0);
                    c.setValue(null);
                    response.addCookie(c) ;
                }
            }
        }
	}
	
}
