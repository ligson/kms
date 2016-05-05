package org.ca.kms.web.common.controller;

import org.ca.common.user.enums.LoginNameType;
import org.ca.kms.user.api.UserApi;
import org.ca.kms.user.dto.LoginRequestDto;
import org.ca.kms.user.dto.LoginResponseDto;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.string.encode.HashHelper;
import org.ligson.fw.string.validator.EmailValidator;
import org.ligson.fw.string.validator.PhoneValidator;
import org.ligson.fw.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/5/5.
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    private UserApi userApi;

    @RequestMapping("/login.html")
    public String toLogin() {
        return "index";
    }

    @RequestMapping("/login.do")
    public String login(LoginRequestDto requestDto) {
        String loginName = requestDto.getLoginName();
        if (PhoneValidator.isMobile(loginName)) {
            requestDto.setLoginNameType(LoginNameType.MOBILE);
        } else if (EmailValidator.isValidEmail(loginName)) {
            requestDto.setLoginNameType(LoginNameType.EMAIL);
        } else {
            requestDto.setLoginNameType(LoginNameType.NAME);
        }
        requestDto.setPassword(HashHelper.md5(requestDto.getPassword()));

        if (!requestDto.validate()) {
            String errorMsg = "";
            for (String e : requestDto.getErrorFieldMap().values()) {
                errorMsg += e + "<br/>";
            }
            request.setAttribute("errorMsg", errorMsg);
            return forward("/user/login.html");
        }

        Result<LoginResponseDto> result = userApi.login(requestDto);
        if (result.isSuccess()) {
            session.setAttribute("user", result.getData().getUser());
            return redirect("/key/index.html");
        } else {
            request.setAttribute("errorMsg", result.getFailureMessage());
            return forward("/login.html");
        }
    }
}
