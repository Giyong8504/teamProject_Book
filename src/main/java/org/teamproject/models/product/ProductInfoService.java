package org.teamproject.models.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.entities.Books;
import org.teamproject.entities.FileInfo;
import org.teamproject.models.files.FileInfoService;
import org.teamproject.repositories.BooksRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final BooksRepository booksRepository;
    private final FileInfoService fileInfoService;


    /**
     * 책 상품 개별조회
     * @param bookNo
     * @return
     */
    public Books get(Long bookNo) {
        Books books = booksRepository.findById(bookNo).orElseThrow(BookNotFoundException::new);
        addFileInfo(books);

        return books;
    }


    /**
     * 첨부 이미지 추가처리
     * @param books
     */
    public void addFileInfo(Books books){
        String gid = books.getGid();
        List<FileInfo> mainImages = fileInfoService.getListDone(gid, "main");
        List<FileInfo> listImages = fileInfoService.getListDone(gid, "list");
        List<FileInfo> editorImages = fileInfoService.getListDone(gid, "editor");
        books.setMainImages(mainImages);
        books.setListImages(listImages);
        books.setEditorImages(editorImages);
    }
}
