package com.ftpmanager.backend.model;

import lombok.Data;

@Data
public class FTPConnectionRequest {
	private String host;
	private int port;
	private String username;
	private String password;
}
