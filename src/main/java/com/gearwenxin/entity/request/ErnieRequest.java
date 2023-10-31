package com.gearwenxin.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gearwenxin.entity.BaseRequest;

import com.gearwenxin.entity.FunctionInfo;
import com.gearwenxin.entity.Message;
import lombok.*;

import java.util.Deque;
import java.util.List;

/**
 * @author Ge Mingjia

 * <p>
 * ContBot 模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ErnieRequest extends BaseRequest {

    /**
     * （1）较高的数值会使输出更加随机，而较低的数值会使其更加集中和确定
     * （2）默认0.95，范围 (0, 1.0]，不能为0
     * （3）建议该参数和top_p只设置1个
     * （4）建议top_p和temperature不要同时更改
     */
    @JsonProperty("temperature")
    private Float temperature;

    /**
     * （1）影响输出文本的多样性，取值越大，生成文本的多样性越强
     * （2）默认0.8，取值范围 [0, 1.0]
     * （3）建议该参数和temperature只设置1个
     * （4）建议top_p和temperature不要同时更改
     */
    @JsonProperty("top_p")
    private Float topP;

    /**
     * 通过对已生成的token增加惩罚，减少重复生成的现象。说明：
     * （1）值越大表示惩罚越大
     * （2）默认1.0，取值范围：[1.0, 2.0]
     */
    @JsonProperty("penalty_score")
    private Float penaltyScore;

    /**
     * 一个可触发函数的描述列表
     */
    @JsonProperty("functions")
    private List<FunctionInfo> functions;

    /**
     * 模型人设，主要用于人设设定，例如，你是xxx公司制作的AI助手，说明：
     * （1）长度限制1024个字符
     * （2）如果使用functions参数，不支持设定人设system
     */
    @JsonProperty("system")
    private String system;

    public static class ErnieRequestBuilder extends BaseRequestBuilder {
        private Float temperature;
        private Float topP;
        private Float penaltyScore;
        private String userId;
        private Deque<Message> messages;
        private Boolean stream;
        private List<FunctionInfo> functions;
        private String system;

        public ErnieRequestBuilder temperature(Float temperature) {
            this.temperature = temperature;
            return this;
        }

        public ErnieRequestBuilder topP(Float topP) {
            this.topP = topP;
            return this;
        }

        public ErnieRequestBuilder penaltyScore(Float penaltyScore) {
            this.penaltyScore = penaltyScore;
            return this;
        }

        @Override
        public ErnieRequestBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        @Override
        public ErnieRequestBuilder messages(Deque<Message> messages) {
            this.messages = messages;
            return this;
        }

        @Override
        public ErnieRequestBuilder stream(Boolean stream) {
            this.stream = stream;
            return this;
        }

        public ErnieRequestBuilder functions(List<FunctionInfo> functions) {
            this.functions = functions;
            return this;
        }

        public ErnieRequestBuilder system(String system) {
            this.system = system;
            return this;
        }

        @Override
        public ErnieRequest build() {
            ErnieRequest ernieRequest = new ErnieRequest();
            ernieRequest.setTemperature(temperature);
            ernieRequest.setTopP(topP);
            ernieRequest.setPenaltyScore(penaltyScore);
            ernieRequest.setUserId(userId);
            ernieRequest.setMessages(messages);
            ernieRequest.setStream(stream);
            ernieRequest.setFunctions(functions);
            ernieRequest.setSystem(system);
            return ernieRequest;
        }
    }

    public static ErnieRequestBuilder builder() {
        return new ErnieRequestBuilder();
    }

}
