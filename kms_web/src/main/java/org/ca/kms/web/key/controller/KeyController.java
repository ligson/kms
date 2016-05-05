package org.ca.kms.web.key.controller;

import org.ca.kms.key.api.KeyApi;
import org.ca.kms.key.dto.GenKeyRequestDto;
import org.ca.kms.key.dto.GenKeyResponseDto;
import org.ca.kms.key.dto.KeyQueryRequestDto;
import org.ca.kms.key.dto.KeyQueryResponseDto;
import org.ca.kms.user.dto.QueryUserRequestDto;
import org.ca.kms.user.vo.User;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.web.controller.BaseController;
import org.ligson.fw.web.vo.WebResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/5/5.
 */
@Controller
@RequestMapping("/key")
public class KeyController extends BaseController {

    @Resource
    private KeyApi keyApi;

    @RequestMapping("/index.html")
    public String index() {
        return "key/index";
    }

    @ResponseBody
    @RequestMapping("/keyList.json")
    public WebResult keyList(KeyQueryRequestDto requestDto) {
        Result<KeyQueryResponseDto> result = keyApi.queryKey(requestDto);
        if (result.isSuccess()) {
            webResult.put("total", result.getData().getTotalCount());
            webResult.put("rows", result.getData().getKeyList());
            webResult.setSuccess(true);
        } else {
            webResult.setError(result);
        }
        return webResult;
    }

    @ResponseBody
    @RequestMapping("/genKey.do")
    public WebResult genKey(GenKeyRequestDto requestDto) {
        User user = (User) session.getAttribute("user");
        requestDto.setUserId(user.getId());
        Result<GenKeyResponseDto> result = keyApi.genKey(requestDto);
        if (result.isSuccess()) {
            webResult.setSuccess(true);
        } else {
            webResult.setError(result);
        }
        return webResult;
    }
}
