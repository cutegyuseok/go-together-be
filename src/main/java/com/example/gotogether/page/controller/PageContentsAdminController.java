package com.example.gotogether.page.controller;

import com.example.gotogether.page.dto.RegionDTO;
import com.example.gotogether.page.service.PageContentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
@Api(tags = {"관리자의 페이지 컨텐츠 관리 서비스"}, description = "인기 지역 추가,수정,삭제, & 배너 추가,수정,삭제")
public class PageContentsAdminController {

    private final PageContentsService pageContentsService;

    @PostMapping("/regions")
    @ApiOperation(value = "인기 지역 추가", notes = "관리자가 인기 여행지역 추가. rate 는 노출 단계(낮을수록 먼저 노출) \n" +
            "code: 201 추가 성공, 400 지역 이름 중복")
    public ResponseEntity<?> addRegion(@RequestBody RegionDTO.RegionReqDTO dto){
        return pageContentsService.addRegion(dto);
    }

    @PutMapping("/regions/{regionId}")
    @ApiOperation(value = "인기 지역 수정", notes = "관리자가 인기 여행지역 수정.\n" +
            "code: 200 수정 성공, 400 지역 이름 중복 또는 잘못된 ID")
    public ResponseEntity<?> updateRegion(@PathVariable Long regionId,@RequestBody RegionDTO.RegionUpdateReqDTO dto){
        return pageContentsService.updateRegion(regionId,dto);
    }

    @DeleteMapping("/regions/{regionId}")
    @ApiOperation(value = "인기 지역 삭제", notes = "관리자가 인기 여행지역 삭제.\n" +
            "code: 200 삭제 성공, 400 잘못된 ID")
    public ResponseEntity<?> deleteRegion(@PathVariable Long regionId){
        return pageContentsService.deleteRegion(regionId);
    }

}
