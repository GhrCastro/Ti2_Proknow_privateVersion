package application;

class StandardResponse {
    private StatusResponse status;
    private String message;
    private Object data;

    public StandardResponse(StatusResponse status) {
        this.status = status;
    }

    public StandardResponse(StatusResponse status, String message) {
        this.status = status;
        this.message = message;
    }

    public StandardResponse(StatusResponse status, Object data) {
        this.status = status;
        this.data = data;
    }

    // Getters and setters
}

enum StatusResponse {
    SUCCESS, ERROR
}
