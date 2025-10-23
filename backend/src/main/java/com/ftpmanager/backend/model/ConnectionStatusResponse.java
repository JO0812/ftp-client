package com.ftpmanager.backend.model;

import lombok.Data;

@Data
public class ConnectionStatusResponse {
	private boolean connected;
	private String host;
	private String currentDirectory;
}
