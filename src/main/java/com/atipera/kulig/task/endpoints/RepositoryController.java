package com.atipera.kulig.task.endpoints;

import com.atipera.kulig.task.domain.Repository;
import com.atipera.kulig.task.domain.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/repos")
public class RepositoryController {
    private final RepositoryService repositoryService;

    @GetMapping("{userName}")
    ResponseEntity<Flux<Repository>> getAllUserRepositories(@PathVariable String userName, @RequestHeader("Accept") String acceptHeader){
        return ok().body(repositoryService.getRepositories(userName, acceptHeader));
    }
}
