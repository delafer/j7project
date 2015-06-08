/**
 *
 */
package net.j7.commons.types;

import java.io.Serializable;

/**
 * @author tavrovsa
 *
 */
public class Range implements Comparable<Range>, Cloneable, Serializable {

	private static final long serialVersionUID = 5230045091892387165L;


	private Integer rangeFrom;
	private Integer rangeTo;
	/**
	 *
	 */
	public Range() {
	}

	public Range(Integer rangeFrom, Integer rangeTo) {
		this.rangeFrom = rangeFrom;
		this.rangeTo = rangeTo;
	}

	public static Range range(int rangeFrom, int size) {
		return new Range(Integer.valueOf(rangeFrom), Integer.valueOf(rangeFrom + size));
	}

	public static Range fromRange(Integer rangeFrom) {
		return new Range(rangeFrom, null);
	}

	public static Range toRange(Integer rangeTo) {
		return new Range(null, rangeTo);
	}

	public boolean hasUpperBound() {
		return rangeTo != null;
	}

	public boolean hasLowerBound() {
		return rangeFrom != null;
	}

	public int length() {
		return !hasLowerBound() && !hasUpperBound() ? (rangeFrom - rangeTo) : -1;
	}

	public int upperBound() {
		return rangeTo != null ? rangeTo.intValue() : Integer.MAX_VALUE;
	}

	public int lowerBound() {
		return rangeFrom != null ? rangeFrom.intValue() : Integer.MIN_VALUE;
	}


	public Integer getRangeFrom() {
		return rangeFrom;
	}

	public void setRangeFrom(Integer rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public Integer getRangeTo() {
		return rangeTo;
	}

	public void setRangeTo(Integer rangeTo) {
		this.rangeTo = rangeTo;
	}

	@Override
	public int compareTo(Range o) {
		return 0;
	}

	@Override
	public int hashCode() {
		int result = ((rangeFrom == null) ? 0 : rangeFrom.hashCode());
		result = 31 * result + ((rangeTo == null) ? 0 : rangeTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;

		if (!(obj instanceof Range)) return false;
		Range other = (Range) obj;


		 if (!equal(rangeFrom, other.rangeFrom)) return false;
		 if (!equal(rangeTo, other.rangeTo)) return false;
		 return true;

	}

	   public final static boolean equal(Integer l1, Integer l2) {
		      return l1 != null && l2 != null ? l1.intValue() == l2.intValue() : l1 == l2;
		   }

	@Override
	public String toString() {
		return String.format("Range [%s - %s]", (rangeFrom != null ? rangeFrom : ".."), (rangeTo != null ? rangeTo : ".."));
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Range newObj = new Range();
		newObj.rangeFrom = this.rangeFrom;
		newObj.rangeTo = this.rangeTo;
		return newObj;
	}

}
