package com.gearwenxin.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ge Mingjia
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromptResponse {

    @JsonProperty("log_id")
    private String logId;

    private PromptResult result;

    private Integer status;

    private Boolean success;

    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("error_msg")
    private String errorMsg;

    @JsonProperty("code")
    private String promptErrCode;

    @JsonProperty("message")
    private PromptErrMessage promptErrMessage;
}
