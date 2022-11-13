package lotto.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningLottoTest {

    @Test
    @DisplayName("보너스 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    void validateBonusDuplication() {
        assertThatThrownBy(() -> new WinningLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)),1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(Lotto.DUPLICATION_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("보너스 번호가 1~45사이 번호아닌 경우 예외가 발생한다.")
    void validateBonusNumberScope() {
        assertThatThrownBy(() -> new WinningLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)),50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(Lotto.SCOPE_ERROR_MESSAGE);
    }

    @DisplayName("로또, 당첨 로또 비교해서 일치하는 번호 개수 출력된다.")
    @ParameterizedTest
    @CsvSource({"1, 2, 3, 4, 5, 6, 7, 6, false",
            "1, 2, 3, 4, 5, 40, 7, 5, false",
            "1, 11, 3, 33, 5, 45, 7, 3, false",
            "2, 3, 4, 5, 6, 7, 1, 6, true",
            "1, 2, 3, 4, 5, 7, 6, 6, true"})
    void countMatchingNumbers(int winning1, int winning2, int winning3, int winning4, int winning5, int winning6, int bonus, int countMatch, boolean isMatchBonus) {
        Lotto lotto = new Lotto(List.of(6, 5, 4, 3, 2, 1));
        WinningLotto winningLotto = new WinningLotto(new Lotto(List.of(winning1, winning2, winning3, winning4, winning5, winning6)), bonus);
        int count = winningLotto.countMatchingNumbers(lotto);
        boolean containsBonus = winningLotto.containsBonusNumber(lotto);
        Assertions.assertThat(count).isEqualTo(countMatch);
        Assertions.assertThat(containsBonus).isEqualTo(isMatchBonus);
    }

}