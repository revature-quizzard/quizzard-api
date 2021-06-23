package com.revature.quizzard.dtos;

import com.revature.quizzard.models.flashcards.CardEntity;

public class CardDTO {
    private int subject_id;
    private String question;
    private String answer;
    private boolean reviewable;
    private boolean isPublic;
    private int id;
    private int account_id;

    public CardDTO(int subject_id, String question, String answer, boolean reviewable, boolean isPublic, int id)
    {
        this.subject_id = subject_id;
        this.question = question;
        this.answer = answer;
        this.reviewable = reviewable;
        this.isPublic = isPublic;
        this.id = id;
    }

    public int getAccount_id()
    {
        return account_id;
    }

    public void setAccount_id(int account_id)
    {
        this.account_id = account_id;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getSubject_id()
    {
        return subject_id;
    }

    public void setSubject_id(int subject_id)
    {
        this.subject_id = subject_id;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public boolean isReviewable()
    {
        return reviewable;
    }

    public void setReviewable(boolean reviewable)
    {
        this.reviewable = reviewable;
    }

    public boolean isPublic()
    {
        return isPublic;
    }

    public void setPublic(boolean aPublic)
    {
        isPublic = aPublic;
    }
}
