package com.gearwenxin.entity.chatmodel;

import lombok.*;

import java.util.Map;

/**
 * @author Ge Mingjia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatPromptRequest {

    /**
     * prompt工程里面对应的模板id
     */
    private int id;

    /**
     * 参数map
     */
    private Map<String, String> paramMap;
}
