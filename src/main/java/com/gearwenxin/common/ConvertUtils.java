package com.gearwenxin.common;

import com.gearwenxin.entity.BaseRequest;
import com.gearwenxin.entity.chatmodel.*;
import com.gearwenxin.entity.request.ErnieRequest;
import com.gearwenxin.entity.request.PromptRequest;
import com.gearwenxin.exception.WenXinException;

import java.util.Objects;

import static com.gearwenxin.common.WenXinUtils.assertNotBlank;
import static com.gearwenxin.common.WenXinUtils.assertNotNull;

/**
 * 类型转换工具类
 *
 * @author Ge Mingjia
 */
public class ConvertUtils {

    public static ErnieRequest.ErnieRequestBuilder toErnieRequest(ChatErnieRequest chatRequest) {

        assertNotNull(chatRequest, "ChatErnieRequest is null");
        assertNotBlank(chatRequest.getContent(), "ChatErnieRequest.content is null");

        return ErnieRequest.builder()
                .userId(chatRequest.getUserId())
                .messages(WenXinUtils.buildUserMessageDeque(chatRequest.getContent()))
                .temperature(chatRequest.getTemperature())
                .topP(chatRequest.getTopP())
                .penaltyScore(chatRequest.getPenaltyScore())
                .functions(chatRequest.getFunctions())
                .system(chatRequest.getSystem());
    }

    public static BaseRequest.BaseRequestBuilder toBaseRequest(ChatBaseRequest chatRequest) {

        assertNotNull(chatRequest, "ChatBaseRequest is null");
        assertNotBlank(chatRequest.getContent(), "ChatBaseRequest.content is null");

        return BaseRequest.builder()
                .userId(chatRequest.getUserId())
                .messages(WenXinUtils.buildUserMessageDeque(chatRequest.getContent()));
    }

    public static PromptRequest toPromptRequest(ChatPromptRequest chatRequest) {

        assertNotNull(chatRequest, "ChatBaseRequest is null");
        assertNotNull(chatRequest.getParamMap(), "ChatPromptRequest.ParamMap is null");

        return PromptRequest.builder()
                .id(String.valueOf(chatRequest.getId()))
                .paramMap(chatRequest.getParamMap())
                .build();
    }

}