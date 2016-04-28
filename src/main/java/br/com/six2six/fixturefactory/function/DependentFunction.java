package br.com.six2six.fixturefactory.function;

import br.com.six2six.fixturefactory.Property;

import java.util.Set;

public interface DependentFunction extends Function {

    <T> T generateValue(Set<Property> properties);

}
