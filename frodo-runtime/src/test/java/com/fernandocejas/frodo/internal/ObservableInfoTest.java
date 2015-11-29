package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ObservableInfoTest {

  private static final String OBSERVABLE_STREAM_VALUE = "fernando";

  private ObservableInfo observableInfo;

  private TestJoinPoint testJoinPoint;
  private FrodoJoinPoint frodoJoinPoint;

  @Before
  public void setUp() {
    testJoinPoint = new TestJoinPoint.Builder(this.getClass())
        .withReturnType(Observable.class)
        .withReturnValue(OBSERVABLE_STREAM_VALUE)
        .build();
    frodoJoinPoint = new FrodoJoinPoint(testJoinPoint);
    observableInfo = new ObservableInfo(frodoJoinPoint);
  }

  @Test
  public void shouldReturnAbsentValues() {
    Optional<String> optionalSubscribeOnThread = observableInfo.getSubscribeOnThread();
    Optional<String> optionalObserveOnThread = observableInfo.getObserveOnThread();
    Optional<Long> optionalTotalExecutionTime = observableInfo.getTotalExecutionTime();
    Optional<Integer> optionalTotalEmittedItems = observableInfo.getTotalEmittedItems();

    assertThat(optionalSubscribeOnThread.isPresent()).isFalse();
    assertThat(optionalObserveOnThread.isPresent()).isFalse();
    assertThat(optionalTotalExecutionTime.isPresent()).isFalse();
    assertThat(optionalTotalEmittedItems.isPresent()).isFalse();
  }

  @Test
  public void shouldReturnPresentValues() {
    observableInfo.setSubscribeOnThread("thread");
    observableInfo.setObserveOnThread("thread");
    observableInfo.setTotalExecutionTime(1000);
    observableInfo.setTotalEmittedItems(5);

    Optional<String> optionalSubscribeOnThread = observableInfo.getSubscribeOnThread();
    Optional<String> optionalObserveOnThread = observableInfo.getObserveOnThread();
    Optional<Long> optionalTotalExecutionTime = observableInfo.getTotalExecutionTime();
    Optional<Integer> optionalTotalEmittedItems = observableInfo.getTotalEmittedItems();

    assertThat(optionalSubscribeOnThread.isPresent()).isTrue();
    assertThat(optionalObserveOnThread.isPresent()).isTrue();
    assertThat(optionalTotalExecutionTime.isPresent()).isTrue();
    assertThat(optionalTotalEmittedItems.isPresent()).isTrue();
  }
}