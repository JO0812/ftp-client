package com.ftpmanager.backend.model;

import lombok.Data;

@Data
public class FileInfo {
	private String name;
	private long size;
	private boolean isDirectory;
	private String lastModified;
}
