/*
* Copyright (c) 2015 - present Facebook, Inc.
* All rights reserved.
*
* This source code is licensed under the BSD style license found in the
* LICENSE file in the root directory of this source tree. An additional grant
* of patent rights can be found in the PATENTS file in the same directory.
*/

package endtoend.cpp;

import static org.hamcrest.MatcherAssert.assertThat;
import static utils.matchers.ResultContainsExactly.containsExactly;

import com.google.common.collect.ImmutableList;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;

import utils.DebuggableTemporaryFolder;
import utils.InferException;
import utils.InferResults;
import utils.InferRunner;

public class ThisNotNullTest {

  public static final String FILE =
      "infer/tests/codetoanalyze/cpp/errors/npe/this_not_null.cpp";

  private static ImmutableList<String> inferCmd;

  public static final String NULL_DEREFERENCE = "NULL_DEREFERENCE";

  @ClassRule
  public static DebuggableTemporaryFolder folder =
      new DebuggableTemporaryFolder();

  @BeforeClass
  public static void runInfer() throws InterruptedException, IOException {
    inferCmd = InferRunner.createCPPInferCommand(folder, FILE);
  }

  @Test
  public void whenInferRunsOnMethodNoThisNullDerefErrorIsFound()
      throws InterruptedException, IOException, InferException {
    InferResults inferResults = InferRunner.runInferCPP(inferCmd);
    String[] proceduresWithNullDeref = {
      /* nothing should be reported */
    };
    assertThat(
        "Results should contain the no null dereference",
        inferResults,
        containsExactly(
            NULL_DEREFERENCE,
            FILE,
            proceduresWithNullDeref
        )
    );
  }
}
