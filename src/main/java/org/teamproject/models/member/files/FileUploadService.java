package org.teamproject.models.member.files;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.teamproject.entities.FileInfo;
import org.teamproject.repositories.FileInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService { // 파일정보를 저장하고 업로드 처리 방법을 사용


    private final FileInfoRepository repository;
    private final FileInfoService infoService;

    // 업로드 시 성공한 파일을 반환, 여러개를 함께 올리기 위해 배열 사용.
    public List<FileInfo> upload(MultipartFile[] files, String gid, String location) {

        // 삼항연산자로 gid가 없으면 java util 패키지의 UUID로 랜덤성, 있으면 gid.
        gid = gid == null || gid.isBlank() ? UUID.randomUUID().toString() : gid;

        List<MultipartFile> uploadedFiles = new ArrayList<>();
        for(MultipartFile file : files) {
            String fileType = file.getContentType();
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf("."),-1);

            // 파일 정보를 저장.
            FileInfo item = FileInfo.builder()
                    .fileName(fileName)
                    .fileType(fileType)
                    .extension(extension)
                    .location(location)
                    .gid(gid)
                    .build();

            repository.saveAndFlush(item);

            // 파일 서버 접속 URL, 경로 ,정보 등.
            infoService.addFileInfo(item); // 파일 저장 처리 완료.


            /** 파일 업로드 처리 작업예정. */
        }



        return null;
    }



    //
    public List<FileInfo> upload(MultipartFile[] files, String gid) {
        return upload(files, gid, null);
    }

    //gid 없는경우 자동생성
    private List<FileInfo> upload(MultipartFile[] files) {
        return upload(files, null);
    }
}