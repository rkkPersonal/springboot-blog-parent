package org.xr.happy.common.vo;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Steven
 */
@Data
@ToString
public class FileInfo {
    private int chunkNumber;
    private int chunkSize;
    private long currentChunkSize;
    private long totalSize;
    private String identifier;
    private String filename;
    private String relativePath;
    private long totalChunks;
    private MultipartFile file;
}
