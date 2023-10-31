package com.gearwenxin.client.base;

import com.gearwenxin.common.*;
import com.gearwenxin.entity.BaseRequest;
import com.gearwenxin.entity.Message;
import com.gearwenxin.entity.chatmodel.ChatBaseRequest;
import com.gearwenxin.entity.response.ChatResponse;
import com.gearwenxin.exception.WenXinException;
import com.gearwenxin.model.chat.ContBot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.LinkedList;
import java.util.Map;
import java.util.Deque;

/**
 * @author Ge Mingjia

 */
@Slf4j
public abstract class FullClient extends BaseClient implements ContBot<ChatBaseRequest> {

    /**
     * 获取此模型的历史消息
     */
    public abstract Map<String, Deque<Message>> getMessageHistoryMap();

    /**
     * 初始化此模型的历史消息
     */
    public abstract void initMessageHistoryMap(Map<String, Deque<Message>> map);

    @Override
    public Mono<ChatResponse> chatCont(String content, String msgUid) {
        return Mono.just(Tuples.of(content, msgUid))
                .filter(tuple -> StringUtils.isNotBlank(tuple.getT1()) && StringUtils.isNotBlank(tuple.getT2()))
                .switchIfEmpty(Mono.error(() -> new WenXinException(ErrorCode.PARAMS_ERROR)))
                .flatMap(tuple -> {
                    Map<String, Deque<Message>> messageHistoryMap = getMessageHistoryMap();
                    Deque<Message> messagesHistory = messageHistoryMap.computeIfAbsent(
                            tuple.getT2(), k -> new LinkedList<>()
                    );

                    Message message = WenXinUtils.buildUserMessage(tuple.getT1());
                    WenXinUtils.offerMessage(messagesHistory, message);

                    BaseRequest baseRequest = BaseRequest.builder()
                            .messages(messagesHistory)
                            .build();

                    log.info("{}-content-contRequest => {}", getTag(), baseRequest.toString());

                    return ChatUtils.historyMono(getURL(), getCustomAccessToken(), baseRequest, messagesHistory);
                });
    }

    @Override
    public Flux<ChatResponse> chatContOfStream(String content, String msgUid) {
        return Mono.just(Tuples.of(content, msgUid))
                .filter(tuple -> StringUtils.isNotBlank(tuple.getT1()) && StringUtils.isNotBlank(tuple.getT2()))
                .switchIfEmpty(Mono.error(() -> new WenXinException(ErrorCode.PARAMS_ERROR)))
                .flatMapMany(tuple -> {
                    Map<String, Deque<Message>> messageHistoryMap = getMessageHistoryMap();
                    Deque<Message> messagesHistory = messageHistoryMap.computeIfAbsent(
                            tuple.getT2(), k -> new LinkedList<>()
                    );

                    Message message = WenXinUtils.buildUserMessage(tuple.getT1());
                    WenXinUtils.offerMessage(messagesHistory, message);

                    BaseRequest baseRequest = BaseRequest.builder()
                            .messages(messagesHistory)
                            .stream(true)
                            .build();

                    log.info("{}-content-contRequest-stream => {}", getTag(), baseRequest.toString());

                    return ChatUtils.historyFlux(getURL(), getCustomAccessToken(), baseRequest, messagesHistory);
                });
    }


    @Override
    public Mono<ChatResponse> chatCont(ChatBaseRequest chatBaseRequest, String msgUid) {
        return Mono.justOrEmpty(chatBaseRequest)
                .filter(request -> StringUtils.isNotBlank(msgUid))
                .switchIfEmpty(Mono.error(() -> new WenXinException(ErrorCode.PARAMS_ERROR)))
                .doOnNext(ChatBaseRequest::validSelf)
                .flatMap(request -> {
                    Map<String, Deque<Message>> messageHistoryMap = getMessageHistoryMap();
                    Deque<Message> messagesHistory = messageHistoryMap.computeIfAbsent(
                            msgUid, key -> new LinkedList<>()
                    );

                    Message message = WenXinUtils.buildUserMessage(request.getContent());
                    WenXinUtils.offerMessage(messagesHistory, message);

                    BaseRequest baseRequest = ConvertUtils.toBaseRequest(request)
                            .messages(messagesHistory)
                            .build();

                    log.info("{}-contRequest => {}", getTag(), baseRequest.toString());

                    return ChatUtils.historyMono(getURL(), getCustomAccessToken(), baseRequest, messagesHistory);
                });
    }

    @Override
    public Flux<ChatResponse> chatContOfStream(ChatBaseRequest chatBaseRequest, String msgUid) {
        return Mono.justOrEmpty(chatBaseRequest)
                .filter(request -> StringUtils.isNotBlank(msgUid))
                .switchIfEmpty(Mono.error(() -> new WenXinException(ErrorCode.PARAMS_ERROR)))
                .doOnNext(ChatBaseRequest::validSelf)
                .flatMapMany(request -> {
                    Map<String, Deque<Message>> messageHistoryMap = getMessageHistoryMap();
                    Deque<Message> messagesHistory = messageHistoryMap.computeIfAbsent(
                            msgUid, key -> new LinkedList<>()
                    );

                    Message message = WenXinUtils.buildUserMessage(request.getContent());
                    WenXinUtils.offerMessage(messagesHistory, message);

                    BaseRequest baseRequest = ConvertUtils.toBaseRequest(request)
                            .messages(messagesHistory)
                            .stream(true)
                            .build();

                    log.info("{}-contRequest-stream => {}", getTag(), baseRequest.toString());

                    return ChatUtils.historyFlux(getURL(), getCustomAccessToken(), baseRequest, messagesHistory);
                });
    }

    private Mono<ChatResponse> processChatRequest(String content, String msgUid, boolean isStream, ChatBaseRequest chatBaseRequest) {
        return Mono.justOrEmpty(Tuples.of(content, msgUid, chatBaseRequest))
                .filter(tuple -> StringUtils.isNotBlank(tuple.getT2()))
                .doOnNext(tuple -> chatBaseRequest.validSelf())
                .flatMap(tuple -> {
                    Map<String, Deque<Message>> messageHistoryMap = getMessageHistoryMap();
                    Deque<Message> messagesHistory = messageHistoryMap.computeIfAbsent(
                            tuple.getT2(), key -> new LinkedList<>()
                    );

                    String messageContent = chatBaseRequest.getContent();
                    Message message = WenXinUtils.buildUserMessage(messageContent);
                    WenXinUtils.offerMessage(messagesHistory, message);

                    BaseRequest.BaseRequestBuilder baseRequestBuilder = ConvertUtils.toBaseRequest(tuple.getT3())
                            .messages(messagesHistory);

                    if (isStream) {
                        baseRequestBuilder.stream(true);
                    }

                    BaseRequest baseRequest = baseRequestBuilder.build();

                    log.info("{}-contRequest{} => {}", getTag(), isStream ? "-stream" : "", baseRequest.toString());

                    return ChatUtils.historyMono(getURL(), getCustomAccessToken(), baseRequest, messagesHistory);
                });
    }

//    private Flux<ChatResponse> processFluxChatRequest(
//            String content,
//            String msgUid,
//            ChatBaseRequest chatBaseRequest,
//            boolean isStream
//    ) {
//        // TODO:补全代码
//        return chatBaseRequest != null ?
//                Flux.empty() : ChatUtils.historyFlux(getURL(), getCustomAccessToken(), baseRequest, messagesHistory);
//    }

}