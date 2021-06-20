package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AccountDTO;
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
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final AccountCardRepository accountCardRepository;

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
                accountEntity.getAccountCardEntities().add(new AccountCardEntity(accountEntity, card, dto.isFavorite()));
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

    @Transactional
    public AccountDTO register(AccountEntity accountEntity, HttpServletResponse response) {
        AccountDTO accountDTO = null;
        try{
            if(accountRepository.existsByUsername(accountEntity.getUsername())) {
                //return with error
                response.setStatus(409);
                response.sendError(409, "Username already taken.");
                return null;
            }
            //continue with logic
            accountRepository.save(accountEntity);
            accountDTO = new AccountDTO(accountEntity);
        } catch (IOException e) {
            //TODO - Log aspect and/or exception handling aspect
            e.printStackTrace();
        }
        return accountDTO;

    }




}




