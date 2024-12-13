package com.board.common;

import com.board.common.base.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse<T> extends BaseResponse {

    private T data;

    /**
     * @param status  HTTP 상태 코드
     * @param message 성공 메시지
     * @param data    성공 데이터
     */
    public SuccessResponse(int status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}