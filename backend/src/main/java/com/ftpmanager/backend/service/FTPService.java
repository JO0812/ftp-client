package com.ftpmanager.backend.service;

import lombok.Getter;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;


@Service
public class FTPService {

	private FTPClient ftpClient;
	private boolean isConnected = false;
	private String currentHost;
	private String currentDirectory;

	@Getter
	private String lastError;

	public boolean connect(String host, int port, String username, String password) {
		try {
			if (ftpClient == null) {
				ftpClient = new FTPClient();
			}
			ftpClient.connect(host, port);
			boolean loginSuccess = ftpClient.login(username, password);

			if (!loginSuccess) {
				lastError = "Login failed: Invalid username or password";
				ftpClient.disconnect();
				return false;
			}

			isConnected = true;
			currentHost = host;
			currentDirectory = ftpClient.printWorkingDirectory();
			lastError = null;
			return true;

		} catch (Exception e) {
			lastError = "Connection failed: " + e.getMessage();
			isConnected = false;
			return false;
		}
	}

	public boolean disconnect() {
		try {
			if (isConnected) {
				ftpClient.disconnect();
				currentHost = null;
				currentDirectory = null;
				isConnected = false;
				lastError = null;
			}
			return true;
		} catch (Exception e) {
			lastError = "Disconnection failed: " + e.getMessage();
			isConnected = false;
			return false; // if we have an error with the connection, should we cleanup as well and return true?
		}
	}

	//public String status()
}

