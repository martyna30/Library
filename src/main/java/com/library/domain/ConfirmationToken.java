package com.library.domain;

import com.library.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CONFIRMATION_TOKEN")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOKEN_ID", unique = true)
    private Long id;
    @Column(name = "TOKEN")
    private String token;

    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;   //LocalDataTime?

    @ManyToOne
    @JoinColumn(nullable = false, name = "USER_ID")
    private User user;


    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,
                             User user) {
            this.token = token;
            this.createdAt = createdAt;
            this.expiresAt = expiresAt;
            this.user =  user;
    }


}





