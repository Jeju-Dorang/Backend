package JejuDorang.JejuDorang.tag.service;

import JejuDorang.JejuDorang.tag.data.Tag;
import JejuDorang.JejuDorang.tag.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.constant.ConstantDescs.NULL;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    // DB에 있으면 찾아서 반환, 없으면 저장
    public Tag saveTag(String name) {
        Tag tag = tagRepository.findByName(name);
        if (tag == NULL) {
            Tag newTag = new Tag(name);
            tagRepository.save(newTag);
            return newTag;
        }
        return tag;
    }
}
