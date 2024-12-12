package com.board.common;

import java.time.LocalDateTime;
import com.board.common.base.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends BaseResponse {

    private String error;
    private String path;
    private LocalDateTime timestamp;

    /**
     * @param status HTTP 상태 코드
     * @param message 에러 메시지
     * @param error 발생한 에러의 이름
     * @param path 에러가 발생한 요청 경로
     * @param timestamp 에러 발생 시간
     */
    public ErrorResponse(int status, String message, String error, String path, LocalDateTime timestamp) {
        super(status, message);
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
    }
}