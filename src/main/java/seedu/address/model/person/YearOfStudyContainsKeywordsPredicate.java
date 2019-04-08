package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code YearOfStudy} matches any of the keywords given.
 */

public class YearOfStudyContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public YearOfStudyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.equalsWordIgnoreCase(person.getYearOfStudy().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof YearOfStudyContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((YearOfStudyContainsKeywordsPredicate) other).keywords)); // state check
    }

}
