package com.green.greengramver2.feed.comment;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.comment.model.FeedCommentDelReq;
import com.green.greengramver2.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comment.model.FeedCommentGetRes;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("feed/comment")
public class FeedCommentController {
    private final FeedCommentService service;

    @PostMapping
    public ResultResponse<Long> postFeedComment(@RequestBody FeedCommentPostReq p) {
        long result = service.postFeedComment(p);
        return ResultResponse.<Long>builder()
                .resultMessage("댓글 등록")
                .resultData(result)
                .build();
    }

    @GetMapping
    public ResultResponse<FeedCommentGetRes> getFeedComment(@ParameterObject @ModelAttribute FeedCommentGetReq p) {
//      FeedCommentGetReq p = new FeedCommentGetReq();
//
//      p.setPage(page);
//      p.setFeedId(feedId);

      FeedCommentGetRes res = service.getFeedComment(p);
      return ResultResponse.<FeedCommentGetRes>builder()
              .resultMessage(String.format("%d rows", res.getCommentList().size()))
              .resultData(res)
              .build();
    }

    @GetMapping("/ver2")
    public ResultResponse<FeedCommentGetRes> getFeedComment2(@RequestParam("feed_id") long feedId, @RequestParam("start_idx") int startIdx, int size) {
        FeedCommentGetReq p = new FeedCommentGetReq(feedId, startIdx, size);
        FeedCommentGetRes res = service.getFeedComment(p);

        return ResultResponse.<FeedCommentGetRes>builder()
                .resultMessage(String.format("%d rows", res.getCommentList().size()))
                .resultData(res)
                .build();
    }

    @DeleteMapping
    //feed_comment_id, signed_user_id
    //FE - data 전달방식 : Query-String
    public ResultResponse<Integer> delFeedComment (@ParameterObject @ModelAttribute FeedCommentDelReq p) {
        log.info("FeedCommentController > delFeedComment > p: {}", p);
        int result = service.delFeedComment(p);
        String message = "삭제에 성공했습니다.";
        if (result == 0) {
            message = "삭제에 실패했습니다.";
        }
        return ResultResponse.<Integer>builder()
                .resultMessage(message)
                .resultData(result)
                .build();
    }
}