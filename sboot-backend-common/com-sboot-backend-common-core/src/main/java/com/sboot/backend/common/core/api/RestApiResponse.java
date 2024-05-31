package com.sboot.backend.common.core.api;

public class RestApiResponse<T> {

    private T result;

    private int resultCode;

    private String resultUserMessage;

    public RestApiResponse(final T result, final int resultCode, final String resultUserMessage) {
        this.result = result;
        this.resultCode = resultCode;
        this.resultUserMessage = resultUserMessage;
    }

    public T getResult() {
        return result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultUserMessage() {
        return resultUserMessage;
    }

    public static <T> RestApiResponseBuilder<T> builder(){
        return new RestApiResponseBuilder<>();
    }


    public static class RestApiResponseBuilder<T> {

        private T result;
        private int resultCode;
        private String resultUserMessage;

        public RestApiResponseBuilder<T> result(T result) {
            this.result = result;
            return this;
        }

        public RestApiResponseBuilder<T> resultCode(int resultCode){
            this.resultCode = resultCode;
            return this;
        }

        public RestApiResponseBuilder<T> resultUserMessage(String resultUserMessage){
            this.resultUserMessage = resultUserMessage;
            return this;
        }

        public RestApiResponse<T> build(){
            return new RestApiResponse<>(result, resultCode, resultUserMessage);
        }
    }
}
