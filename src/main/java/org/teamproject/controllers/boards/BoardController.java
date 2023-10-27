package org.teamproject.controllers.boards;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.teamproject.commons.CommonException;
import org.teamproject.commons.MemberUtil;
import org.teamproject.entities.Board;
import org.teamproject.entities.Member;
import org.teamproject.models.board.config.BoardConfigInfoService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardConfigInfoService boardConfigInfoService;
   // private final HttpServletResponse response;
      private  final MemberUtil memberUtil;
    // 게시글 목록
    @GetMapping("/list/{bId}")
    public String list(@PathVariable String bId, Model model) {
        commonProcess(bId, "list", model);

        return "board/list";
    }

    // 게시글 작성
    @GetMapping("/write/{bId}")
    public String write(@PathVariable String bId, Model model) {
        commonProcess(bId, "write", model);

        return "board/write";
    }

    // 게시글 수정
    @GetMapping("/{id}/update")
    public String update(@PathVariable Long id, Model model) {
        commonProcess(null, "update", model);

        return "board/update";
    }

    @PostMapping("/save") // get post
    public String save(Model model) {
        commonProcess(null, "list", model);
         return "board/save";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        commonProcess(null, "view", model);
        return "board/view";
    }

    private void commonProcess(String bId, String action, Model model) {
        /**
         * 1. bId 게시판 설정 조회
         * 2. action - write, update : 공통 스크립트, 공통 CSS
         *           - 에디터 사용 -> 에디터 스크립트 추가
         *           - 에디터 미사용 -> 에디터 스크립트 미추가
         *           - write, list, view 0> 권한 체크
         *           - update - 본인의 게시글만 수정 가능
         *                    - 회원 : 회원 번호
         *                    - 비회원 : 비회원 비밀번호
         *                    - 관리자는 모두 가능
         */

        Board board = boardConfigInfoService.get(bId, action);
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        // 공통 스타일 CSS
        addCss.add("board/style");
        addCss.add(String.format("board/%s_style", board.getSkin()));

        // 글 작성, 수정 시 필요한 자바스크립트
        if (action.equals("write") || action.equals("update")) {
            if(board.isUseEditor()) { // 에디터를 사용하는 경우
                addScript.add("ckeditor/ckeditor");
            }
            addScript.add("board/form");
        }
        
        // 공통 필요 속성 추가
        model.addAttribute("board", board); // 게시판 설정
        model.addAttribute("addCss", addCss); // CSS 설정
        model.addAttribute("addScript", addScript); // JS 설정
    }

    @ExceptionHandler(CommonException.class)
    public String errorHandler(CommonException e, Model model, HttpServletResponse response) {
        e.printStackTrace();

        String message = e.getMessage();
        HttpStatus status = e.getStatus();
        response.setStatus(status.value());

        String script = String.format("alert('%s');history.back();", message);
        model.addAttribute("script", script);
        return "common/_execute_script";
    }
}
