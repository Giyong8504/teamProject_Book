package org.teamproject.models.member.files;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.teamproject.entities.FileInfo;
import org.teamproject.repositories.FileInfoRepository;

import java.io.File;
<<<<<<< HEAD
import java.util.Arrays;
=======
>>>>>>> test1
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
        List<FileInfo> items = repository.getFiles(opts.getGid(), opts.getLocation(), opts.getMode().name());

        items.stream().forEach(this::addFileInfo);

        return items;
    }

    // 전체 조회
    public List<FileInfo> getListAll(String gid, String location) {
        Options opts = Options.builder()
                .gid(gid)
                .location(location)
                .mode(SearchMode.ALL)
                .build();

        return getList(opts);
    }

    public List<FileInfo> getListAll(String gid) {
        return getListAll(gid, null);
    }

    // 완료된 파일만 조회(gid,location)
    public List<FileInfo> getListDone(String gid, String location) {
        Options opts = Options.builder()
                .gid(gid)
                .location(location)
                .mode(SearchMode.DONE)
                .build();

        return getList(opts);
    }

    // 완료된 파일만 조회(gid)
    public List<FileInfo> getListDone(String gid) {
        return getListDone(gid, null);
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
        String filePath = uploadPath + folder + "/" + fileName;

        // 파일 서버 접속 URL (fileUrl)
        String fileUrl = request.getContextPath() + uploadUrl + folder + "/" + fileName;

<<<<<<< HEAD
        //썸네일 경로 (thumbsPath)
        String thumbPath = getUploadThumbPath() + folder;
=======
        // 썸네일 경로 (thumbsPath)
        String thumbPath = getUploadThumbPath() +  folder;
>>>>>>> test1
        File thumbDir = new File(thumbPath);
        if (!thumbDir.exists()) {
            thumbDir.mkdirs();
        }
<<<<<<< HEAD
        // _1.png 포함되어 있으면 가져오기
        String[] thumbsPath = thumbDir.list((dir, name) -> name.indexOf("_" + fileName) != -1);

        // 썸네일 URL(thumbsUrl)
        String[] thumbsUrl = Arrays.stream(thumbsPath)
                .map(s -> s.replace(uploadPath, request.getContextPath() + uploadUrl))
                .toArray(String[]::new);

        item.setFilePath(filePath);
        item.setFileUrl(fileUrl);
        item.setThumbsPath(thumbsPath);
        item.setThumbsUrl(thumbsUrl);
    }

    private String getUploadThumbPath() {
=======

        //
        String[] thumbsPath = thumbDir.list((dir, name) -> name.indexOf("_" + fileName) != -1);
    }

    private String getUploadThumbPath() { // thumbs/folder/가로_세로_파일명
>>>>>>> test1
        return uploadPath + "thumbs/";
    }

    private String getUploadThumbUrl() {
        return uploadUrl + "thumbs/";
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
