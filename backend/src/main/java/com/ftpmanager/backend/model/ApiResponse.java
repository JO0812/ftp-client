package com.ftpmanager.backend.model;

import lombok.Data;

@Data
public class ApiResponse {
	private boolean success;
	private String message;
	private String error;
}
