package org.teamproject.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.FileInfo;
import org.teamproject.entities.QFileInfo;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>, QuerydslPredicateExecutor {

    /**
     *
     * @param gid
     * @param location
     * @param mode all : 완료, 미완료 파일 모두 조회, done : 완료 파일, undone: 미완료 파일
     * @return
     */

    //gid, location 으로 조회하는 부분.
    default List<FileInfo> getFiles(String gid, String location, String mode) {
        QFileInfo fileInfo = QFileInfo.fileInfo;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(fileInfo.gid.eq(gid));

        if (location != null && !location.isBlank()) {
            builder.and(fileInfo.location.eq(location));
        }

        //mode 가 완료 파일 이거나 미완료 파일일 경우
        if (mode.equals("done")) builder.and(fileInfo.done.eq(true));
        else if (mode.equals("undone")) builder.and(fileInfo.done.eq(false));

        List<FileInfo> items = (List<FileInfo>)findAll(builder, Sort.by(Order.asc("regDt")));

        return items;
    }

    // 완료, 미완료 파일 모두 조회.
    default List<FileInfo> getFiles(String gid, String location) {
        return getFiles(gid, location, "all");
    }

    // gid 만 가지고 조회
    default List<FileInfo> getFiles(String gid) {
        return getFiles(gid, null);
    }

    // 업로드 완료된 이후(Done이 붙은것으로 조회) 파일만 조회
    default List<FileInfo> getFilesDone(String gid, String location) {
        return getFiles(gid, location, "done");
    }

    default List<FileInfo> getFilesDone(String gid) {
        return getFiles(gid, null);
    }

    // 작업완료 처리
    default void processDone(String gid) {
        List<FileInfo> items = getFiles(gid);
        items.stream().forEach(item -> {
            item.setDone(true);
        });

        flush();
    }

}
