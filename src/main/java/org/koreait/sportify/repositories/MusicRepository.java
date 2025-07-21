package org.koreait.sportify.repositories;

import org.koreait.sportify.entities.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {

}
