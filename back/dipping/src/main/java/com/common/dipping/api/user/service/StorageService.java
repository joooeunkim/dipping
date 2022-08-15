package com.common.dipping.api.user.service;

import com.common.dipping.api.board.domain.dto.BoardDto;
import com.common.dipping.api.board.domain.dto.ProfilePostDto;
import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.BoardSong;
import com.common.dipping.api.board.repository.BoardRepository;
import com.common.dipping.api.board.repository.BoardSongRepository;
import com.common.dipping.api.user.domain.entity.Storage;
import com.common.dipping.api.user.domain.entity.StorageId;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.StorageRepository;
import com.common.dipping.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardSongRepository boardSongRepository;

    public void storageBoard(Long userId, Long boardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + boardId));
        StorageId storageId = new StorageId();
        storageId.setUserId(userId);
        storageId.setBoardId(boardId);
        Storage storage = Storage.builder()
                .storageId(storageId)
                .build();
        storageRepository.save(storage);
    }

    public List<ProfilePostDto> getAllStorageByUserId(Long userId) {

        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        List<Storage> storages = storageRepository.findlistByUserId(userId);
        List<ProfilePostDto> list = new ArrayList<>();
        for(Storage s : storages){
            List<BoardSong> boardSongList = boardSongRepository.findBoardSongByBoardId(s.getBoard().getId());
            String songImgUrl = boardSongList.get(0).getSongImgUrl();
            list.add(new ProfilePostDto(s.getBoard().getId(), songImgUrl));
        }
        return list;
    }

    public void getListStorage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        List<Storage> storages = storageRepository.findlistByUserId(userId);
        List<BoardDto> boardDtos = new ArrayList<>();
        if (!storages.isEmpty()) {
            for (Storage s : storages) {
                Board board = boardRepository.findById(s.getBoard().getId()).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + s.getBoard().getId()));
                BoardDto boardDto = new BoardDto();
                boardDto.setId(board.getId());
                boardDto.setContent(board.getContent());
                boardDto.setCreatedAt(board.getCreatedAt().toString());
                boardDto.setUpdatedAt(board.getUpdatedAt().toString());
                boardDto.setOpenPost(board.isOpenPost());
                boardDto.setOpenComment(board.isOpenComment());
                boardDto.setAlbumArt(board.isAlbumArt());

                boardDtos.add(boardDto);
            }

        }
    }

    public void deletStorage(Long userId, Long boardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + boardId));

        StorageId storageId = new StorageId();
        storageId.setUserId(userId);
        storageId.setBoardId(boardId);

        storageRepository.deleteById(storageId);
    }
}
