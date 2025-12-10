package org.example.concrete.regex;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexValidator implements IValidator<String> {
    private Pattern pattern;

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public RegexValidator(String regex) throws PatternSyntaxException {
        try {
            this.pattern = Pattern.compile(regex);
        }
        catch (PatternSyntaxException e) {
            throw new PatternSyntaxException("Invalid regex" + e.getDescription(), e.getPattern(), e.getIndex());
        }
    }

    @Override
    public ValidatorResult validate(String object) {
        Matcher matcher = pattern.matcher(object);
        if(matcher.matches()) {
            return ValidatorResult.valid();
        }
        return ValidatorResult.invalid(object + " is not a valid format");
    }
}
