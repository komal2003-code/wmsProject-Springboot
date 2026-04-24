
package com.wms.dto;

public class ApiResponse {

    private String status;
    private String message;
    private int quantity;

    public ApiResponse() {}

    public ApiResponse(String status, String message, int quantity) {
        this.status = status;
        this.message = message;
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}