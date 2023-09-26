package org.teamproject.models.files;

import org.springframework.http.HttpStatus;
import org.teamproject.commons.CommonException;

public class FileNotFoundException extends CommonException {
    public FileNotFoundException() { // 파일 없을 시 발생.
        super(bundleError.getString("NotFound.file"), HttpStatus.BAD_REQUEST);
    }
}
