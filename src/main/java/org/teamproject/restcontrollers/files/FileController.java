package org.teamproject.restcontrollers.files;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.teamproject.commons.rests.JSONData;
import org.teamproject.entities.FileInfo;
import org.teamproject.models.files.FileDeleteService;
import org.teamproject.models.files.FileDownloadService;
import org.teamproject.models.files.FileUploadService;

import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadService uploadService;
    private final FileDownloadService downloadService;
    private final FileDeleteService deleteService;

    /* 파일 업로드 처리 */
    @PostMapping("/upload")
    public ResponseEntity<JSONData<List<FileInfo>>> uploadPs(MultipartFile[] files, String gid, String location) {
        List<FileInfo> items = uploadService.upload(files, gid, location);

        JSONData<List<FileInfo>> data = new JSONData<>();
        data.setSuccess(true);
        data.setData(items);

        return ResponseEntity.ok(data);
    }

    /* 파일 다운로드 처리 */
    @RequestMapping("/download/{id}")
    public void download(@PathVariable Long id) {
        downloadService.download(id);
    }

    /* 파일 삭제 처리 */
    @RequestMapping("/delete/{id}")
    public ResponseEntity<JSONData<Long>> delete(@PathVariable Long id) {
        deleteService.delete(id);

        JSONData<Long> data = new JSONData<>();
        data.setSuccess(true);
        data.setData(id);

        return ResponseEntity.ok(data);
    }
}
