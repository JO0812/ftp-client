package com.ftpmanager.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class FileListResponse {
	private List<FileInfo> files;
	private String currentPath;
}
