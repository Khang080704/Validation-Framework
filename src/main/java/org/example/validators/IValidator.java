package org.example.validators;

import java.util.Map;
import java.util.List;

public interface IValidator {
    Map<String, List<String>> validate(Object object);
}
