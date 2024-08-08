package com.marcondes.springsecurity.controllers;

import com.marcondes.springsecurity.controllers.dto.CreateTweetDto;
import com.marcondes.springsecurity.controllers.dto.FeedDto;
import com.marcondes.springsecurity.controllers.dto.FeedItemDto;
import com.marcondes.springsecurity.entities.Role;
import com.marcondes.springsecurity.entities.Tweet;
import com.marcondes.springsecurity.repositories.TweetRepository;
import com.marcondes.springsecurity.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetController(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/feed")
    public ResponseEntity<FeedDto> feed(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){


        var tweets = tweetRepository.findAll(
                PageRequest.of(page,pageSize, Sort.Direction.DESC, "creationTimeStamp"))
                .map(tweet -> new FeedItemDto(tweet.getTweetId(),tweet.getContent(), tweet.getUser().getUsername()));


        return ResponseEntity.ok(
                new FeedDto(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getNumberOfElements()));
    }




    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();

        user.ifPresent(tweet::setUser);
        tweet.setContent(dto.content());

        tweetRepository.save(tweet);

        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/tweets/{id}")
    private ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));
        var tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        //valida se é um admin
        var isAmdin = false;
        if (user.isPresent()){
            isAmdin = user.get().getRoles().stream().anyMatch(role -> role.getName()
                    .equalsIgnoreCase(Role.values.ADMIN.name()));
        }

        //valida se é o dono do tweet
        var isTweetOwner = tweet.getUser().getUserId().equals(UUID.fromString(token.getName()));

        if (isAmdin || isTweetOwner) {
            tweetRepository.delete(tweet);
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok().build();
    }
}
