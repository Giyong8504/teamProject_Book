package org.teamproject.models.member.files;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.teamproject.entities.FileInfo;
import org.teamproject.repositories.FileInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileInfoService { // 파일 개별조회 목록조회 기능.

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("file.upload.url")
    private String uploadUrl;

    private final HttpServletRequest request;

    private final FileInfoRepository repository;

    // 파일 등록 번호를 사용한 개별 조회
    public FileInfo get(Long id) {

        // 파일을 못찾을 시 예외 던짐.
        FileInfo item = repository.findById(id).orElseThrow(FileNotFoundException::new);

        addFileInfo(item);

        return item;

    }

    public List<FileInfo> getList(Options opts) {


        return null;
    }

    /**
     * 파일 업로드 서버 경로(filePath)
     * 파일 서버 접속 URL (fileUrl)
     * 썸네일 경로 (thumbsPath), 썸네일 URL(thumbsUrl)
     * */
    public void addFileInfo(FileInfo item) {
        long id = item.getId();
        String extension = item.getExtension();

        // 확장자 있을 경우 .확장자 , 없을경우 Id만.
        String fileName = extension == null || extension.isBlank() ? "" + id : id + "." + extension;
        long folder = id % 10L; // 각각 폴더 경로

        // 파일 업로드 서버 경로
        String filePath = uploadPath + "/" + folder + "/" + fileName;

        // 파일 서버 접속 URL (fileUrl)
        String fileUrl = request.getContextPath() + uploadUrl + folder + "/" + fileName;
    }

    @Data
    @Builder
    static class Options { // 여기서만 사용하므로 내부클래스 작성.
        private String gid;
        private String location;
        private SearchMode mode = SearchMode.ALL;
    }

    static enum SearchMode {
        ALL,
        DONE,
        UNDONE
    }
}
