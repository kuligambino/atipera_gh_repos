package com.atipera.kulig.task.domain;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class RepositoryServiceTest {

    private RepositoryService repositoryService;
    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() {
        mockWebServer = new MockWebServer();
        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();
        repositoryService = new RepositoryService(webClient);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void whenGetRepoThenReturnAllNotForkedReposWithBranches() throws IOException {
        // given
        enqueueResponse("repository.json");
        enqueueResponse("branch.json");
        enqueueResponse("branch.json");

        // when then
        StepVerifier.create(repositoryService.getRepositories("username", "application/json"))
                .expectNextMatches(repo -> {
                    assertRepo(repo, "repo1", "ownerlogin1",1);
                    assertBranch(repo.branches().get(0), "branch", "asdfasdf");
                    return true;
                })
                .expectNextMatches(repo -> {
                    assertRepo(repo, "repo2", "ownerlogin2",1);
                    assertBranch(repo.branches().get(0), "branch", "asdfasdf");
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void whenGetForkedRepoThenReturnEmptyCollection() throws IOException {
        // given
        enqueueResponse("forkedrepository.json");

        // when then
        StepVerifier.create(repositoryService.getRepositories("username", "application/json"))
                .expectComplete()
                .verify();
    }

    @Test
    void whenGetRepoThenReturnOnlyTypeSource() throws IOException {
        // given
        enqueueResponse("mixedrepository.json");
        enqueueResponse("branch.json");

        // when then
        StepVerifier.create(repositoryService.getRepositories("username", "application/json"))
                .expectNextMatches(repo -> {
                    assertRepo(repo, "repo1", "ownerlogin1",1);
                    assertBranch(repo.branches().get(0), "branch", "asdfasdf");
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void whenUserNameNotFoundThen404() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(NOT_FOUND.value()));

        // when then
        StepVerifier.create(repositoryService.getRepositories("nonexistentuser", "application/json"))
                .expectErrorSatisfies(throwable -> {
                    assertTrue(throwable instanceof WebClientResponseException);
                    assertEquals(NOT_FOUND, ((WebClientResponseException) throwable).getStatusCode());
                })
                .verify();
    }

    @Test
    void whenHeaderIsApplicationXmlThen406() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(NOT_ACCEPTABLE.value()));

        // when then
        StepVerifier.create(repositoryService.getRepositories("username", "application/xml"))
                .expectErrorSatisfies(throwable -> {
                    assertTrue(throwable instanceof WebClientResponseException);
                    assertEquals(NOT_ACCEPTABLE, ((WebClientResponseException) throwable).getStatusCode());
                })
                .verify();
    }

    private void assertRepo(Repository repository, String expectedRepoName, String expectedOwnerLogin, int branchesNr){
        assertThat(repository.name()).isEqualTo(expectedRepoName);
        assertThat(repository.ownerLogin()).isEqualTo(expectedOwnerLogin);
        assertThat(repository.branches()).hasSize(branchesNr);
    }

    private void assertBranch(Branch branch, String expectedBranchName, String expectedLastCommitSha){
        assertThat(branch.name()).isEqualTo(expectedBranchName);
        assertThat(branch.lastCommitSha()).isEqualTo(expectedLastCommitSha);
    }

    private void enqueueResponse(String resourcePath) throws IOException {
        ClassPathResource resource1 = new ClassPathResource(resourcePath);
        byte[] jsonBytes1 = FileCopyUtils.copyToByteArray(resource1.getInputStream());
        String body = new String(jsonBytes1);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader("Content-Type", "application/json")
                .setBody(body));
    }
}
