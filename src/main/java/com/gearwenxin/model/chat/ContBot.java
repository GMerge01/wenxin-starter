package com.gearwenxin.model.chat;

import com.gearwenxin.entity.chatmodel.ChatBaseRequest;
import com.gearwenxin.entity.response.ChatResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Ge Mingjia

 */
public interface ContBot<T> {

    /**
     * 多轮对话，有上下文记忆，默认参数
     * （非流式）
     *
     * @param content 对话内容
     * @param msgUid  对话唯一识别码
     * @return ChatResponse 响应实体类
     */
    Mono<ChatResponse> chatCont(String content, String msgUid);

    /**
     * 多轮对话，有上下文记忆，默认参数
     * （非流式）
     *
     * @param content 对话内容
     * @param msgUid  对话唯一识别码
     * @return ChatResponse 响应实体类
     */
    Flux<ChatResponse> chatContOfStream(String content, String msgUid);

    /**
     * 多轮对话，有上下文记忆，支持参数配置
     * （非流式）
     *
     * @param chatRequest 请求实体类
     * @param msgUid      对话唯一识别码
     * @return ChatResponse 响应实体类
     */
    <T extends ChatBaseRequest> Mono<ChatResponse> chatCont(T chatRequest, String msgUid);

    /**
     * 多轮对话，有上下文记忆，支持参数配置
     * （非流式）
     *
     * @param chatRequest 请求实体类
     * @param msgUid      对话唯一识别码
     * @return ChatResponse 响应实体类
     */
    <T extends ChatBaseRequest> Flux<ChatResponse> chatContOfStream(T chatRequest, String msgUid);

}
