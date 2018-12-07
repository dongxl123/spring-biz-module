/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.winbaoxian.module.security.config.shiro;

import com.winbaoxian.module.security.model.common.JsonResult;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/")
public class WinSecurityErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public JsonResult error(HttpServletRequest request, HttpServletResponse response) {
        String errorMsg = "winSecurity internal error";
        int status = response.getStatus();
        if (status == 401) {
            errorMsg = "未登录";
        }
        if (status == 403) {
            errorMsg = "访问无权限";
        } else if (status == 404) {
            errorMsg = "请求URL不存在";
        } else if (status == 500) {
            errorMsg = "服务器内部错误";
        }
        return JsonResult.createErrorResult(errorMsg);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
