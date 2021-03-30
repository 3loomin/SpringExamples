package com.api.board.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.api.board.domain.Board;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
class BoardServiceImplTest {

	Logger logger = LoggerFactory.getLogger(BoardServiceImplTest.class);
	 
    @Autowired
    BoardServiceImpl boardServiceImpl;
 
    @Test
    public void testA() throws Exception { // 게시글 목록 조회 테스트 
 
        try {
 
            List<Board> boardList = boardServiceImpl.getBoardList();
 
            // 조회 결과가 5건일 경우 테스트 통과
            assertEquals(5, boardList.size());
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Test
    public void testB() throws Exception { // 게시글 상세 조회 테스트
 
        try {
 
            Board boardDetail = boardServiceImpl.getBoardDetail(1);
 
            // 1번 게시글이 있고, 제목이 일치하는 경우 테스트 통과
            assertNotNull(boardDetail);
            assertEquals("게시글 제목1", boardDetail.getBoard_subject());
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Test
    @Rollback(true)
    public void testC() throws Exception { // 게시글 등록 테스트
 
        try {
 
            Board boardDetail = boardServiceImpl.getBoardDetail(6);
            assertNull(boardDetail);
 
            Board insertBoard = new Board();
            insertBoard.setBoard_writer("게시글 작성자");
            insertBoard.setBoard_subject("게시글 제목 등록");
            insertBoard.setBoard_content("게시글 제목 등록");
            boardServiceImpl.insertBoard(insertBoard);
 
            int insertBoardSeq = insertBoard.getBoard_seq();                    
            logger.info("insertBoardSeq:[{}]", insertBoardSeq);
 
            boardDetail = boardServiceImpl.getBoardDetail(insertBoardSeq);
            
            // 게시글이 있고, 제목이 일치하는 경우 테스트 통과
            assertNotNull(boardDetail);
            assertEquals("게시글 제목 등록", boardDetail.getBoard_subject());
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Test
    @Rollback(true)
    public void testD() throws Exception { // 게시글 수정 테스트
 
        try {
            
            Board insertBoard = new Board();
            insertBoard.setBoard_writer("게시글 작성자");
            insertBoard.setBoard_subject("게시글 제목 등록");
            insertBoard.setBoard_content("게시글 제목 등록");
            boardServiceImpl.insertBoard(insertBoard);
 
            int insertBoardSeq = insertBoard.getBoard_seq();            
            logger.info("insertBoardSeq:[{}]", insertBoardSeq);
            
            Board updateBoard = new Board();
            updateBoard.setBoard_seq(insertBoardSeq);
            updateBoard.setBoard_subject("게시글 제목 수정");
            updateBoard.setBoard_content("게시글 제목 수정");
            boardServiceImpl.updateBoard(updateBoard);
            
            Board boardDetail = boardServiceImpl.getBoardDetail(insertBoardSeq);
            assertNotNull(boardDetail);
 
            // 등록한 게시글 제목이 수정되었으면 테스트 통과
            assertEquals("게시글 제목 수정", boardDetail.getBoard_subject());
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Test
    @Rollback(true)
    public void testE() throws Exception { // 게시글 삭제 테스트
 
        try {
 
            Board insertBoard = new Board();
            insertBoard.setBoard_writer("게시글 작성자");
            insertBoard.setBoard_subject("게시글 제목 등록");
            insertBoard.setBoard_content("게시글 제목 등록");
            boardServiceImpl.insertBoard(insertBoard);
 
            int insertBoardSeq = insertBoard.getBoard_seq();            
            logger.info("insertBoardSeq:[{}]", insertBoardSeq);
 
            boardServiceImpl.deleteBoard(insertBoardSeq);
            
            Board boardDetail = boardServiceImpl.getBoardDetail(insertBoardSeq);
 
            // 삭제한 게시글이 조회되지 않으면 테스트 통과
            assertNull(boardDetail);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
