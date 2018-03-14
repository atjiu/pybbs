package co.yiiu.module.score.repository;

import co.yiiu.module.score.model.ScoreLog;
import co.yiiu.module.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Repository
public interface ScoreLogRepository extends JpaRepository<ScoreLog, Integer> {

  Page<ScoreLog> findByUser(User user, Pageable pageable);

}
