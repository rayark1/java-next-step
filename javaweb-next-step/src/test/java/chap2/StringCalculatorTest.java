package chap2;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringCalculatorTest {
    private final StringCalculator calculator = new StringCalculator();

    @Test
    void returnsZeroOnEmptyString() {
        assertThat(calculator.add("")).isZero();
    }

    @Test
    void returnsZeroOnNull() {
        assertThat(calculator.add(null)).isZero();
    }

    @Test
    void returnNumberOnSingleNumberInput() {
        assertThat(calculator.add("1")).isEqualTo(1);
    }

    @Test
    void returnSumOnTwoNumbersCommaDelimited() {
        assertThat(calculator.add("1,2")).isEqualTo(3);
    }

    @Test
    void returnSumUsingCustomDelimiter() {
        assertThat(calculator.add("//;\n1;2;3")).isEqualTo(6);
    }

    @Test
    void throwOnNegativeNumber() {
        assertThatThrownBy(() -> calculator.add("-1,2,3"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Negatives not allowed")
                .hasMessageContaining("-1");
    }

    @Test
    void exceptionMessageShouldContainAllNegativeNumbers() {
        assertThatThrownBy(() -> calculator.add("-1,2,-3"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Negatives not allowed")
                .hasMessageContaining("-1")
                .hasMessageContaining("-3");
    }
}
