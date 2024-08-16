package JejuDorang.JejuDorang.view.service;

import JejuDorang.JejuDorang.diary.data.Diary;
import JejuDorang.JejuDorang.diary.repository.DiaryRepository;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.view.data.View;
import JejuDorang.JejuDorang.view.repository.ViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ViewService {

    private final DiaryRepository diaryRepository;
    private final ViewRepository viewRepository;

    // 유저가 일기 봤음을 기록
    public void createView(Long diaryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 일기입니다 : " + diaryId));

        View view = View.builder()
                .diary(diary)
                .member(member)
                .build();
        viewRepository.save(view);
    }

    // 유저가 특정 일기를 봤는지 여부
    public boolean alreadyView(Long diaryId, Long memberId) {

        boolean alreadyView = viewRepository.existsByDiaryIdAndMemberId(diaryId, memberId);
        return alreadyView;
    }
}
