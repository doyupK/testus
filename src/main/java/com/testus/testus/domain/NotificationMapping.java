package com.testus.testus.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 알림을 읽었는지 체크하는 매핑 테이블 ( Notice -< this >- User )
 */
@Entity
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class NotificationMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_seq")
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

}
