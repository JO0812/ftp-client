package com.ftpmanager.backend.model;

import lombok.Data;

@Data
public class FTPConnectionInfo {
	private String host;
	private int port;
	private String username;
	private String password;
}
