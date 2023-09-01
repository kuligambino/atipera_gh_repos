package com.atipera.kulig.task.domain;

import com.atipera.kulig.task.dto.BranchDto;
import com.atipera.kulig.task.dto.RepositoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RepositoryService {
    private final WebClient webClient;

    public Flux<Repository> getRepositories(String userName, String acceptHeader) {
        return this.webClient.get()
                .uri("/users/{userName}/repos", userName).header("Accept", acceptHeader)
                .retrieve()
                .onStatus(status -> status.isSameCodeAs(NOT_FOUND), ClientResponse::createException)
                .bodyToFlux(RepositoryDto.class)
                .filter(RepositoryDto::isNotFork)
                .flatMap(repository -> this.webClient.get()
                        .uri("/repos/{userName}/{repositoryName}/branches", userName, repository.getName())
                        .retrieve()
                        .bodyToFlux(BranchDto.class)
                        .collectList()
                        .flatMap(branches -> Mono.just(new Repository(repository.getName(), repository.getOwnerLogin(), branches.stream().map(Branch::of).toList()))));
    }
}