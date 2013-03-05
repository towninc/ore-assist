package com.aimluck.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PublishTest extends AppEngineTestCase {

    private Publish model = new Publish();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
