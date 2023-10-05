package org.teamproject.models.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.entities.Books;
import org.teamproject.entities.FileInfo;
import org.teamproject.entities.product.Category;
import org.teamproject.models.files.FileInfoService;
import org.teamproject.repositories.BooksRepository;
import org.teamproject.repositories.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryInfoService {
    private final CategoryRepository categoryRepository;
    private final BooksRepository booksRepository;
    private final FileInfoService fileInfoService;

    /* 개별 조회 */
    public Books get(Long bookNo) {
        Books book = booksRepository.findById(bookNo).orElseThrow(BookNotFoundException::new);
        addFileInfo(book);

        return book;
    }

    public List<Category> getAll(String mode) {
        return categoryRepository.getAll(mode);
    }


    public void addFileInfo(Books book) {
        String gid = book.getGid();
        List<FileInfo> mainImages = fileInfoService.getListDone(gid, "main");
        List<FileInfo> listImages = fileInfoService.getListDone(gid, "list");
        List<FileInfo> editorImages = fileInfoService.getListDone(gid, "editor");
        book.setMainImages(mainImages);
        book.setListImages(listImages);
        book.setEditorImages(editorImages);
        System.out.println("여기?");
        System.out.println(book);
    }

}
