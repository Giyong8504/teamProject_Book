package org.teamproject.models.files;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.commons.MemberUtil;
import org.teamproject.entities.FileInfo;
import org.teamproject.models.member.UserInfo;
import org.teamproject.repositories.FileInfoRepository;

import java.io.File;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FileDeleteService {
    private final MemberUtil memberUtil;
    private final FileInfoService infoService;
    private final FileInfoRepository repository;

    public void delete(Long id) {
        FileInfo item = infoService.get(id); // id 파이 정보 조회.

        /* 파일 삭제 권한 체크 S */
        //업로드한 사용자와 로그인한 회원이 같은지 확인.관리자 권한 인지 확인.
        String createBy = item.getCreatedBy();
        UserInfo user = memberUtil.getMember();
        if (createBy != null && createBy.isBlank() && !memberUtil.isAdmin()
                && (!memberUtil.isLogin() || memberUtil.isLogin() && user.getEmail().equals(createBy))) {

            throw new ArithmeticException("UnAuthorized.delete.file");
        }

        /* 파일 삭제 권한 체크 E */

        /**
         *
         * 1. 파일 삭제
         * 2. thumbs 삭제
         * 3. 파일 정보삭제
         */

        File file = new File(item.getFilePath());
        if (file.exists()) file.delete(); // 파일 삭제 부분.

        String[] thumbsPath = item.getThumbsPath();
        if (thumbsPath != null && thumbsPath.length > 0) {
            Arrays.stream(thumbsPath).forEach(p -> {
                File thumb = new File(p);
                if (thumb.exists()) thumb.delete();// 썸네일 이미지 삭제.
            });
        }

        repository.delete(item); // 파일정보 삭제
        repository.flush();
    }
}
