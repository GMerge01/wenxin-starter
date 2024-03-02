package com.gearwenxin.client.base;

import com.gearwenxin.core.ChatCore;
import com.gearwenxin.common.ErrorCode;
import com.gearwenxin.core.ChatUtils;
import com.gearwenxin.entity.chatmodel.ChatBaseRequest;
import com.gearwenxin.entity.response.ChatResponse;
import com.gearwenxin.exception.WenXinException;
import com.gearwenxin.model.BaseBot;
import com.gearwenxin.model.chat.SingleBot;
import com.gearwenxin.validator.RequestValidator;
import com.gearwenxin.validator.RequestValidatorFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;

import static com.gearwenxin.common.WenXinUtils.assertNotBlankMono;

/**
 * @author Ge Mingjia
 * {@code @date} 2023/8/4
 */
@Slf4j
@Data
public abstract class BaseClient implements SingleBot, BaseBot {

    @Override
    public Mono<ChatResponse> chatSingle(String content) {
        return Mono.from(this.chatSingleFunc(content, this::chatSingle));
    }

    @Override
    public Flux<ChatResponse> chatSingleOfStream(String content) {
        return Flux.from(this.chatSingleFunc(content, this::chatSingleOfStream));
    }

    @Override
    public <T extends ChatBaseRequest> Mono<ChatResponse> chatSingle(T chatRequest) {
        return Mono.from(chatSingleProcess(chatRequest, false));
    }

    @Override
    public <T extends ChatBaseRequest> Flux<ChatResponse> chatSingleOfStream(T chatRequest) {
        return Flux.from(chatSingleProcess(chatRequest, true));
    }

    public <T extends ChatBaseRequest> Publisher<ChatResponse> chatSingleProcess(T requestT, boolean stream) {
        return Mono.justOrEmpty(requestT)
                .switchIfEmpty(Mono.error(() -> new WenXinException(ErrorCode.PARAMS_ERROR)))
                .doOnNext(reqT -> validRequest(requestT))
                .flatMapMany(reqT -> {
                    Object targetRequest = ChatUtils.buildTargetRequest(null, stream, reqT);

                    String logMessage = stream ? "{}-single-request-stream => {}" : "{}-single-request => {}";
                    log.info(logMessage, getTag(), targetRequest);

                    return typeReturn(stream, targetRequest);
                });
    }

    public Publisher<ChatResponse> typeReturn(boolean stream, Object request) {
        return stream ? ChatCore.fluxChatPost(getURL(), getCustomAccessToken(), request, ChatResponse.class) :
                ChatCore.monoChatPost(getURL(), getCustomAccessToken(), request, ChatResponse.class);
    }

    public <T extends ChatBaseRequest> void validRequest(T request) {
        RequestValidator validator = RequestValidatorFactory.getValidator(request);
        validator.validate(request);
    }

    private Publisher<ChatResponse> chatSingleFunc(String content, Function<ChatBaseRequest, Publisher<ChatResponse>> chatFunction) {
        assertNotBlankMono(content, "content is null or blank");

        return chatFunction.apply(this.buildRequest(content));
    }

    public ChatBaseRequest buildRequest(String content) {
        return ChatBaseRequest.builder().content(content).build();
    }

}