package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.function.AtomicFunction;
import br.com.six2six.fixturefactory.function.DependentFunction;
import br.com.six2six.fixturefactory.function.Function;
import br.com.six2six.fixturefactory.function.RelationFunction;
import br.com.six2six.fixturefactory.function.impl.IdentityFunction;
import br.com.six2six.fixturefactory.processor.Processor;

import java.util.Set;

public class Property {

	private String name;
	
	private Function function;

	private Object value;

	public Property(String name, Function function) {
	    if (name == null) {
	        throw new IllegalArgumentException("name must not be null");
	    }
	    
	    if (function == null) {
            throw new IllegalArgumentException("function must not be null");
        }
	    
		this.name = name;
		this.function = function;
	}

	public Property(String name, Object value) {
	    this(name, new IdentityFunction(value));
	}

	public String getName() {
		return this.name;
	}

	public Object getValue() {
		if(value == null) {
			value = ((AtomicFunction) this.function).generateValue();
		}
		return value;
	}
	
	public Object getValue(Processor processor) {
		if(value == null) {
			value = ((RelationFunction) this.function).generateValue(processor);
		}
		return value;
	}
	
	public Object getValue(Object owner) {
		if(value == null) {
			value = ((RelationFunction) this.function).generateValue(owner);
		}
		return value;
	}
	
	public Object getValue(Object owner, Processor processor) {
		if(value == null) {
			value = ((RelationFunction) this.function).generateValue(owner, processor);
		}
		return value;
	}

	public Object getValue(Set<Property> properties) {
		if(value == null) {
			value = ((DependentFunction) this.function).generateValue(properties);
		}
		return value;
	}
	
	public boolean hasRelationFunction() {
		return this.function instanceof RelationFunction;
	}

	public boolean hasDependentFunction() {
		return this.function instanceof DependentFunction;
	}

	public String getRootAttribute() {
		int index = this.name.indexOf(".");
		return index > 0 ? this.name.substring(0, index) : this.name;
	}

    public Function getFunction() {
        return function;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
