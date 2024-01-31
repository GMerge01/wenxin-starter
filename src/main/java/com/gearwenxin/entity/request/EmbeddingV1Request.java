package com.gearwenxin.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ge Mingjia
 * {@code @date} 2023/7/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddingV1Request {

    /**
     * 内容
     */
    private String content;

}
