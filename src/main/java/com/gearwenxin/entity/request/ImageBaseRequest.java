package com.gearwenxin.entity.request;

import com.gearwenxin.common.ErrorCode;
import com.gearwenxin.exception.WenXinException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static com.gearwenxin.common.Constant.MAX_CONTENT_LENGTH;

/**
 * @author Ge Mingjia
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageBaseRequest {

    /**
     * 提示词
     */
    private String content;

    /**
     * 宽 px
     */
    private int width;

    /**
     * 高 px
     */
    private int height;

    public void validSelf() {

        // 检查content不为空
        if (StringUtils.isBlank(content)) {
            throw new WenXinException(ErrorCode.PARAMS_ERROR, "content cannot be empty");
        }
        // 检查单个content长度
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new WenXinException(ErrorCode.PARAMS_ERROR, "content's length cannot be more than 2000");
        }
        if (width <= 0) {
            throw new WenXinException(ErrorCode.PARAMS_ERROR, "width is less than or eq 0");
        }
        if (height <= 0) {
            throw new WenXinException(ErrorCode.PARAMS_ERROR, "height is less than or eq 0");
        }
    }

}
