package com.testus.testus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.testus.testus.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSeq;

    @Column
    private String userName;

    @Column(nullable = false)
    private SocialType providerType;

    @Column(nullable = false)
    private String providerSubject;

    @Column
    private String userEmail;

    @JsonIgnore
    @Column
    private String userPassword;

    @Column
    private char marketingYn;

    @PrePersist
    public void prePersist(){
        this.marketingYn = 'n';
    }


}
