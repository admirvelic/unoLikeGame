package com.vella.unoLikeGame.model.user;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SeeUsersResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private Integer noOfCards;

    public SeeUsersResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public SeeUsersResponse setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public SeeUsersResponse setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public SeeUsersResponse setNoOfCards(Integer noOfCards) {
        this.noOfCards = noOfCards;
        return this;
    }
}
