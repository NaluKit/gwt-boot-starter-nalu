package com.github.nalukit.bootstarternalu.client.ui.content.composite.project.validation;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import org.dominokit.domino.ui.forms.FormElement;
import org.dominokit.domino.ui.forms.validations.ValidationResult;
import org.dominokit.domino.ui.utils.HasValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class ClassNameValidator
    implements HasValidation.Validator {
  
  private List<String> reservedKeyWords;
  private RegExp       regExp;
  private FormElement  element;
  
  public ClassNameValidator(FormElement element) {
    this.element = element;
    
    this.regExp = RegExp.compile("^[A-Za-z_$]+[a-zA-Z0-9_$]*$");
    
    this.reservedKeyWords = new ArrayList<>();
    reservedKeyWords.add("abstract");
    reservedKeyWords.add("assert");
    reservedKeyWords.add("boolean");
    reservedKeyWords.add("break");
    reservedKeyWords.add("byte");
    reservedKeyWords.add("case");
    reservedKeyWords.add("catch");
    reservedKeyWords.add("char");
    reservedKeyWords.add("class");
    reservedKeyWords.add("const");
    reservedKeyWords.add("continue");
    reservedKeyWords.add("default");
    reservedKeyWords.add("do");
    reservedKeyWords.add("double");
    reservedKeyWords.add("else");
    reservedKeyWords.add("enum");
    reservedKeyWords.add("extends");
    reservedKeyWords.add("false");
    reservedKeyWords.add("final");
    reservedKeyWords.add("finally");
    reservedKeyWords.add("float");
    reservedKeyWords.add("for");
    reservedKeyWords.add("if");
    reservedKeyWords.add("goto");
    reservedKeyWords.add("implements");
    reservedKeyWords.add("import");
    reservedKeyWords.add("instanceof");
    reservedKeyWords.add("int");
    reservedKeyWords.add("interface");
    reservedKeyWords.add("long");
    reservedKeyWords.add("native");
    reservedKeyWords.add("new");
    reservedKeyWords.add("null");
    reservedKeyWords.add("package");
    reservedKeyWords.add("private");
    reservedKeyWords.add("protected");
    reservedKeyWords.add("public");
    reservedKeyWords.add("return");
    reservedKeyWords.add("short");
    reservedKeyWords.add("static");
    reservedKeyWords.add("strictfp");
    reservedKeyWords.add("super");
    reservedKeyWords.add("switch");
    reservedKeyWords.add("synchronized");
    reservedKeyWords.add("this");
    reservedKeyWords.add("throw");
    reservedKeyWords.add("throws");
    reservedKeyWords.add("transient");
    reservedKeyWords.add("true");
    reservedKeyWords.add("try");
    reservedKeyWords.add("void");
    reservedKeyWords.add("volatile");
    reservedKeyWords.add("while");
  }
  
  @Override
  public ValidationResult isValid() {
    String      value   = (String) element.getValue();
    MatchResult matcher = regExp.exec(value);
    if (Objects.isNull(matcher)) {
      return ValidationResult.invalid("This is not a valid class name");
    } else {
      Optional<String> optional = Stream.of(value.split("[.]"))
                                        .filter(s -> reservedKeyWords.contains(s))
                                        .findFirst();
      if (optional.isPresent()) {
        return ValidationResult.invalid("Do not use Java reserved words");
      }
    }
    return ValidationResult.valid();
  }
  
}
