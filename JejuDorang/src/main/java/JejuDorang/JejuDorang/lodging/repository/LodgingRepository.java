package JejuDorang.JejuDorang.lodging.repository;

import JejuDorang.JejuDorang.member.data.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JejuDorang.JejuDorang.lodging.data.Lodging;

@Repository
public interface LodgingRepository extends JpaRepository<Lodging, Long>, LodgingCustomRepository {

    Lodging findByMember(Member member);
}
