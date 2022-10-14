package com.valentinpopescu98.testing.template_resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IndexControllerTest {
    private IndexController indexController;

    @BeforeEach
    void setUp() {
        indexController = new IndexController();
    }

    @Test
    void shouldFetchIndexPage() {
        // When
        String index = indexController.getIndex();

        // Then
        assertThat(index).isEqualTo("index");
    }
}