package com.revature.quizzard.services;

import com.revature.quizzard.dtos.requestmodels.CardConfidentDTO;
import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountCardRepository;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CardService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final AccountCardRepository accountCardRepository;

    /**
     * Takes in a DTO containing the account id, card id, and information about wether or not the user is confident in that card
     * and checks the values inside of it. Runs logic to check if the user has interacted with the card or not
     * then changes the confident column in the junction table to the value of the field in the dto.
     *
     * @param dto contains the account id, card id, and state of correction
     * @return true if the operation is successful
     * @throws InvalidRequestException if the account or card are not present in the optional.
     * @author Richard Taylor
     * @author Nicholas Recino
     */
    public boolean toggleConfidentCard(CardConfidentDTO dto) throws InvalidRequestException {
        Optional<AccountEntity> o_account = accountRepository.findById(dto.getAccountId());
        Optional<CardEntity> o_card = cardRepository.findById(dto.getCardId());
        Optional<AccountCardEntity> accountCardEntity = Optional.empty();
        if (o_card.isPresent() && o_account.isPresent()) {
            AccountEntity accountEntity = o_account.get();
            CardEntity card = o_card.get();
            accountCardEntity = accountEntity.getAccountCardEntities()
                    .stream()
                    .filter(cardToFavorite -> cardToFavorite.getAccountEntity().getId() == dto.getAccountId() &&
                            cardToFavorite.getCardEntity().getId() == dto.getCardId())
                    .findFirst();
            if (!accountCardEntity.isPresent()) {
                accountEntity.getAccountCardEntities().add(new AccountCardEntity(accountEntity, card, null, dto.isConfident()));
                accountRepository.save(accountEntity);
                return true;
            } else {
                accountCardEntity.get().setConfident(dto.isConfident());
                accountCardRepository.save(accountCardEntity.get());
            }
            return true;
        } else {
            throw new InvalidRequestException();
        }
    }

    /**
     * Takes in an account DTO, and checks the values inside of it. Runs logic to check if the user has interacted with the card or not
     * then changes the favorite column in the junction table to the value of the field in the dto.
     *
     * @param dto contains the account id, card id, and state of favorite
     * @return  true if the operation is successful
     * @throws InvalidRequestException if the account or card are not present in the optional.
     * @author Richard Taylor
     * @author Nicholas Recino
     */
    public boolean addFavoriteCard(CardFavoriteDTO dto) throws InvalidRequestException {
        Optional<AccountEntity> o_account = accountRepository.findById(dto.getAccountId());
        Optional<CardEntity> o_card = cardRepository.findById(dto.getCardId());
        Optional<AccountCardEntity> accountCardEntity = Optional.empty();
        if (o_card.isPresent() && o_account.isPresent()) {
            AccountEntity accountEntity = o_account.get();
            CardEntity card = o_card.get();
            accountCardEntity = accountEntity.getAccountCardEntities()
                    .stream()
                    .filter(cardToFavorite -> cardToFavorite.getAccountEntity().getId() == dto.getAccountId() &&
                            cardToFavorite.getCardEntity().getId() == dto.getCardId())
                    .findFirst();
            if (!accountCardEntity.isPresent()) {
                accountEntity.getAccountCardEntities().add(new AccountCardEntity(accountEntity, card, dto.isFavorite(), null));
                accountRepository.save(accountEntity);
                return true;
            } else {
                accountCardEntity.get().setFavorite(dto.isFavorite());
                accountCardRepository.save(accountCardEntity.get());
            }
            return true;
        } else {
            throw new InvalidRequestException();
        }
    }
}
