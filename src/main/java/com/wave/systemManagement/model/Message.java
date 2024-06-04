package com.wave.systemManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    private String content;

    private LocalDate createdAt;

    @ManyToOne
    private Chat chat;

    @ManyToOne
    private User sender;

}
